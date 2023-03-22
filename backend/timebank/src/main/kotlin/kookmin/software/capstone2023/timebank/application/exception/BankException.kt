package kookmin.software.capstone2023.timebank.application.exception
class BankException(
        code: ApplicationErrorCode = ApplicationErrorCode.BANK_ERROR,
        override val message: String?,
) : ApplicationException(code, message)
