package miniQuestionnaires.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import miniQuestionnaires.dto.helpers.choice.NumberOfChoices;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionApiData {
    private Long id;
    private String questionText;
    private NumberOfChoices numberOfChoices;
    private Long questionnaireId;
    private List<AnswerForQuestionApiData> answersForQuestionsApiData;
}
