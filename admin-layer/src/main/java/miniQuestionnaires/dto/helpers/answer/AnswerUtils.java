package miniQuestionnaires.dto.helpers.answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerUtils {
    private static final int MAX_NUMBER_OF_ANSWERS = 10;

    public static List<Integer> getMaxNumberOfAnswersList(){
        List<Integer> numberOfAnswers = new ArrayList<>();
        for (int i = 1; i <= MAX_NUMBER_OF_ANSWERS; i++){
            numberOfAnswers.add(i);
        }
        return numberOfAnswers;
    }

    public static List<Integer> getSelectedNumberOfAnswersList(int selectedSize){
        List<Integer> numberOfAnswers = new ArrayList<>();
        for (int i = 0; i < selectedSize; i++){
            numberOfAnswers.add(i);
        }
        return numberOfAnswers;
    }
}
