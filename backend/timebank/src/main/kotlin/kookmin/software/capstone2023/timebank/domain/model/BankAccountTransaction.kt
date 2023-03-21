package kookmin.software.capstone2023.timebank.domain.model
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "BankAccountTransaction")
data class BankAccountTransaction(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "bank_account_id")
        val bankAccount: BankAccount,

        @Enumerated(EnumType.STRING)
        val code: TransactionCode? = null,

        val amount: Int? = null,

        @Enumerated(EnumType.STRING)
        var status: TransactionStatus = TransactionStatus.REQUESTED,

        val receiverAccountNumber: String? = null,

        val senderAccountNumber: String? = null,

        val balanceSnapshot: Int? = null,

        @Column(nullable = false)
        val transactionAt: LocalDateTime = LocalDateTime.now(),

        @Column(nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(nullable = false)
        var updatedAt: LocalDateTime = LocalDateTime.now()
)