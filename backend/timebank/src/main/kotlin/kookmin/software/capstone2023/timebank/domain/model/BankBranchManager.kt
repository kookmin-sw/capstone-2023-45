//package kookmin.software.capstone2023.timebank.domain.model
//
//import jakarta.persistence.*
//import java.time.LocalDateTime
//
//@Entity
//@Table(name = "BankBranchManager")
//data class BankBranchManager(
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        val key: Long = 0,
//
//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "branch_id")
//        val branch: BankBranch,
//
//        @Column(nullable=false, updatable = true)
//        val id: String,
//
//        @Column(nullable = false, updatable = true)
//        val password: String,
//
//        @Column(nullable = false, updatable = true)
//        val name: String,
//
//) : BaseTimeEntity()
