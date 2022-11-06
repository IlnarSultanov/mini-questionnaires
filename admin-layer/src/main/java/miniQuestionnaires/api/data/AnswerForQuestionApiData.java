package miniQuestionnaires.api.data;

import lombok.Data;

@Data
public class AnswerForQuestionApiData {
    private Long id;
    private String answerText;
    private Long questionId;
}
