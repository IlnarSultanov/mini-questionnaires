package miniQuestionnaires.service.answerOnQuestionService;

import miniQuestionnaires.api.data.AnswerApiData;
import miniQuestionnaires.api.data.AnswerForQuestionApiData;
import miniQuestionnaires.api.data.QuestionApiData;
import miniQuestionnaires.api.data.UserApiData;
import miniQuestionnaires.dto.createQuestionnaire.CreateAnswerForQuestionDto;
import miniQuestionnaires.dto.updateQuestionnaire.UpdateQuestionDto;
import miniQuestionnaires.dto.updateQuestionnaire.UpdatedQuestionWithAnswers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnswerForQuestionService {
    /**
     * создает список ответов пользователя на вопрос
     * @return List<AnswerOnQuestionApiData>
     */
    List<AnswerApiData> createAnswersToQuestionsForUser(List<QuestionApiData> questionsFromQuestionnaire,
                                                        UserApiData userApiData);

    @Transactional
    void saveAnswerForQuestion(Long questionId, CreateAnswerForQuestionDto answerForQuestionDto);

    /**
     * нахождение всех вопросов для каждого ответа из анкеты для обновления данных
     * @param questionsDto
     * @return List<UpdatedQuestionWithAnswers>
     */
    List<UpdatedQuestionWithAnswers> getUpdatableQuestionsWithAnswers(List<UpdateQuestionDto> questionsDto);

    List<AnswerForQuestionApiData> findAllAnswersForQuestions();

    /**
     * удаление ответа для вопроса из БД
     * @return boolean isDeleted
     */
    @Transactional
    boolean deleteAnswerForQuestion(Long answerId);

    /**
     * сохранение изменных ответов из анкеты в БД
     */
    @Transactional
    void saveUpdatedAnswerForQuestion(List<UpdatedQuestionWithAnswers> updatedQuestionWithAnswersList);
}
