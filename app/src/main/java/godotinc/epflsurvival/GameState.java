package godotinc.epflsurvival;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lis on 12.04.2017.
 */

public class GameState implements Serializable {
    private static final long serialVersionUID = 7526472295622776247L;

    private int academics,
                health,
                finances,
                social;

    private int date;
    private String nextQuestionTag;
    private QuestionsManager qMgr;

    public GameState(ArrayList<Question> questions, int academics, int health, int finances, int social, int date){
        this.academics = academics;
        this.health = health;
        this.finances = finances;
        this.social = social;

        this.date = date;
        qMgr = new QuestionsManager(questions);
    }

    public Question getRandomQuestion(){
        return qMgr.randomQuestion(health, social, finances, academics, nextQuestionTag, date, -1);
    }

    public void setNextQuestionTag(String tag){ nextQuestionTag = tag; }

    public int getAcademics() {
        return academics;
    }

    public int getHealth() {
        return health;
    }

    public int getFinances() {
        return finances;
    }

    public int getSocial() {
        return social;
    }

    public int getDate() {
        return date;
    }

    public void addAcademics(int academics) {
        this.academics += academics;
    }

    public void addHealth(int health) {
        this.health += health;
    }

    public void addFinances(int finances) {
        this.finances += finances;
    }

    public void addSocial(int social) {
        this.social += social;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void incrementDate(int delta){
        this.date += delta;
    }
}
