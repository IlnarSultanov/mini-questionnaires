package miniQuestionnaires.api.converter;

import miniQuestionnaires.api.data.QuestionApiData;
import miniQuestionnaires.api.data.QuestionnaireApiData;
import miniQuestionnaires.dto.helpers.choice.NumberOfChoices;
import miniQuestionnaires.dto.updateQuestionnaire.UpdateQuestionDto;
import miniQuestionnaires.dto.updateQuestionnaire.UpdateQuestionnaireDto;
import miniQuestionnaires.persistence.layer.entity.Question;
import miniQuestionnaires.persistence.layer.entity.Questionnaire;
import org.springframework.stereotype.Component;

@Component
public class QuestionApiConverter {

    public QuestionApiData convertToShowApi(Question question) {
        QuestionApiData questionApiData = new QuestionApiData();

        questionApiData.setId(question.getId());
        questionApiData.setQuestionText(question.getQuestionText());
        questionApiData.setNumberOfChoices(getTypeNumberOfChoices(question));
        questionApiData.setQuestionnaireId(question.getQuestionnaire().getId());

        return questionApiData;
    }

    public UpdateQuestionDto convertToUpdateApi(Question question) {
        UpdateQuestionDto updatableQuestionDto = new UpdateQuestionDto();

        updatableQuestionDto.setId(question.getId());
        updatableQuestionDto.setQuestionText(question.getQuestionText());

        return updatableQuestionDto;
    }

    private NumberOfChoices getTypeNumberOfChoices(Question question) {
        return question.getNumberOfChoices().name().equals(NumberOfChoices.ONE.name()) ?
                NumberOfChoices.ONE : NumberOfChoices.MANY;
    }
}
