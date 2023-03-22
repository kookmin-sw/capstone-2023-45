package kookmin.software.capstone2023.timebank.domain.model
import jakarta.persistence.*
import kookmin.software.capstone2023.timebank.domain.model.BankBranch
import kookmin.software.capstone2023.timebank.domain.model.OwnerType
import kookmin.software.capstone2023.timebank.domain.model.User
import java.time.LocalDateTime

@Entity
@Table(name = "BankAccount")
data class BankAccount(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "account_id")
        val account: Account,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "branch_id")
        var branch: BankBranch,

        @Enumerated(EnumType.STRING)
        val ownerType: OwnerType? = null,

        @Column(nullable = false)
        val accountNumber: String,

        @Column(nullable = false)
        var password: String,

        @Column(nullable = false)
        var balance: Int,

        @Column(nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(nullable = false)
        var updatedAt: LocalDateTime = LocalDateTime.now(),

        @Column(nullable = true)
        val deletedAt: LocalDateTime? = null
)