package br.com.lucasmancan.pms.services.interfaces;

import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.models.AppUser;
import br.com.lucasmancan.pms.models.PasswordConfirmation;
import br.com.lucasmancan.pms.models.dto.AccountInfo;
import br.com.lucasmancan.pms.models.dto.UserDTO;

public interface UserService extends AppService<UserDTO> {
    UserDTO signUp(AccountInfo info) throws AppException;

    AppUser getCurrentUser();

    void verifyEmail(String email) throws AppException;

    void verifyToken(String token) throws AppException;

    void activate(String token);

    void changePassword(PasswordConfirmation passwordConfirmation) throws AppException;
}
