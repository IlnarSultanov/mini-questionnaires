package miniQuestionnaires.api.converter;

import miniQuestionnaires.api.data.AnswerApiData;
import miniQuestionnaires.persistence.layer.entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnswerApiConverter {

    private final UserApiConverter userApiConverter;
    private final QuestionApiConverter questionApiConverter;
    private final AnswerForQuestionApiConverter answerForQuestionApiConverter;

    @Autowired
    public AnswerApiConverter(UserApiConverter userApiConverter,
                              QuestionApiConverter questionApiConverter,
                              AnswerForQuestionApiConverter answerForQuestionApiConverter) {
        this.userApiConverter = userApiConverter;
        this.questionApiConverter = questionApiConverter;
        this.answerForQuestionApiConverter = answerForQuestionApiConverter;
    }

    public AnswerApiData convertToShowApi(Answer answer) {
        AnswerApiData answerApiData = new AnswerApiData();

        answerApiData.setId(answer.getId());
        answerApiData.setUserApiData(userApiConverter.convertToShowApi(answer.getUser()));
        answerApiData.setQuestionApiData(questionApiConverter.convertToShowApi(answer.getQuestion()));
        answerApiData.setAnswerForQuestionApiData(answerForQuestionApiConverter.convertToShowApi(answer.getAnswerForQuestion()));

        return answerApiData;
    }
}
