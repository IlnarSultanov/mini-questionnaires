package miniQuestionnaires.controller;

import miniQuestionnaires.dto.RegisterUserDto;
import miniQuestionnaires.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/registration")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAddUserForm(Model model){
        RegisterUserDto registerUserDto = new RegisterUserDto();
        model.addAttribute("registerUserDto", registerUserDto);
        return "/api/registration/registration";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@Valid RegisterUserDto registerUserDto, BindingResult result,
                             Model model){
        String err = userService.validateNewUser(registerUserDto);

        if (result.hasErrors() || !err.isEmpty()){
            if (!err.isEmpty()){
                ObjectError error = new ObjectError("globalError", err);
                result.addError(error);
            }
            model.addAttribute("registerUserDto", registerUserDto);
            return "/api/registration/registration";
        }
        userService.saveRegisterUser(registerUserDto);

        model.addAttribute("successRegistration", true);
        return "api/authentication/auth";
    }
}
