package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "BankBranchManager")
data class BankBranchManager(
        @Id
        @Column(name = "Key", nullable = false)
        val key: String,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "branch_id")
        val branch: BankBranch,

        val id: String? = null,

        val password: String? = null,

        val name: String? = null,

        @Column(nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(nullable = false)
        var updatedAt: LocalDateTime = LocalDateTime.now(),

        @Column(nullable = true)
        val field: String? = null

)