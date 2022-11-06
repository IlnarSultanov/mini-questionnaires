package miniQuestionnaires.dto.helpers.choice;

import java.util.ArrayList;
import java.util.List;

public class ChoiceUtils {
    public static List<NumberOfChoices> getAllNumberOfChoicesList(){
        List<NumberOfChoices> numberOfChoices = new ArrayList<>();
        numberOfChoices.add(NumberOfChoices.ONE);
        numberOfChoices.add(NumberOfChoices.MANY);
        return numberOfChoices;
    }
}
