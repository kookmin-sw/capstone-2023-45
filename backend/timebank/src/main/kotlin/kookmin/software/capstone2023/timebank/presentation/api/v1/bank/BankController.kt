package kookmin.software.capstone2023.timebank.presentation.controller

import kookmin.software.capstone2023.timebank.application.service.bank.BankAccountService
import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import kookmin.software.capstone2023.timebank.domain.model.BankAccountTransaction
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("v1/bank/account")
class BankAccountController(
        private val bankAccountService: BankAccountService
) {
    @PostMapping
    fun createBankAccount(@RequestBody requestDto: BankAccountService.CreateBankAccountRequestDto): ResponseEntity<BankAccount> {

        val bankAccount = bankAccountService.createBankAccount(requestDto)

        return ResponseEntity(bankAccount, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getBankAccount(@PathVariable id: Long): ResponseEntity<BankAccount> {
        val bankAccount = bankAccountService.getBankAccount(id)
        return ResponseEntity(bankAccount, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateBankAccount(
            @PathVariable id: Long,
            @RequestParam(required = false) password: String?,
            @RequestParam(required = false) initialBalance: Int?
    ): ResponseEntity<BankAccount> {
        val bankAccount = bankAccountService.updateBankAccount(id, password, initialBalance)
        return ResponseEntity(bankAccount, HttpStatus.OK)
    }

    @GetMapping("/{id}/balance")
    fun getBalance(@PathVariable id: Long): ResponseEntity<Int> {
        val balance = bankAccountService.getBalance(id)
        return ResponseEntity(balance, HttpStatus.OK)
    }

    @PostMapping("/{id}/transfer")
    fun transferMoney(
            @PathVariable id: Long,
            @RequestParam to: Long,
            @RequestParam amount: Int
    ): ResponseEntity<Unit> {
        bankAccountService.transferMoney(id, to, amount)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/{id}/transactions")
    fun getTransactionHistory(
            @PathVariable id: Long,
            @RequestParam(required = false) fromDate: String?,
            @RequestParam(required = false) toDate: String?
    ): ResponseEntity<List<BankAccountTransaction>> {
        val fromDateTime = fromDate?.let { LocalDateTime.parse(it) }
        val toDateTime = toDate?.let { LocalDateTime.parse(it) }
        val transactions = bankAccountService.getTransactionHistory(id, fromDateTime, toDateTime)
        return ResponseEntity(transactions, HttpStatus.OK)
    }
}
