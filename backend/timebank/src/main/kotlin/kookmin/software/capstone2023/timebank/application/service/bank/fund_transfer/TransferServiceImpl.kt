package kookmin.software.capstone2023.timebank.application.service.bank.fund_transfer

import kookmin.software.capstone2023.timebank.application.exception.BadRequestException
import kookmin.software.capstone2023.timebank.application.exception.NotFoundException
import kookmin.software.capstone2023.timebank.application.exception.UnauthorizedException
import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import kookmin.software.capstone2023.timebank.domain.model.BankAccountTransaction
import kookmin.software.capstone2023.timebank.domain.model.TransactionCode
import kookmin.software.capstone2023.timebank.domain.model.TransactionStatus
import kookmin.software.capstone2023.timebank.domain.repository.BankAccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.BankAccountTransactionJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
@Transactional(isolation= Isolation.READ_COMMITTED)
class TransferServiceImpl(
    private val bankAccountJpaRepository: BankAccountJpaRepository,
    private val bankAccountTransactionJpaRepository: BankAccountTransactionJpaRepository,
):TransferService {

    // 계좌 이체를 수행하는 메소드
    override fun transfer(request: TransferService.TransferRequest): BankAccountTransaction {

        // 송금 계좌 조회
        val sender = bankAccountJpaRepository.findByAccountNumber(request.senderAccountNumber)
            ?: throw NotFoundException(message= "송금 계좌가 존재하지 않습니다")

        // 수신 계좌 조회
        val receiver = bankAccountJpaRepository.findByAccountNumber(request.receiverAccountNumber)
            ?: throw NotFoundException(message = "수신 계좌가 존재하지 않습니다")

        // 송금 계좌 비밀번호 일치 여부 확인
        if (sender.password != request.password) {
            throw UnauthorizedException(message = "계좌 비밀번호가 일치하지 않습니다.")
        }

        // 송금 계좌 잔액 부족 여부 확인
        if (sender.balance < request.amount) {
            throw BadRequestException(message = "계좌 잔액이 불충분합니다.")
        }

        // 송금 계좌에서 출금할 트랜잭션 생성
        val senderTransaction = BankAccountTransaction(
            bankAccountId = sender.id,
            code = TransactionCode.WITHDRAW,
            amount = request.amount,
            status = TransactionStatus.REQUESTED,
            senderAccountNumber = sender.accountNumber,
            balanceSnapshot = sender.balance,
            transactionAt = LocalDateTime.now()
        )

        // 수신 계좌에 입금할 트랜잭션 생성
        val receiverTransaction = BankAccountTransaction(
            bankAccountId = receiver.id,
            code = TransactionCode.DEPOSIT,
            amount = request.amount,
            status = TransactionStatus.REQUESTED,
            receiverAccountNumber = receiver.accountNumber,
            balanceSnapshot = receiver.balance,
            transactionAt = LocalDateTime.now()
        )

        // 동시성 제어를 위해 sender, receiver 순서로 락을 걸어줌
        if (sender.id < receiver.id) {
            synchronized(sender) {
                synchronized(receiver) {
                    // 송금 계좌에서 출금하고, 수신 계좌에 입금
                    performTransfer(sender, receiver, senderTransaction, receiverTransaction)
                }
            }
        } else {
            synchronized(receiver) {
                synchronized(sender) {
                    // 송금 계좌에서 출금하고, 수신 계좌에 입금
                    performTransfer(sender, receiver, senderTransaction, receiverTransaction)
                }
            }
        }

        // 송금 계좌에서 출금한 트랜잭션 반환
        return senderTransaction
    }

    // 송금 계좌에서 출금하고, 수신 계좌에 입금하는 메소드
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = [Exception::class])
    fun performTransfer(
        sender: BankAccount,
        receiver: BankAccount,
        senderTransaction: BankAccountTransaction,
        receiverTransaction: BankAccountTransaction
    ) {
        try {
            // 송금 계좌에서 출금
            sender.balance -= senderTransaction.amount!!

            // 수신 계좌에 입금
            receiver.balance += receiverTransaction.amount!!

            // 송금 계좌에서 출금한 트랜잭션 저장
            bankAccountTransactionJpaRepository.save(senderTransaction)

            // 수신 계좌에 입금한 트랜잭션 저장
            bankAccountTransactionJpaRepository.save(receiverTransaction)

            // 계좌 정보 저장
            bankAccountJpaRepository.save(sender)
            bankAccountJpaRepository.save(receiver)

            // 송금 계좌에서 출금한 트랜잭션 상태를 성공으로 변경
            senderTransaction.status = TransactionStatus.SUCCESS
            bankAccountTransactionJpaRepository.save(senderTransaction)

            // 수신 계좌에 입금한 트랜잭션 상태를 성공으로 변경
            receiverTransaction.status = TransactionStatus.SUCCESS
            bankAccountTransactionJpaRepository.save(receiverTransaction)

        } catch (ex: Exception) {
            // 송금 계좌에서 출금한 트랜잭션 상태를 실패로 변경
            senderTransaction.status = TransactionStatus.FAILURE
            bankAccountTransactionJpaRepository.save(senderTransaction)

            // 수신 계좌에 입금한 트랜잭션 상태를 실패로 변경
            receiverTransaction.status = TransactionStatus.FAILURE
            bankAccountTransactionJpaRepository.save(receiverTransaction)

            // 예외 던지기
            throw ex
        }
    }
}