package miniQuestionnaires.dto.updateQuestionnaire;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
@AllArgsConstructor
public class CompletedUpdateQuestionnaire {
    @Valid
    private UpdateQuestionnaireDto updatedQuestionnaire;

    private List<UpdatedQuestionWithAnswers> updatedQuestionWithAnswersList;

    public CompletedUpdateQuestionnaire(){}
}
