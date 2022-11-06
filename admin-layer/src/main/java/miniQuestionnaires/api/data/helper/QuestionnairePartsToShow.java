package miniQuestionnaires.api.data.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import miniQuestionnaires.api.data.AnswerApiData;
import miniQuestionnaires.api.data.AnswerForQuestionApiData;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionnairePartsToShow {
    private List<AnswerForQuestionApiData> answersForQuestions;
    private List<AnswerApiData> answersOnQuestionsApiData;
}
