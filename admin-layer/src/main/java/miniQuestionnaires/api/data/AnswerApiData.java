package miniQuestionnaires.api.data;

import lombok.Data;

import java.util.List;

@Data
public class AnswerApiData {
    private Long id;
    private UserApiData userApiData;
    private QuestionApiData questionApiData;
    private AnswerForQuestionApiData answerForQuestionApiData;
    private List<AnswerForQuestionApiData> answersForQuestionApiData;
}
