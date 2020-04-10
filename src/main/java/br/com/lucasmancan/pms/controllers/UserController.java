package br.com.lucasmancan.pms.controllers;

import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.exceptions.AppNotFoundException;
import br.com.lucasmancan.pms.models.AppResponse;
import br.com.lucasmancan.pms.models.dto.UserDTO;
import br.com.lucasmancan.pms.services.interfaces.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Your profile was saved."),
            @ApiResponse(code = 400, message = "e-mail associated with another user")
    })
    @ApiOperation(value = "Atualiza informações de conta do usuário.", response = AppResponse.class)
    public AppResponse save(@RequestBody UserDTO userDTO) throws AppException {
        userService.save(userDTO);
        return AppResponse.valueOf("Your profile was saved.");
    }

    @DeleteMapping
    @ApiOperation(value = "Inativa o usuário logado.", response = AppResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Your profile was saved.")
    })
    public AppResponse inactivate() throws AppException, AppNotFoundException {
        userService.inactivate(userService.getCurrentUser().getId());
        return AppResponse.valueOf("Your profile was saved.");
    }

}
