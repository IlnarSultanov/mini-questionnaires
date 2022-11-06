package miniQuestionnaires.api.converter;

import miniQuestionnaires.api.data.AnswerApiData;
import miniQuestionnaires.api.data.AnswerForQuestionApiData;
import miniQuestionnaires.dto.updateQuestionnaire.UpdateAnswerForQuestionDto;
import miniQuestionnaires.persistence.layer.entity.Answer;
import miniQuestionnaires.persistence.layer.entity.AnswerForQuestion;
import org.springframework.stereotype.Component;

@Component
public class AnswerForQuestionApiConverter {

    public AnswerForQuestionApiData convertToShowApi(AnswerForQuestion answerForQuestion) {
        AnswerForQuestionApiData answerForQuestionApiData = new AnswerForQuestionApiData();

        answerForQuestionApiData.setId(answerForQuestion.getId());
        answerForQuestionApiData.setAnswerText(answerForQuestion.getAnswerText());
        answerForQuestionApiData.setQuestionId(answerForQuestion.getQuestion().getId());

        return answerForQuestionApiData;
    }

    public UpdateAnswerForQuestionDto convertToUpdateApi(AnswerForQuestion answerForQuestion) {
        UpdateAnswerForQuestionDto updateAnswerForQuestionDto = new UpdateAnswerForQuestionDto();

        updateAnswerForQuestionDto.setId(answerForQuestion.getId());
        updateAnswerForQuestionDto.setAnswerText(answerForQuestion.getAnswerText());

        return updateAnswerForQuestionDto;
    }
}
