package br.com.lucasmancan.gap.controllers;

import br.com.lucasmancan.gap.exceptions.AppException;
import br.com.lucasmancan.gap.exceptions.AppNotFoundException;
import br.com.lucasmancan.gap.models.AppResponse;
import br.com.lucasmancan.gap.models.dto.UserDTO;
import br.com.lucasmancan.gap.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseBody
    public AppResponse save(@RequestBody UserDTO userDTO) throws AppException {
        userService.save(userDTO);
        return AppResponse.valueOf("Your profile has been saved.");
    }

    @DeleteMapping("{userId}")
    public AppResponse inactivate(@RequestParam("userId") Long userId) throws AppException, AppNotFoundException {
        userService.inactivate(userId);
        return AppResponse.valueOf("Your profile has been saved.");
    }

}
