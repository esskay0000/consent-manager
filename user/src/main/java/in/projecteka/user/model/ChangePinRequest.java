package in.projecteka.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangePinRequest {
    private final String pin;
}
