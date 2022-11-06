package miniQuestionnaires.dto.updateQuestionnaire;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
@AllArgsConstructor
public class UpdatedQuestionWithAnswers {
    private UpdateQuestionDto updateQuestionDto;
    private List<UpdateAnswerForQuestionDto> updateAnswerForQuestionDtoList;

    public UpdatedQuestionWithAnswers(){}
}
