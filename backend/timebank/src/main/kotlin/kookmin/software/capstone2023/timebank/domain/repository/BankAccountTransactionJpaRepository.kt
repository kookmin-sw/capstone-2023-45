package kookmin.software.capstone2023.timebank.domain.repository
import kookmin.software.capstone2023.timebank.domain.model.BankAccountTransaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface BankAccountTransactionJpaRepository : JpaRepository<BankAccountTransaction, Long> {
    @Query("SELECT t FROM BankAccountTransaction t WHERE t.bankAccount = :bankAccount AND t.transactionAt BETWEEN :start AND :end ORDER BY t.transactionAt DESC")
    fun findAllByBankAccountAndTransactionAtBetween(bankAccount: Long, start: LocalDateTime, end: LocalDateTime): List<BankAccountTransaction>
}