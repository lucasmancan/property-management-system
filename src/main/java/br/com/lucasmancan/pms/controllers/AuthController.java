package br.com.lucasmancan.pms.controllers;


import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.models.AppResponse;
import br.com.lucasmancan.pms.models.PasswordConfirmation;
import br.com.lucasmancan.pms.models.dto.AccountInfo;
import br.com.lucasmancan.pms.services.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/auth")
@Api("Funcionalidades relacionadas a acesso e segurança.")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verify-email/{email}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Envia em e-mail com código de recuperação de senha para o usuário.", response = AppResponse.class)
    public AppResponse verifyEmail(@PathVariable("email") @Valid @Email(message = "E-mail must be valid") String email) throws AppException {
        userService.verifyEmail(email);
        return AppResponse.valueOf("An e-mail with password recovery instructions was sent.", null);
    }

    @PutMapping("activate/{token}")
    @ResponseBody
    @ApiOperation(value = "Ativa o usuário com base no token recebido por e-mail no momento de registro.", response = AppResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Token is invalid")
    })
    public AppResponse activateUser(@PathVariable("token") @Valid @NotNull(message = "token must be valid") String token) throws AppException {
        userService.activate(token);
        return AppResponse.valueOf("", null);
    }

    @PostMapping("sign-up")
    @ResponseBody
    @ApiOperation(value = "Registra o usuário na aplicação.", response = AppResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Welcome to your space."),
            @ApiResponse(code = 400, message = "The data is invalid or user already exists")
    })
    public AppResponse signUp(@RequestBody @Valid AccountInfo accountInfo) throws AppException {
        userService.signUp(accountInfo);
        return AppResponse.valueOf("Welcome to your space.");
    }

    @PostMapping("/change-password")
    @ResponseBody
    @ApiOperation(value = "Troca a senha do usuário caso informações sejam válidas.", response = AppResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password was changed."),
            @ApiResponse(code = 400, message = "Token is invalid")
    })
    public AppResponse changePassword(@RequestBody @Valid PasswordConfirmation passwordConfirmation) throws AppException {
        userService.changePassword(passwordConfirmation);
        return AppResponse.valueOf("Password was changed.");
    }
}
