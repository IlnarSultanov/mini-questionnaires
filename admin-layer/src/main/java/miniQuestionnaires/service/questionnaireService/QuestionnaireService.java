package miniQuestionnaires.service.questionnaireService;

import miniQuestionnaires.api.data.QuestionnaireApiData;
import miniQuestionnaires.dto.createQuestionnaire.CreateAnswerForQuestionDto;
import miniQuestionnaires.dto.createQuestionnaire.CreateQuestionDto;
import miniQuestionnaires.dto.createQuestionnaire.CreateQuestionnaireDto;
import miniQuestionnaires.dto.updateQuestionnaire.CompletedUpdateQuestionnaire;
import miniQuestionnaires.dto.updateQuestionnaire.UpdateQuestionnaireDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionnaireService {
    /**
     * нахождение всех созданных анкет
     */
    List<QuestionnaireApiData> findAll();

    /**
     * нахождение всех анкет, которые еще не заполнял пользователь
     */
    List<QuestionnaireApiData> findAllNonCompletedQuestionnairesByUser(Long userId);

    /**
     * нахождение анкеты по id
     */
    QuestionnaireApiData findQuestionnaireById(Long questionnaireId);

    /**
     * добавление новой анкеты
     */
    @Transactional
    void saveQuestionnaire(CreateQuestionnaireDto registerQuestionnaireDto, CreateQuestionDto questionDto,
                           CreateAnswerForQuestionDto answerDto);

    /**
     * валидация новой анкеты
     * @return String errorMessage
     */
    String validateNewQuestionnaire(CreateQuestionnaireDto createQuestionnaireDto);

    /**
     * получение обновляемой анкеты для изменения данных
     */
    UpdateQuestionnaireDto getUpdatableQuestionnaireById(Long questionnaireId);

    /**
     * удаление анкеты
     */
    @Transactional
    void deleteQuestionnaire(Long questionnaireId);

    /**
     * сохранение изменных данных анкеты в БД
     * @return boolean isSaved
     */
    @Transactional
    boolean saveUpdatedQuestionnaire(CompletedUpdateQuestionnaire completedUpdateQuestionnaire);
}

