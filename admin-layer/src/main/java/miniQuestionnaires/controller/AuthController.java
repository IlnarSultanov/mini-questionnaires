package miniQuestionnaires.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/auth")
    public String loginPage() {
        return "api/authentication/auth";
    }

    @GetMapping("/authError")
    public String loginError(Model model) {
        model.addAttribute("authError", true);
        return "api/authentication/auth";
    }
}
