package miniQuestionnaires.dto.helpers.choice;

public enum NumberOfChoices {
    ONE("ОДИН ОТВЕТ"),
    MANY("НЕСКОЛЬКО ОТВЕТОВ");
    private final String translation;

    NumberOfChoices(String translation) {
        this.translation = translation;
    }


    public String getTranslation() {
        return translation;
    }
}
