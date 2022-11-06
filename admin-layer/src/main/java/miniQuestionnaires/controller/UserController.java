package miniQuestionnaires.controller;

import miniQuestionnaires.api.data.*;
import miniQuestionnaires.api.data.helper.QuestionnairePartsToShow;
import miniQuestionnaires.persistence.layer.entity.Role;
import miniQuestionnaires.persistence.layer.entity.User;
import miniQuestionnaires.service.answerOnQuestionService.AnswerForQuestionService;
import miniQuestionnaires.service.answerService.AnswerService;
import miniQuestionnaires.service.questionService.QuestionService;
import miniQuestionnaires.service.questionnaireService.QuestionnaireService;
import miniQuestionnaires.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final QuestionnaireService questionnaireService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AnswerForQuestionService answerForQuestionService;

    @Autowired
    public UserController(UserService userService, QuestionnaireService questionnaireService,
                          QuestionService questionService, AnswerService answerService,
                          AnswerForQuestionService answerOnQuestionService) {
        this.userService = userService;
        this.questionnaireService = questionnaireService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.answerForQuestionService = answerOnQuestionService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "api/user/profile";
    }

    @GetMapping("/showAllAvailableQuestionnaires")
    public String showAllAvailableQuestionnaires(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());

        String userRole = userService.getUserRole(user);

        List<QuestionnaireApiData> questionnairesApiData = questionnaireService
                .findAllNonCompletedQuestionnairesByUser(user.getId());

        model.addAttribute("questionnairesApiData", questionnairesApiData);
        model.addAttribute("userRole", userRole);

        return "api/user/allQuestionnaires";
    }

    @GetMapping("/showQuestionnaire")
    public String showQuestionnaire(@ModelAttribute("questionnaire_id") Long questionnaireId,
                                                 Model model){

        QuestionnaireApiData questionnaireApiData = questionnaireService
                .findQuestionnaireById(questionnaireId);

        List<QuestionApiData> questionsFromQuestionnaire = questionService.findAllByQuestionnaireIdApiData(questionnaireId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());

        String userRole = userService.getUserRole(user);

        UserApiData userApiData = userService.getUserById(user.getId());

        List<AnswerApiData> answersOnQuestionsApiData = answerForQuestionService
                .createAnswersToQuestionsForUser(questionsFromQuestionnaire, userApiData);

        List<AnswerForQuestionApiData> answersForQuestions = answerForQuestionService
                .findAllAnswersForQuestions();

        QuestionnairePartsToShow questionnaireParts = new QuestionnairePartsToShow(
                answersForQuestions,
                answersOnQuestionsApiData
        );

        model.addAttribute("questionnaireParts", questionnaireParts);
        model.addAttribute("questionnaireApiData", questionnaireApiData);
        model.addAttribute("userRole", userRole);

        return "api/user/showQuestionnaire";
    }

    @PostMapping("/saveAnswersFromQuestionnaire")
    public String saveAnswersFromQuestionnaire(@ModelAttribute("questionnaireParts")
                                                   QuestionnairePartsToShow questionnaireParts, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());

        answerService.saveAnswer(user, questionnaireParts.getAnswersOnQuestionsApiData());

        String userRole = userService.getUserRole(user);

        model.addAttribute("user", user);
        model.addAttribute("userRole", userRole);

        return "/api/user/successCompleteQuestionnaire";
    }
}
