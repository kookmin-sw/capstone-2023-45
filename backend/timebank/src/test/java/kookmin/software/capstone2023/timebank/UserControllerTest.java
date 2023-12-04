package kookmin.software.capstone2023.timebank;

import com.fasterxml.jackson.databind.ObjectMapper;
import kookmin.software.capstone2023.timebank.domain.model.Gender;
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.UserRegisterRequestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterBranch() throws Exception {

    }

    @Test
    public void testRegisterUser() throws Exception {
        // Mock request data
        UserRegisterRequestData requestData =
                new UserRegisterRequestData.UserPasswordRegisterRequestData(
                        "test1",
                        "test1",
                        "test1",
                        "01012341234",
                        Gender.MALE,
                        LocalDate.now());

        // Perform the request
        ResultActions resultActions = mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestData)));
        resultActions.andExpect(status().isCreated());

        System.out.println(resultActions);
    }
}
