package godotinc.epflsurvival;

import java.io.Serializable;

/**
 * Created by lis on 12.04.2017.
 */

public class Question implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;

    private int mins[];
    private int maxs[];

    public enum Range{
        Academics,
        Finances,
        Health,
        Social
    }

    private String  questionText,
                    tag,
                    image;

    private int date,
                period,
                section;

    private QuestionAnswer[] answers;
    private int answersCount;


    public Question(String text, String tag,
                    String image, int date, int period,
                    int section, QuestionAnswer[] answers, int answersCount,
                    int[] mins, int[] maxs){
        this.questionText = text;
        this.tag = tag;
        this.image = image;
        this.date = date;
        this.period = period;
        this.section = section;
        this.answers = answers.clone();
        this.mins = mins.clone();
        this.maxs = maxs.clone();
        this.answersCount = answersCount;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getTag() {
        return tag;
    }

    public String getImage() {
        return image;
    }

    public int getDate() {
        return date;
    }

    public int getPeriod() {
        return period;
    }

    public int getSection() {
        return section;
    }

    public QuestionAnswer[] getAnswers() {
        return answers;
    }

    public int getAnswersCount(){
        return answersCount;
    }

    public int[] getMins() {
        return mins;
    }

    public int[] getMaxs() {
        return maxs;
    }


    /**
     * Class to build questions
     */
    public static class QuestionBuilder implements Serializable {
        private static final long serialVersionUID = 8526472295622776147L;

        private String  questionText,
                tag,
                image;

        private int date,
                period,
                section;

        private QuestionAnswer[] answers;
        private int answersCount;

        private int mins[];
        private int maxs[];

        public QuestionBuilder(){
            answers = new QuestionAnswer[4];
            mins = new int[Range.values().length];
            maxs = new int[Range.values().length];
            answersCount = 0;
        }

        public Question build(){
            return new Question(questionText, tag, image, date,
                                period, section, answers, answersCount,
                                mins, maxs);
        }

        public void addAnswer(String text, String suite, String message,
                              int a, int s, int h, int f){
            if(answersCount > 4)
                return;

            QuestionAnswer qa = new QuestionAnswer(text, message, suite, a, s, h, f);
            answers[answersCount++] = qa;
        }

        public void setRange(Range range, int min, int max){
            int idx = range.ordinal();
            mins[idx] = min;
            maxs[idx] = max;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public void setSection(int section) {
            this.section = section;
        }
    }

    /**
     *      Question answer
     */
    public static class QuestionAnswer implements Serializable {
        private static final long serialVersionUID = 6526472295622776147L;

        String text,
                message,
                suite;

        int health,
            social,
            finances,
            academics;

        public QuestionAnswer(String text, String message, String suite, int health, int social, int finances, int academics) {
            this.text = text;
            this.message = message;
            this.suite = suite;
            this.health = health;
            this.social = social;
            this.finances = finances;
            this.academics = academics;
        }

        public String getText() {
            return text;
        }

        public String getMessage(){
            return message;
        }

        public String getSuite() {
            return suite;
        }

        public int getHealth() {
            return health;
        }

        public int getSocial() {
            return social;
        }

        public int getFinances() {
            return finances;
        }

        public int getAcademics() {
            return academics;
        }
    }
}