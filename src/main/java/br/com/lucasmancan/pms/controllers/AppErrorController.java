package br.com.lucasmancan.pms.controllers;

import br.com.lucasmancan.pms.models.AppResponse;
import io.swagger.annotations.Api;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@Api(hidden = true)
public class AppErrorController implements ErrorController {


    @RequestMapping("/error")
    @ResponseBody
    public AppResponse handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return AppResponse.valueOf("Resource not found");
            }
            else if(statusCode == HttpStatus.UNAUTHORIZED.value() || statusCode == HttpStatus.FORBIDDEN.value()) {
                return AppResponse.valueOf("You have no permission to access this resource.");
            }else if(statusCode == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                return AppResponse.valueOf("Unprocessable entity.");
            }
        }
        return AppResponse.OOPS;
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}