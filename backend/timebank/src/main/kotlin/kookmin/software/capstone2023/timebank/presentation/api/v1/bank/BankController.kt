package kookmin.software.capstone2023.timebank.presentation.controller

import kookmin.software.capstone2023.timebank.application.service.bank.BankAccountCreateService
import kookmin.software.capstone2023.timebank.presentation.api.v1.bank.model.BankAccountCreateRequestData
import kookmin.software.capstone2023.timebank.presentation.api.v1.bank.model.BankAccountCreateResponseData
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/bank/account")
class BankAccountController(
        private val bankAccountCreateService: BankAccountCreateService
) {
    @PostMapping
    fun createBankAccount(
        @Validated @RequestBody data: BankAccountCreateRequestData
    ):BankAccountCreateResponseData {
        val createdBankAccount: BankAccountCreateService.CreatedBankAccount = bankAccountCreateService.createBankAccount(
            userId = data.userId,
            accountId = data.accountId,
            branchId = data.branchId,
            encryptedPin = data.password,
            iv = data.iv,
        )

        return BankAccountCreateResponseData(
            balance = createdBankAccount.balance,
            accountNumber = createdBankAccount.accountNumber,
            accountId = createdBankAccount.accountId,
            userId = createdBankAccount.userId,
            bankAccountId = createdBankAccount.bankAccountId
        )
    }
}
