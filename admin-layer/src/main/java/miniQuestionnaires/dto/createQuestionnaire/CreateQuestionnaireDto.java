package miniQuestionnaires.dto.createQuestionnaire;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CreateQuestionnaireDto {
    @Size(min = 2, max = 50, message = "Длина названия должна быть от 2 до 50 символов")
    private String title;
}
