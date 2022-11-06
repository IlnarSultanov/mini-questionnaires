package miniQuestionnaires.controller;

import miniQuestionnaires.api.data.AnswerApiData;
import miniQuestionnaires.api.data.QuestionApiData;
import miniQuestionnaires.api.data.QuestionnaireApiData;
import miniQuestionnaires.api.data.UserApiData;
import miniQuestionnaires.dto.createQuestionnaire.CreateAnswerForQuestionDto;
import miniQuestionnaires.dto.createQuestionnaire.CreateQuestionDto;
import miniQuestionnaires.dto.createQuestionnaire.CreateQuestionnaireDto;
import miniQuestionnaires.dto.CreateQuestionnairePartsDto;
import miniQuestionnaires.dto.helpers.answer.AnswerUtils;
import miniQuestionnaires.dto.helpers.choice.ChoiceUtils;
import miniQuestionnaires.dto.updateQuestionnaire.*;
import miniQuestionnaires.persistence.layer.entity.User;
import miniQuestionnaires.service.answerOnQuestionService.AnswerForQuestionService;
import miniQuestionnaires.service.answerService.AnswerService;
import miniQuestionnaires.service.questionService.QuestionService;
import miniQuestionnaires.service.questionnaireService.QuestionnaireService;
import miniQuestionnaires.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final QuestionnaireService questionnaireService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AnswerForQuestionService answerForQuestionService;

    @Autowired
    public AdminController(UserService userService, QuestionnaireService questionnaireService,
                           QuestionService questionService, AnswerService answerService,
                           AnswerForQuestionService answerForQuestionService) {
        this.userService = userService;
        this.questionnaireService = questionnaireService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.answerForQuestionService = answerForQuestionService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal){
        User admin = userService.getUserByEmail(principal.getName());
        model.addAttribute("admin", admin);
        return "api/admin/profile";
    }

    @GetMapping("/questionnaires")
    public String getAllQuestionnaires(Model model){
        List<QuestionnaireApiData> questionnaires = questionnaireService.findAll();
        model.addAttribute("questionnaires", questionnaires);
        return "api/admin/allQuestionnaires";
    }

    @GetMapping("/showAddQuestionnaire")
    public String showAddQuestionnaire(Model model){
        CreateQuestionnairePartsDto createQuestionnairePartsDto = new CreateQuestionnairePartsDto(
                new CreateQuestionnaireDto(),
                new CreateQuestionDto(),
                new CreateAnswerForQuestionDto(),
                Boolean.FALSE
        );

        model.addAttribute("createQuestionnairePartsDto", createQuestionnairePartsDto);
        model.addAttribute("numberOfChoices", ChoiceUtils.getAllNumberOfChoicesList());
        model.addAttribute("numberOfAnswers", AnswerUtils.getMaxNumberOfAnswersList());
        model.addAttribute("isAvailableToEnterAnswers", false);

        return "api/admin/addQuestionnaire";
    }

    @GetMapping("/showAddQuestionToQuestionnaire")
    public String showAddQuestionToQuestionnaire(CreateQuestionnaireDto createQuestionnaireDto,
                                                 Model model){
        CreateQuestionnairePartsDto createQuestionnairePartsDto = new CreateQuestionnairePartsDto(
                new CreateQuestionnaireDto(),
                new CreateQuestionDto(),
                new CreateAnswerForQuestionDto(),
                Boolean.TRUE
        );

        createQuestionnairePartsDto.getCreateQuestionnaireDto().setTitle(createQuestionnaireDto.getTitle());
        model.addAttribute("createQuestionnairePartsDto", createQuestionnairePartsDto);
        model.addAttribute("numberOfChoices", ChoiceUtils.getAllNumberOfChoicesList());
        model.addAttribute("numberOfAnswers", AnswerUtils.getMaxNumberOfAnswersList());
        model.addAttribute("isAvailableToEnterAnswers", false);

        return "api/admin/addQuestionnaire";
    }

    @PostMapping("/saveQuestionnaire")
    public String saveQuestionnaire(@Valid CreateQuestionnairePartsDto createQuestionnairePartsDto,
                                   BindingResult result, Model model){

        model.addAttribute("createQuestionnairePartsDto", createQuestionnairePartsDto);
        model.addAttribute("numberOfChoices", ChoiceUtils.getAllNumberOfChoicesList());
        model.addAttribute("numberOfAnswers", AnswerUtils.getMaxNumberOfAnswersList());
        model.addAttribute("isAvailableToEnterAnswers", false);

        String err = "";
        if (!createQuestionnairePartsDto.getIsQuestionnaireTitleFixed()) {
            err = questionnaireService.validateNewQuestionnaire(
                    createQuestionnairePartsDto.getCreateQuestionnaireDto());
        }

        if (result.hasErrors() || !err.isEmpty()){
            if (!err.isEmpty()){
                result.addError(new ObjectError("globalError", err));
            }
            return "api/admin/addQuestionnaire";
        }

        model.addAttribute("selectedNumberOfAnswers", AnswerUtils.getSelectedNumberOfAnswersList(
                createQuestionnairePartsDto.getCreateQuestionDto().getNumberOfAnswers()));
        model.addAttribute("isAvailableToEnterAnswers", true);

        if (createQuestionnairePartsDto.getCreateAnswerForQuestionDto() != null){
            questionnaireService.saveQuestionnaire(
                    createQuestionnairePartsDto.getCreateQuestionnaireDto(),
                    createQuestionnairePartsDto.getCreateQuestionDto(),
                    createQuestionnairePartsDto.getCreateAnswerForQuestionDto()
            );

            return showAddQuestionToQuestionnaire(createQuestionnairePartsDto.getCreateQuestionnaireDto(),
                    model);
        }

        return "api/admin/addQuestionnaire";
    }

    @GetMapping("/deleteQuestionnaire")
    public String deleteQuestionnaire(@ModelAttribute("questionnaire_id") Long questionnaireId,
                                      Model model){
        questionnaireService.deleteQuestionnaire(questionnaireId);
        return getAllQuestionnaires(model);
    }

    @GetMapping("/showAllQuestionnairesStatistic")
    public String showQuestionnairesStatistic(Model model){
        List<QuestionnaireApiData> questionnaires = questionnaireService.findAll();
        model.addAttribute("questionnaires", questionnaires);
        return "/api/admin/questionnairesStatistics";
    }

    @GetMapping("/showConcreteQuestionnaireStatistic")
    public String showConcreteQuestionnaireStatistic(@ModelAttribute("questionnaire_id") Long questionnaireId,
                                                     Model model){
        List<UserApiData> usersApiData = userService.getUsersCompletedQuestionnaire(questionnaireId);
        QuestionnaireApiData questionnaireApiData = questionnaireService.findQuestionnaireById(questionnaireId);

        model.addAttribute("usersApiData", usersApiData);
        model.addAttribute("questionnaireApiData", questionnaireApiData);

        return "api/admin/concreteQuestionnaireStatistics";
    }

    @GetMapping("showResultsOfCompletingQuestionnaireByUser")
    public String showResultsOfCompletingQuestionnaireByUser(@ModelAttribute("user_id") Long userId,
                                                             @ModelAttribute("questionnaire_id") Long questionnaireId,
                                                             Model model){

        Map<QuestionApiData, List<AnswerApiData>> answersOnQuestionsByUser =
                answerService.getAnswersOnQuestionsByUser(userId, questionnaireId);

        UserApiData userApiData = userService.getUserById(userId);

        model.addAttribute("answersOnQuestionsByUser", answersOnQuestionsByUser);
        model.addAttribute("userApiData", userApiData);

        return "api/admin/resultOfCompletingQuestionnaire";
    }

    @GetMapping("/updateQuestionnaire")
    public String showUpdateQuestionnaireForm(@ModelAttribute("questionnaire_id") Long questionnaireId, Model model) {

        UpdateQuestionnaireDto updatableQuestionnaire = questionnaireService
                .getUpdatableQuestionnaireById(questionnaireId);

        List<UpdatedQuestionWithAnswers> updatableQuestionWithAnswersList = answerForQuestionService.getUpdatableQuestionsWithAnswers(
                        questionService.getUpdatableQuestionsByQuestionnaireId(questionnaireId));

        CompletedUpdateQuestionnaire completedUpdateQuestionnaire = new CompletedUpdateQuestionnaire(
                updatableQuestionnaire,
                updatableQuestionWithAnswersList);

        model.addAttribute("completedUpdateQuestionnaire", completedUpdateQuestionnaire);

        if (!model.containsAttribute("isErrorOccurred"))
            model.addAttribute("isErrorOccurred", false);

        return "api/admin/updateQuestionnaire";
    }

    @PostMapping("/updateQuestionnaire")
    public String updateQuestionnaire(@Valid CompletedUpdateQuestionnaire completedUpdateQuestionnaire,
                                      BindingResult result, Model model){

        if (result.hasErrors()){
            model.addAttribute("completedUpdateQuestionnaire", completedUpdateQuestionnaire);
            model.addAttribute("isErrorOccurred", false);
            return "api/admin/updateQuestionnaire";
        }

        boolean isQuestionnaireSaved = questionnaireService.saveUpdatedQuestionnaire(completedUpdateQuestionnaire);

        if (!isQuestionnaireSaved){
            model.addAttribute("isErrorOccurred", true);
            model.addAttribute("completedUpdateQuestionnaire", completedUpdateQuestionnaire);
            return "api/admin/updateQuestionnaire";
        }

        return getAllQuestionnaires(model);
    }

    @GetMapping("/showAddQuestion")
    public String showAddQuestion(@ModelAttribute("questionnaire_id") Long questionnaireId, Model model){

        CreateQuestionnairePartsDto createQuestionnairePartsDto = new CreateQuestionnairePartsDto(
                new CreateQuestionnaireDto(),
                new CreateQuestionDto(),
                new CreateAnswerForQuestionDto(),
                Boolean.TRUE
        );

        createQuestionnairePartsDto.getCreateQuestionnaireDto().setTitle(
                questionnaireService.findQuestionnaireById(questionnaireId).getTitle());

        model.addAttribute("createQuestionnairePartsDto", createQuestionnairePartsDto);
        model.addAttribute("numberOfChoices", ChoiceUtils.getAllNumberOfChoicesList());
        model.addAttribute("numberOfAnswers", AnswerUtils.getMaxNumberOfAnswersList());
        model.addAttribute("isAvailableToEnterAnswers", false);

        return "api/admin/addQuestionToQuestionnaire";
    }

    @GetMapping("/deleteQuestion")
    public String deleteQuestion(@ModelAttribute("question_id") Long questionId,
                                 @ModelAttribute("questionnaire_id") Long questionnaireId,
                                 Model model){

        boolean isQuestionDeleted = questionService.deleteQuestion(questionId);

        if (isQuestionDeleted)
            return showUpdateQuestionnaireForm(questionnaireId, model);

        model.addAttribute("isErrorOccurred", true);
        return showUpdateQuestionnaireForm(questionnaireId, model);
    }

    @GetMapping("/deleteAnswerForQuestion")
    public String deleteAnswerForQuestion(@ModelAttribute("answerForQuestion_id") Long answerForQuestionId,
                                 @ModelAttribute("questionnaire_id") Long questionnaireId,
                                 Model model){

        boolean isAnswerForQuestionDeleted = answerForQuestionService.deleteAnswerForQuestion(answerForQuestionId);

        if (isAnswerForQuestionDeleted)
            return showUpdateQuestionnaireForm(questionnaireId, model);

        model.addAttribute("isErrorOccurred", true);
        return showUpdateQuestionnaireForm(questionnaireId, model);
    }
}
