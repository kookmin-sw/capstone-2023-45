package kookmin.software.capstone2023.timebank.domain.repository
import kookmin.software.capstone2023.timebank.domain.model.BankAccountTransaction
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountTransactionJpaRepository : JpaRepository<BankAccountTransaction, Long> {
}
