package miniQuestionnaires.dto.createQuestionnaire;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateQuestionDto {
    @Size(min = 5, max = 100, message = "Длина вопроса должна быть от 5 до 100 символов")
    private String questionText;

    @NotNull(message = "Селектор тип ответа не может быть пустым")
    private String numberOfChoices;

    @NotNull(message = "Селектор количество ответов не может быть пустым")
    private Integer numberOfAnswers;
}
