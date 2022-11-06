package miniQuestionnaires.service.answerService;

import miniQuestionnaires.api.data.*;
import miniQuestionnaires.dto.updateQuestionnaire.UpdatedQuestionWithAnswers;
import miniQuestionnaires.persistence.layer.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface AnswerService {
    /**
     * добавление нового ответа
     */
    @Transactional
    void saveAnswer(User user, List<AnswerApiData> answersOnQuestionsApiData);

    /**
     * получение мапы с вопросом и ответами пользователя на него
     */
    Map<QuestionApiData, List<AnswerApiData>> getAnswersOnQuestionsByUser(Long userId, Long questionnaireId);
}

