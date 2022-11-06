package miniQuestionnaires.service.questionService;

import miniQuestionnaires.api.data.QuestionApiData;
import miniQuestionnaires.dto.createQuestionnaire.CreateQuestionDto;
import miniQuestionnaires.dto.updateQuestionnaire.UpdateQuestionDto;
import miniQuestionnaires.dto.updateQuestionnaire.UpdatedQuestionWithAnswers;
import miniQuestionnaires.persistence.layer.entity.Question;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionService {
    /**
     * нахождение всех вопросов анкеты
     * @return List<QuestionApiData>
     */
    List<QuestionApiData> findAllByQuestionnaireIdApiData(Long questionnaireId);

    /**
     * нахождение всех вопросов анкеты
     * @return List<Question>
     */
    List<Question> findAllByQuestionnaireId(Long questionnaireId);

    /**
     * добавление нового вопроса
     * @return id нового вопроса
     */
    @Transactional
    Long saveQuestion(Long questionnaireId, CreateQuestionDto questionDto);

    /**
     * получение обновляемого списка вопросов обновляемой анкеты для изменных данных
     */
    List<UpdateQuestionDto> getUpdatableQuestionsByQuestionnaireId(Long questionnaireId);

    /**
     * удаление вопроса
     * @return @return boolean isDeleted
     */
    boolean deleteQuestion(Long questionId);

    /**
     * сохранение изменных вопросов из анкеты в БД
     *
     */
    @Transactional
    void saveUpdatedQuestion(List<UpdatedQuestionWithAnswers> updatedQuestionsWithAnswers);
}

