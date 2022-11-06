package miniQuestionnaires.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import miniQuestionnaires.dto.createQuestionnaire.CreateAnswerForQuestionDto;
import miniQuestionnaires.dto.createQuestionnaire.CreateQuestionDto;
import miniQuestionnaires.dto.createQuestionnaire.CreateQuestionnaireDto;

import javax.validation.Valid;

@Data
@AllArgsConstructor
public class CreateQuestionnairePartsDto {
    @Valid
    private CreateQuestionnaireDto createQuestionnaireDto;
    @Valid
    private CreateQuestionDto createQuestionDto;

    private CreateAnswerForQuestionDto createAnswerForQuestionDto;

    private Boolean isQuestionnaireTitleFixed;
}
