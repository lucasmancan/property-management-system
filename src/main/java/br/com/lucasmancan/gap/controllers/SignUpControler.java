package br.com.lucasmancan.gap.controllers;


import br.com.lucasmancan.gap.exceptions.AppException;
import br.com.lucasmancan.gap.models.AppResponse;
import br.com.lucasmancan.gap.models.PasswordConfirmation;
import br.com.lucasmancan.gap.models.dto.SignUpInfo;
import br.com.lucasmancan.gap.models.dto.UserDTO;
import br.com.lucasmancan.gap.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class SignUpControler {

    private UserService userService;

    @Autowired
    public SignUpControler(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/verify-email")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public AppResponse verifyEmail(@RequestBody UserDTO userDTO) throws AppException {
        userService.verifyEmail(userDTO.getEmail());
        return AppResponse.valueOf("An e-mail with password recovery instructions was sent.", null);
    }

    @PostMapping("verify-token")
    @ResponseBody
    public AppResponse verifyToken(@RequestBody PasswordConfirmation token) throws AppException {
        userService.verifyToken(token.getToken());
        return AppResponse.valueOf("", null);
    }

    @PostMapping("activate")
    @ResponseBody
    public AppResponse activateUser(@RequestBody PasswordConfirmation token) throws AppException {
        userService.activate(token.getToken());
        return AppResponse.valueOf("", null);
    }

    @PostMapping("sign-up")
    @ResponseBody
    public AppResponse signUp(@RequestBody SignUpInfo signUpInfo) throws AppException {
        userService.signUp(signUpInfo);
        return AppResponse.valueOf("Welcome to your space.");
    }

    @PostMapping("/change-password")
    @ResponseBody
    public AppResponse changePassword(@RequestBody PasswordConfirmation passwordConfirmation) throws AppException {
        userService.changePassword(passwordConfirmation);
        return AppResponse.valueOf("Password has been changed.");
    }
}
