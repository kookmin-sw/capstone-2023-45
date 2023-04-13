package kookmin.software.capstone2023.timebank.presentation.api.v1

import kookmin.software.capstone2023.timebank.application.service.inqui.InquiryService
import kookmin.software.capstone2023.timebank.domain.model.Period
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/inquiries")
class InquiryController(
    private val inquiryService: InquiryService,
) {
    /**
     * 문의 작성
     */
    @PostMapping
    fun createInquiry(@RequestBody request: InquiryService.InquiryCreateRequest): InquiryService.InquiryDto {
        return inquiryService.createInquiry(request)
    }

    /**
     * 문의 전체 조회
     */
    @GetMapping
    fun getInquiries(): List<InquiryService.InquiryDto> {
        return inquiryService.getInquiries()
    }

    /**
     * 문의ID겁색 조회 (댓글까지)
     *
     */
    @GetMapping("/{id}")
    fun getInquiryById(@PathVariable id: Long): InquiryService.InquiryDto {
        return inquiryService.getInquiryById(id)
    }

    /**
     * userId검색 조회
     */
    @GetMapping("/users/{userId}")
    fun getInquiriesByUserId(@PathVariable userId: Long): List<InquiryService.InquiryDto> {
        return inquiryService.getInquiriesByUserId(userId)
    }

    /**
     * 문의 기간 조회
     */
    @GetMapping("/period")
    fun getInquiriesByPeriod(@RequestParam("period") period: Period): List<InquiryService.InquiryDto> {
        return inquiryService.getInquiriesByPeriod(period)
    }

    /**
     * 문의 제목 조회
     */
    @GetMapping("/search")
    fun getInquiriesByTitle(@RequestParam("title") title: String): List<InquiryService.InquiryDto> {
        return inquiryService.getInquiryByTitle(title)
    }

    /**
     * 문의 수정
     */
    @PutMapping("/{id}")
    fun updateInquiry(
        @PathVariable id: Long,
        @RequestBody request: InquiryService.InquiryUpdateRequest,
    ): InquiryService.InquiryDto {
        return inquiryService.updateInquiry(id, request)
    }

    /**
     * 문의 삭제
     */
    @DeleteMapping("/users/{userId}/{inquiryId}")
    fun deleteInquiryByUserId(@PathVariable userId: Long, @PathVariable inquiryId: Long): ResponseEntity<Unit> {
        inquiryService.deleteInquiryByUserId(userId, inquiryId)
        return ResponseEntity.noContent().build()
    }
}
