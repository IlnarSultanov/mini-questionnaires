package miniQuestionnaires.service.questionnaireService;

import miniQuestionnaires.api.converter.QuestionnaireApiConverter;
import miniQuestionnaires.api.data.QuestionnaireApiData;
import miniQuestionnaires.dto.createQuestionnaire.CreateAnswerForQuestionDto;
import miniQuestionnaires.dto.createQuestionnaire.CreateQuestionDto;
import miniQuestionnaires.dto.createQuestionnaire.CreateQuestionnaireDto;
import miniQuestionnaires.dto.updateQuestionnaire.CompletedUpdateQuestionnaire;
import miniQuestionnaires.dto.updateQuestionnaire.UpdateQuestionnaireDto;
import miniQuestionnaires.persistence.layer.entity.Questionnaire;
import miniQuestionnaires.persistence.layer.repository.AnswerForQuestionRepo;
import miniQuestionnaires.persistence.layer.repository.QuestionnaireRepo;
import miniQuestionnaires.service.answerOnQuestionService.AnswerForQuestionService;
import miniQuestionnaires.service.answerService.AnswerService;
import miniQuestionnaires.service.questionService.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService{

    private final QuestionnaireRepo questionnaireRepo;
    private final QuestionService questionService;
    private final AnswerForQuestionService answerForQuestionService;
    private final QuestionnaireApiConverter questionnaireApiConverter;

    @Autowired
    public QuestionnaireServiceImpl(QuestionnaireRepo questionnaireRepo, QuestionService questionService,
                                    AnswerForQuestionService answerForQuestionService,
                                    QuestionnaireApiConverter questionnaireApiConverter) {
        this.questionnaireRepo = questionnaireRepo;
        this.answerForQuestionService = answerForQuestionService;
        this.questionService = questionService;
        this.questionnaireApiConverter = questionnaireApiConverter;
    }

    @Override
    public List<QuestionnaireApiData> findAll() {
        return questionnaireRepo.findAll().stream()
                .map(questionnaireApiConverter::convertToShowApi)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionnaireApiData> findAllNonCompletedQuestionnairesByUser(Long userId) {
        List<Questionnaire> allQuestionnaires = questionnaireRepo.findAll();
        List<Questionnaire> completedQuestionnairesByUser = questionnaireRepo.findCompletedQuestionnairesByUser(userId);

        allQuestionnaires.removeAll(completedQuestionnairesByUser);

        return allQuestionnaires.stream()
                .map(questionnaireApiConverter::convertToShowApi)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionnaireApiData findQuestionnaireById(Long questionnaireId) {
        return questionnaireApiConverter.convertToShowApi(
                questionnaireRepo.getById(questionnaireId));
    }

    @Override
    public String validateNewQuestionnaire(CreateQuestionnaireDto createQuestionnaireDto) {
        String message = "";
        String title = createQuestionnaireDto.getTitle();

        if (title != null) {
            if (questionnaireRepo.findQuestionnaireByTitle(title).isPresent()) {
                message = "Анкета с названием \"" + title + "\" уже существует";
            }
        }
        return message;
    }

    @Override
    public UpdateQuestionnaireDto getUpdatableQuestionnaireById(Long questionnaireId) {
        return questionnaireApiConverter.convertToUpdateApi(
                questionnaireRepo.getById(questionnaireId));
    }

    @Override
    public void saveQuestionnaire(CreateQuestionnaireDto questionnaireDto,
                                  CreateQuestionDto questionDto,
                                  CreateAnswerForQuestionDto answerDto) {

        Questionnaire questionnaire;

        Optional<Questionnaire> optionalQuestionnaire = questionnaireRepo.findQuestionnaireByTitle(
                questionnaireDto.getTitle());

        if (!optionalQuestionnaire.isPresent()) {
            questionnaire = new Questionnaire();
            questionnaire.setTitle(questionnaireDto.getTitle());
            questionnaireRepo.save(questionnaire);
        }
        else {
            questionnaire = optionalQuestionnaire.get();
        }

        Long questionId = questionService.saveQuestion(questionnaire.getId(), questionDto);
        answerForQuestionService.saveAnswerForQuestion(questionId, answerDto);
    }

    @Override
    public void deleteQuestionnaire(Long questionnaireId) {
        questionnaireRepo.deleteById(questionnaireId);
    }

    @Override
    public boolean saveUpdatedQuestionnaire(CompletedUpdateQuestionnaire completedUpdateQuestionnaire) {
        Optional<Questionnaire> result = questionnaireRepo.findById(
                completedUpdateQuestionnaire.getUpdatedQuestionnaire().getId());

        if (result.isPresent()) {
            Questionnaire questionnaire = result.get();

            String newTitle = completedUpdateQuestionnaire.getUpdatedQuestionnaire().getTitle();
            if (!questionnaire.getTitle().equals(newTitle)){
                questionnaire.setTitle(newTitle);
                questionnaireRepo.save(questionnaire);
            }

            questionService.saveUpdatedQuestion(completedUpdateQuestionnaire.getUpdatedQuestionWithAnswersList());
            answerForQuestionService.saveUpdatedAnswerForQuestion(completedUpdateQuestionnaire.getUpdatedQuestionWithAnswersList());

            return true;
        }
        return false;
    }
}
