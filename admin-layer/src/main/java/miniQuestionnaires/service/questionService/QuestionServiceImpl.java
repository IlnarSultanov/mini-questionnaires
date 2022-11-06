package miniQuestionnaires.service.questionService;

import miniQuestionnaires.api.converter.QuestionApiConverter;
import miniQuestionnaires.api.data.QuestionApiData;
import miniQuestionnaires.dto.createQuestionnaire.CreateQuestionDto;
import miniQuestionnaires.dto.updateQuestionnaire.UpdateQuestionDto;
import miniQuestionnaires.dto.updateQuestionnaire.UpdatedQuestionWithAnswers;
import miniQuestionnaires.persistence.layer.entity.Question;
import miniQuestionnaires.persistence.layer.entity.qustionHelper.NumberOfChoicesOnQuestion;
import miniQuestionnaires.persistence.layer.repository.QuestionRepo;
import miniQuestionnaires.persistence.layer.repository.QuestionnaireRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionnaireRepo questionnaireRepo;
    private final QuestionRepo questionRepo;
    private final QuestionApiConverter questionApiConverter;

    @Autowired
    public QuestionServiceImpl(QuestionnaireRepo questionnaireRepo, QuestionRepo questionRepo, QuestionApiConverter questionApiConverter) {
        this.questionnaireRepo = questionnaireRepo;
        this.questionRepo = questionRepo;
        this.questionApiConverter = questionApiConverter;
    }

    @Override
    public List<QuestionApiData> findAllByQuestionnaireIdApiData(Long questionnaireId) {
        return questionRepo.findAllByQuestionnaireIdOrderById(questionnaireId).stream()
                .map(questionApiConverter::convertToShowApi)
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> findAllByQuestionnaireId(Long questionnaireId) {
        return questionRepo.findAllByQuestionnaireIdOrderById(questionnaireId);
    }

    @Override
    public Long saveQuestion(Long questionnaireId, CreateQuestionDto questionDto) {
        Question question = new Question();
        question.setQuestionText(questionDto.getQuestionText());
        question.setNumberOfChoices((questionDto.getNumberOfChoices().equals(
                NumberOfChoicesOnQuestion.ONE.name()) ? NumberOfChoicesOnQuestion.ONE :
                NumberOfChoicesOnQuestion.MANY
        ));
        question.setQuestionnaire(questionnaireRepo.getById(questionnaireId));
        questionRepo.save(question);

        return question.getId();
    }

    @Override
    public List<UpdateQuestionDto> getUpdatableQuestionsByQuestionnaireId(Long questionnaireId) {
        return questionRepo.findAllByQuestionnaireIdOrderById(questionnaireId).stream()
                .map(questionApiConverter::convertToUpdateApi)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteQuestion(Long questionId) {
        Optional<Question> result = questionRepo.findById(questionId);

        if (result.isPresent()){
            questionRepo.delete(result.get());
            return true;
        }
        return false;
    }

    @Override
    public void saveUpdatedQuestion(List<UpdatedQuestionWithAnswers> updatedQuestionsWithAnswers) {
        for (UpdatedQuestionWithAnswers updatedQuestionWithAnswer : updatedQuestionsWithAnswers){
            UpdateQuestionDto updatedQuestion = updatedQuestionWithAnswer.getUpdateQuestionDto();

            Optional<Question> result = questionRepo.findById(updatedQuestion.getId());
            if (result.isPresent()){
                Question question = result.get();

                if (!question.getQuestionText().equals(updatedQuestion.getQuestionText())){
                    question.setQuestionText(updatedQuestion.getQuestionText());
                    questionRepo.save(question);
                }
            }
        }
    }

}
