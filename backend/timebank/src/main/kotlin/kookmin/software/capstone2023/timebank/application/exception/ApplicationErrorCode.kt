package kookmin.software.capstone2023.timebank.application.exception

enum class ApplicationErrorCode(
    val value: String,
    val message: String,
) {
    // 일반 오류
    BAD_REQUEST("BAD_REQUEST", "잘못된 요청입니다."),
    UNAUTHORIZED("UNAUTHORIZED", ""),
    FORBIDDEN("FORBIDDEN", "접근 권한이 없습니다."),
    NOT_FOUND("NOT_FOUND", "존재하지 않는 리소스입니다."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버 내부 오류입니다."),


    // 뱅킹 시스템 관련 오류
    BANK_ERROR("BANK_ERROR", "뱅킹 서버 오류입니다.")
}