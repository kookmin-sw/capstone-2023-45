package kookmin.software.capstone2023.timebank.application.service.bank.account

import kookmin.software.capstone2023.timebank.application.exception.NotFoundException
import kookmin.software.capstone2023.timebank.domain.model.Account
import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import kookmin.software.capstone2023.timebank.domain.repository.AccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.BankAccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BankAccountReadService(
    private val userRepository: UserJpaRepository,
    private val accountRepository: AccountJpaRepository,
    private val bankAccountRepository: BankAccountJpaRepository,
) {

    // 은행 계좌 조회
    fun readBankAccount(
        userId: Long, // 유저 id
        accountId: Long, // 계정 id
        bankAccountId: Long, // 은행 계좌 id
    ): ReadedBankAccount {
        /*
        유저가 가진 계정id와 요청에 있는 계정id가 일치하는지 확인.
         */
        isUserHasAccount(userId, accountId)
        /*
         유효한 계정 id인지 확인.
         */
        isAccountValid(accountId)
        /*
         은행 계좌 정보 조회
         */
        val bankAccount = getBankAccount(bankAccountId)
        /*
        은행 계좌 정보 반환, ReadedBankAccount 객체로 반환
         */
        return ReadedBankAccount(
            userId = userId,
            accountId = bankAccount.accountId,
            branchId = bankAccount.branchId,
            bankAccountId = bankAccount.id,
            balance = bankAccount.balance,
            accountNumber = bankAccount.accountNumber,
        )
    }

    // 유저가 가진 계정 id와 요청에 있는 계정 id가 일치하는지 확인.
    private fun isUserHasAccount(
        userId: Long,
        accountId: Long,
    ): Boolean {
        val user = userRepository.getUserById(userId)

        if (user == null) { throw NotFoundException(message = "User with ID $userId not found") }

        return user.accountId == accountId
    }

    // 유효한 계정 id인지 확인.
    private fun isAccountValid(
        accountId: Long,
    ): Boolean {
        val account = accountRepository.getAccountById(accountId)

        if (account == null) { throw NotFoundException(message = "Account with ID $accountId not found") }

        return account.id == accountId
    }

    fun getAccountById(accountId: Long): Account {
        return accountRepository.findById(accountId)
            .orElseThrow { NotFoundException(message = "찾으시는 계정이 존재하지 않습니다.") }
    }

    fun getBankAccount(bankAccountId: Long): BankAccount {
        return bankAccountRepository.findById(bankAccountId)
            .orElseThrow { NotFoundException(message = "찾으시는 계좌가 존재하지 않습니다.") }
    }

    // 중복된 계좌 생성을 방지하는 기능 추가
    fun isAccountNumberExists(accountNumber: String): Boolean {
        return bankAccountRepository.findByAccountNumber(accountNumber) != null
    }

    data class ReadedBankAccount(
        val userId: Long,
        val accountId: Long,
        val branchId: Long,
        val bankAccountId: Long,
        val balance: BigDecimal,
        val accountNumber: String,
    )
}
