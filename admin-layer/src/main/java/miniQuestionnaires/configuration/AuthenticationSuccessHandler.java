package miniQuestionnaires.configuration;

import miniQuestionnaires.persistence.layer.entity.User;
import miniQuestionnaires.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    @Autowired
    public AuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);

        String login = authentication.getName();
        User user = userService.getUserByEmail(login);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }

    @Override
    public String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();
        String url = "";

        if (role.equals("[ROLE_ADMIN]")) {
            url = "/api/admin/profile";
        }
        else if (role.equals("[ROLE_USER]")){
            url = "/api/user/profile";
        }

        return url;
    }
}