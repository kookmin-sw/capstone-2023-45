package kookmin.software.capstone2023.timebank.domain.repository
import kookmin.software.capstone2023.timebank.domain.model.BankAccountTransaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface BankAccountTransactionJpaRepository : JpaRepository<BankAccountTransaction, Long> {
}
