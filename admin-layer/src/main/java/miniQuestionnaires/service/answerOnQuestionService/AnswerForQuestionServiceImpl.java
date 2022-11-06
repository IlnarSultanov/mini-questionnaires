package miniQuestionnaires.service.answerOnQuestionService;

import miniQuestionnaires.api.converter.AnswerForQuestionApiConverter;
import miniQuestionnaires.api.data.AnswerApiData;
import miniQuestionnaires.api.data.AnswerForQuestionApiData;
import miniQuestionnaires.api.data.QuestionApiData;
import miniQuestionnaires.api.data.UserApiData;
import miniQuestionnaires.dto.createQuestionnaire.CreateAnswerForQuestionDto;
import miniQuestionnaires.dto.updateQuestionnaire.UpdateAnswerForQuestionDto;
import miniQuestionnaires.dto.updateQuestionnaire.UpdateQuestionDto;
import miniQuestionnaires.dto.updateQuestionnaire.UpdatedQuestionWithAnswers;
import miniQuestionnaires.persistence.layer.entity.AnswerForQuestion;
import miniQuestionnaires.persistence.layer.entity.Question;
import miniQuestionnaires.persistence.layer.repository.AnswerForQuestionRepo;
import miniQuestionnaires.persistence.layer.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnswerForQuestionServiceImpl implements AnswerForQuestionService {

    private final AnswerForQuestionRepo answerForQuestionRepo;
    private final QuestionRepo questionRepo;
    private final AnswerForQuestionApiConverter answerForQuestionApiConverter;

    @Autowired
    public AnswerForQuestionServiceImpl(AnswerForQuestionRepo answerForQuestionRepo,
                                        QuestionRepo questionRepo, AnswerForQuestionApiConverter answerForQuestionApiConverter) {
        this.answerForQuestionRepo = answerForQuestionRepo;
        this.questionRepo = questionRepo;
        this.answerForQuestionApiConverter = answerForQuestionApiConverter;
    }

    public void saveAnswerForQuestion(Long questionId, CreateAnswerForQuestionDto answerForQuestionDto) {
        List<String> answersText = Arrays.stream(answerForQuestionDto.getAnswerText()
                        .split(","))
                .collect(Collectors.toList());

        Question question = questionRepo.getById(questionId);

        for (String answerText : answersText){
            AnswerForQuestion answer = new AnswerForQuestion();
            answer.setAnswerText(answerText);
            answer.setQuestion(question);
            answerForQuestionRepo.save(answer);
        }
    }

    @Override
    public List<UpdatedQuestionWithAnswers> getUpdatableQuestionsWithAnswers(List<UpdateQuestionDto> questionsDto) {
        List<UpdatedQuestionWithAnswers> updatableQuestionWithAnswersList = new ArrayList<>();

        for (UpdateQuestionDto questionDto : questionsDto){
            List<AnswerForQuestion> answersForQuestionDto = answerForQuestionRepo.findAllByQuestionId(questionDto.getId());

            updatableQuestionWithAnswersList.add(new UpdatedQuestionWithAnswers(
                    questionDto,
                    answersForQuestionDto.stream()
                            .map(answerForQuestionApiConverter::convertToUpdateApi)
                            .collect(Collectors.toList()))
            );
        }

        return updatableQuestionWithAnswersList;
    }

    @Override
    public List<AnswerApiData> createAnswersToQuestionsForUser(List<QuestionApiData> questionsFromQuestionnaire,
                                                               UserApiData userApiData) {
        List<AnswerApiData> answersList = new ArrayList<>();

        for (QuestionApiData questionApiData : questionsFromQuestionnaire){
            AnswerApiData answer = new AnswerApiData();
            answer.setQuestionApiData(questionApiData);
            answer.setUserApiData(userApiData);
            answer.setAnswersForQuestionApiData(new ArrayList<>());
            answersList.add(answer);
        }

        return answersList;
    }

    @Override
    public List<AnswerForQuestionApiData> findAllAnswersForQuestions() {
        return answerForQuestionRepo.findAll().stream()
                .map(answerForQuestionApiConverter::convertToShowApi)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteAnswerForQuestion(Long answerId) {
        Optional<AnswerForQuestion> result = answerForQuestionRepo.findById(answerId);

        if (result.isPresent()){
            answerForQuestionRepo.delete(result.get());
            return true;
        }
        return false;
    }

    @Override
    public void saveUpdatedAnswerForQuestion(List<UpdatedQuestionWithAnswers> updatedQuestionWithAnswersList) {

        for (UpdatedQuestionWithAnswers updatedQuestionWithAnswers : updatedQuestionWithAnswersList){
            for (UpdateAnswerForQuestionDto updatedAnswerForQuestion : updatedQuestionWithAnswers.getUpdateAnswerForQuestionDtoList()){

                Optional<AnswerForQuestion> result = answerForQuestionRepo.findById(updatedAnswerForQuestion.getId());

                if (result.isPresent()){
                    AnswerForQuestion answerForQuestion = result.get();

                    if (!answerForQuestion.getAnswerText().equals(updatedAnswerForQuestion.getAnswerText())){
                        answerForQuestion.setAnswerText(updatedAnswerForQuestion.getAnswerText());
                        answerForQuestionRepo.save(answerForQuestion);
                    }
                }
            }
        }
    }


}
