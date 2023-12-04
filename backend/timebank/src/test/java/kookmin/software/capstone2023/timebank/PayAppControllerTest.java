package kookmin.software.capstone2023.timebank;

import kookmin.software.capstone2023.timebank.application.service.payapp.PayAppService;
import kookmin.software.capstone2023.timebank.domain.model.PayApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PayAppControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private PayAppService payAppService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testRegisterPayApp() {
        // 테스트할 데이터
        String appName = "testApp";

        // 요청 및 응답 테스트
        ResponseEntity<PayApp> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/payapp/register/{appName}",
                PayApp.class,
                appName
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());

        // 필요에 따라 추가적인 검증을 수행할 수 있습니다.
    }
}
