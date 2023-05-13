package kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account.transfer

import java.math.BigDecimal

data class BankAccountTransferRequestData(
    val senderBankAccountNumber: String,
    val receiverBankAccountNumber: String,
    val amount: BigDecimal,
    val password: String,
)
