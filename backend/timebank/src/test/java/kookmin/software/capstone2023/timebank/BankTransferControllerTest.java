package kookmin.software.capstone2023.timebank;

import com.fasterxml.jackson.databind.ObjectMapper;
import kookmin.software.capstone2023.timebank.application.service.bank.account.BankAccountReadService;
import kookmin.software.capstone2023.timebank.domain.model.AccountType;
import kookmin.software.capstone2023.timebank.domain.model.BankAccount;
import kookmin.software.capstone2023.timebank.domain.repository.BankAccountJpaRepository;
import kookmin.software.capstone2023.timebank.presentation.api.RequestAttributes;
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserContext;
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.transfer.BankAccountTransferRequestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BankTransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BankAccountReadService bankAccountReadService;

    @Test
    public void testBankTransfer() throws Exception {
        BankAccount senderBankAccount = bankAccountReadService.getBankAccountByBankAccountId(1L);
        BankAccount recieverBankAccount = bankAccountReadService.getBankAccountByBankAccountId(2L);

        assertNotNull(senderBankAccount, "보내는 계좌가 없습니다");
        assertNotNull(recieverBankAccount, "받는 계좌가 없습니다.");

        // 테스트할 데이터
        BankAccountTransferRequestData requestData = new BankAccountTransferRequestData(
                senderBankAccount.getAccountNumber(),
                recieverBankAccount.getAccountNumber(),
                new BigDecimal(120),
                "user1"
        );
        UserContext userContext = new UserContext(1L, 1L, AccountType.INDIVIDUAL);

        // POST 요청 보내기
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/bank/account/transfer")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(RequestAttributes.USER_CONTEXT, objectMapper.writeValueAsString(userContext))
                .content(objectMapper.writeValueAsString(requestData))
        );

        // 응답 확인
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.transactionAt").isNotEmpty())
                .andExpect(jsonPath("$.amount").value(requestData.getAmount()))
                .andExpect(jsonPath("$.balanceSnapshot").isNotEmpty())
                .andExpect(jsonPath("$.status").isNotEmpty())
                .andExpect(jsonPath("$.senderBankAccountNumber").value(requestData.getSenderBankAccountNumber()))
                .andExpect(jsonPath("$.receiverBankAccountNumber").value(requestData.getReceiverBankAccountNumber()));
    }
}
