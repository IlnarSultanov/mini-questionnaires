package miniQuestionnaires.service.answerService;

import miniQuestionnaires.api.converter.AnswerApiConverter;
import miniQuestionnaires.api.data.*;
import miniQuestionnaires.persistence.layer.entity.*;
import miniQuestionnaires.persistence.layer.repository.AnswerForQuestionRepo;
import miniQuestionnaires.persistence.layer.repository.AnswerRepo;
import miniQuestionnaires.persistence.layer.repository.QuestionRepo;
import miniQuestionnaires.persistence.layer.repository.UserRepo;
import miniQuestionnaires.service.questionService.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final QuestionRepo questionRepo;
    private final QuestionService questionService;
    private final AnswerForQuestionRepo answerForQuestionRepo;
    private final AnswerRepo answerRepo;
    private final AnswerApiConverter answerApiConverter;

    @Autowired
    public AnswerServiceImpl(QuestionRepo questionRepo, AnswerForQuestionRepo answerForQuestionRepo,
                             QuestionService questionService, AnswerRepo answerRepo,
                             AnswerApiConverter answerApiConverter) {
        this.questionRepo = questionRepo;
        this.answerForQuestionRepo = answerForQuestionRepo;
        this.questionService = questionService;
        this.answerRepo = answerRepo;
        this.answerApiConverter = answerApiConverter;
    }


    @Override
    public void saveAnswer(User user, List<AnswerApiData> answersOnQuestionsApiData) {
        for (AnswerApiData answerApiData : answersOnQuestionsApiData){
            Question question = questionRepo.getById(answerApiData.getQuestionApiData().getId());

            for (AnswerForQuestionApiData answerForQuestionApiData : answerApiData.getAnswersForQuestionApiData()) {
                if (answerForQuestionApiData.getId() != null) {
                    AnswerForQuestion answerForQuestion = answerForQuestionRepo.getById(answerForQuestionApiData.getId());

                    Answer answer = new Answer();
                    answer.setUser(user);
                    answer.setAnswerForQuestion(answerForQuestion);
                    answer.setQuestion(question);

                    answerRepo.save(answer);
                }
            }
        }
    }

    @Override
    public Map<QuestionApiData, List<AnswerApiData>> getAnswersOnQuestionsByUser(Long userId, Long questionnaireId) {

        Map<QuestionApiData, List<AnswerApiData>> answersOnQuestionsByUser = new HashMap<>();

        List<QuestionApiData> questionsFromQuestionnaireApiData = questionService.findAllByQuestionnaireIdApiData(questionnaireId);

        for (QuestionApiData question : questionsFromQuestionnaireApiData){
            List<Answer> answersOnQuestionByUser = answerRepo.findAnswersByQuestionIdAndUserId(question.getId(), userId);

            answersOnQuestionsByUser.put(question,
                    answersOnQuestionByUser.stream()
                            .map(answerApiConverter::convertToShowApi)
                            .collect(Collectors.toList()));
        }

        return answersOnQuestionsByUser;
    }
}
