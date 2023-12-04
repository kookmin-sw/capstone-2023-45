package kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BankAccountCreateRequestData {

    @NotBlank(message = "생성하려는 은행 계정의 패스워드를 보내주세요")
    private final String password;

    public BankAccountCreateRequestData(@JsonProperty("password") String password) {
        this.password = password;
    }
}
