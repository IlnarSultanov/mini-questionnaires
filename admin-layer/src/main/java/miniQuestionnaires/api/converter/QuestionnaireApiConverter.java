package miniQuestionnaires.api.converter;

import miniQuestionnaires.api.data.QuestionnaireApiData;
import miniQuestionnaires.api.data.UserApiData;
import miniQuestionnaires.dto.updateQuestionnaire.UpdateQuestionnaireDto;
import miniQuestionnaires.persistence.layer.entity.Questionnaire;
import miniQuestionnaires.persistence.layer.entity.User;
import org.springframework.stereotype.Component;

@Component
public class QuestionnaireApiConverter {

    public QuestionnaireApiData convertToShowApi(Questionnaire questionnaire) {
        QuestionnaireApiData questionnaireApiData = new QuestionnaireApiData();

        questionnaireApiData.setId(questionnaire.getId());
        questionnaireApiData.setTitle(questionnaire.getTitle());

        return questionnaireApiData;
    }

    public UpdateQuestionnaireDto convertToUpdateApi(Questionnaire questionnaire) {
        UpdateQuestionnaireDto updatableQuestionnaire = new UpdateQuestionnaireDto();

        updatableQuestionnaire.setId(questionnaire.getId());
        updatableQuestionnaire.setTitle(questionnaire.getTitle());

        return updatableQuestionnaire;
    }
}
