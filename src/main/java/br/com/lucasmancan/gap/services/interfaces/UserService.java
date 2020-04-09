package br.com.lucasmancan.gap.services.interfaces;

import br.com.lucasmancan.gap.exceptions.AppException;
import br.com.lucasmancan.gap.models.PasswordConfirmation;
import br.com.lucasmancan.gap.models.dto.SignUpInfo;
import br.com.lucasmancan.gap.models.dto.UserDTO;
import org.springframework.stereotype.Service;

public interface UserService extends AppService<UserDTO> {
    UserDTO signUp(SignUpInfo info) throws AppException;

    void verifyEmail(String email);

    void verifyToken(String token) throws AppException;

    void activate(String token);

    void changePassword(PasswordConfirmation passwordConfirmation) throws AppException;
}
