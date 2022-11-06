package miniQuestionnaires.persistence.layer.entity;

import lombok.Data;
import miniQuestionnaires.persistence.layer.entity.qustionHelper.NumberOfChoicesOnQuestion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "questions")
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String questionText;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private NumberOfChoicesOnQuestion numberOfChoices;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerForQuestion> answersForQuestion;
}
