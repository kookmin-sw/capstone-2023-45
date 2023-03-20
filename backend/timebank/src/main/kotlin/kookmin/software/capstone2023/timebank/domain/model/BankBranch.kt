package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "BankBranch")
data class BankBranch(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val branchId: Long = 0,

        val name: String? = null,

        @Column(nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(nullable = false)
        var updatedAt: LocalDateTime = LocalDateTime.now()
)