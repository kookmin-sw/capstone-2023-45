package kookmin.software.capstone2023.timebank.application.service.bank

import kookmin.software.capstone2023.timebank.application.exception.BankException
import kookmin.software.capstone2023.timebank.domain.model.*
import kookmin.software.capstone2023.timebank.domain.repository.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BankAccountService (
        private val accountRepository: AccountJpaRepository,
        private val bankAccountRepository: BankAccountJpaRepository,
        private val bankBranchRepository: BankBranchRepository,
        private val bankAccountTransactionRepository: BankAccountTransactionJpaRepository
) {
    // 계좌 생성
    fun createBankAccount(
            requestDto: CreateBankAccountRequestDto
    ): BankAccount {

        /*
         * 계좌 생성에 필요한 정보를 조회한다.
         */
        val account = getAccountById(requestDto.accountId)
        val branch = getBankBranchById(requestDto.branchId)
        val accountNumber = generateAccountNumber(account, branch)

        /*
         * 계좌 생성에 필요한 정보를 조회한 후, 계좌 번호가 중복되는지 확인한다.
         */
        val existingAccount = bankAccountRepository.findByAccountNumber(accountNumber)

        /*
         * 계좌 번호가 중복되는 경우, 예외를 발생시킨다.
         */
        if (existingAccount != null) {
            throw BankException(message = "Account number $accountNumber already exists")
        }

        /*
         * 계좌 번호가 중복되지 않는 경우, 계좌를 생성한다.
         */
        val bankAccount = BankAccount(
                account = account,
                branch = branch,
                ownerType = OwnerType.USER,
                accountNumber = accountNumber,
                password = encryptPassword(requestDto.password),
                balance = requestDto.initialBalance ?: 0,
                createdAt = LocalDateTime.now()
        )

        bankAccountRepository.save(bankAccount)

        // 계좌 생성 결과를 저장하고 조회할 수 있는 기능 추가
        return getBankAccountById(bankAccount.id)
    }

    // 계좌 삭제
    fun deleteBankAccount(
            bankAccountId: Long,
    ) {
        val bankAccount = getBankAccountById(bankAccountId)
        bankAccountRepository.delete(bankAccount)
    }

    // 계좌 조회
    fun getBankAccount(
            bankAccountId: Long
    ): BankAccount {
        return bankAccountRepository.findById(bankAccountId)
                .orElseThrow { BankException(message = "Bank account with ID $bankAccountId not found") }
    }

    fun getAccountById(accountId: Long): Account {
        return accountRepository.findById(accountId)
                .orElseThrow { BankException(message = "Bank branch with ID $accountId not found") }
    }

    fun getBankBranchById(branchId: Long): BankBranch {
        return bankBranchRepository.findById(branchId)
                .orElseThrow { BankException(message = "Bank branch with ID $branchId not found") }
    }

    fun getBankAccountById(bankAccountId: Long): BankAccount {
        return bankAccountRepository.findById(bankAccountId)
                .orElseThrow { BankException(message = "Bank account with ID $bankAccountId not found") }
    }

    // 유효한 은행 계정인지 여부 반환하는 함수
    fun isValidBankAccount(bankAccountId: Long): Boolean {
        val bankAccount = getBankAccountById(bankAccountId)
        return bankAccount != null
    }

    // 계좌번호 비밀번호 인코딩
    fun encryptPassword(password: String): String {
        return BCryptPasswordEncoder().encode(password)
    }

    // 계좌번호 생성
    private fun generateAccountNumber(account: Account, branch: BankBranch): String {
        val accountCode = account.id.toString().padStart(6, '0')
        val branchCode = branch.id.toString().padStart(4, '0')
        val randomCode = (100000..999999).random().toString()

        return "$accountCode-$branchCode-$randomCode"
    }

    // 중복된 계좌 생성을 방지하는 기능 추가
    fun isAccountNumberExists(accountNumber: String): Boolean {
        return bankAccountRepository.findByAccountNumber(accountNumber) != null
    }

    // 계좌 정보 수정 기능 추가
    fun updateBankAccount(
            bankAccountId: Long,
            password: String? = null,
            initialBalance: Int? = null
    ): BankAccount {
        val bankAccount = getBankAccountById(bankAccountId)

        // 비밀번호 변경
        if (password != null) {
            bankAccount.password = encryptPassword(password)
        }

        // 초기 잔액 변경
        if (initialBalance != null) {
            bankAccount.balance = initialBalance
        }

        bankAccountRepository.save(bankAccount)

        return bankAccount
    }

    // 계좌 잔액 조회 기능 추가
    fun getBalance(bankAccountId: Long): Int {
        val bankAccount = getBankAccountById(bankAccountId)
        return bankAccount.balance
    }

    // 계좌 이체
    fun transferMoney(
            fromBankAccountId: Long,
            toBankAccountId: Long,
            amount: Int
    ) {
        val fromAccount = getBankAccountById(fromBankAccountId)
        val toAccount = getBankAccountById(toBankAccountId)

        if (fromAccount.balance < amount) {
            throw BankException(message = "Insufficient balance")
        }

        fromAccount.balance -= amount
        toAccount.balance += amount

        bankAccountRepository.save(fromAccount) // 출금자
        bankAccountRepository.save(toAccount) // 입금자

        val transactionAt = LocalDateTime.now()

        // 계좌 이체 거래 기록 저장
        val senderTransaction = BankAccountTransaction(
                bankAccount = fromAccount,
                code = TransactionCode.WITHDRAW,
                amount = amount,
                receiverAccountNumber = toAccount.accountNumber,
                senderAccountNumber = fromAccount.accountNumber,
                balanceSnapshot = fromAccount.balance,
                transactionAt = transactionAt
        )

        val receiverTransaction = BankAccountTransaction(
                bankAccount = toAccount,
                code = TransactionCode.DEPOSIT,
                amount = amount,
                receiverAccountNumber = toAccount.accountNumber,
                senderAccountNumber = fromAccount.accountNumber,
                balanceSnapshot = toAccount.balance,
                transactionAt = transactionAt
        )

        bankAccountTransactionRepository.save(senderTransaction)
        bankAccountTransactionRepository.save(receiverTransaction)
    }

    // 기간에 따른 거래 내역 조회
    fun getTransactionHistory(
            bankAccountId: Long,
            fromDate: LocalDateTime? = null,
            toDate: LocalDateTime? = null
    ): List<BankAccountTransaction> {

        if(isValidBankAccount(bankAccountId) == false) {
            throw BankException(message = "Bank account with ID $bankAccountId not found")
        }

        // 계좌의 입력 기간동안의 거래 내역을 조회, 입력 기간이 없다면, 계좌의 전체 거래 내역을 조회하는 기능 추가
        return bankAccountTransactionRepository.findAllByBankAccountAndTransactionAtBetween(
                bankAccountId,
                fromDate ?: LocalDateTime.MIN,
                toDate ?: LocalDateTime.MAX
        )
    }


    data class CreateBankAccountRequestDto(
            val accountId: Long,
            val branchId: Long,
            val password: String,
            val initialBalance: Int? = null
    )
}