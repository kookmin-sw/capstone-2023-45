package kookmin.software.capstone2023.timebank.presentation.api.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import kookmin.software.capstone2023.timebank.domain.model.AccountType;
import lombok.Getter;

@Getter
public class UserContext {
    private final long userId;
    private final long accountId;
    private final AccountType accountType;

    @JsonCreator
    public UserContext(@JsonProperty("userId") long userId,
                       @JsonProperty("accountId") long accountId,
                       @JsonProperty("accountType") AccountType accountType) {
        this.userId = userId;
        this.accountId = accountId;
        this.accountType = accountType;
    }
}
