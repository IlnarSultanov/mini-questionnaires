package miniQuestionnaires.dto.updateQuestionnaire;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateQuestionDto {
    private Long id;

    @Size(min = 5, max = 100, message = "Длина вопроса должна быть от 5 до 100 символов")
    private String questionText;
}
