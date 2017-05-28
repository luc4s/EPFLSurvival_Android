package godotinc.epflsurvival;

import android.util.Log;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lis on 12.04.2017.
 */

public class GameState implements Serializable {
    private static final long serialVersionUID = 7526472295622776247L;

    private static final int CARDS_PER_GAME = 30;
    private static final int PERIODS_COUNT = 11;

    private int academics,
                health,
                finances,
                social;


    /*
    0 début de semestre I
    1 milieu de semestre I
    2 fin de semestre I
    3 révisions I
    4 examens I
    5 vacances
    6 début de semestre II
    7 milieu de semestre II
    8 fin de semestre II
    9 révisions II
    10 examen II
     */
    private int currentPeriod;
    private String nextQuestionTag;
    private int date;


    public GameState(int academics, int health, int finances, int social, int date){
        this.academics = academics;
        this.health = health;
        this.finances = finances;
        this.social = social;

        this.date = date;
        currentPeriod = 0;
    }

    public void setNextQuestionTag(String tag){ nextQuestionTag = tag; }

    public boolean isGameOver(){
        return !(academics > 0 &&
                health > 0 &&
                finances > 0 &&
                social > 0) || date >= CARDS_PER_GAME;
    }

    public int getCurrentPeriod(){
        return date / (CARDS_PER_GAME / PERIODS_COUNT);
    }

    public String getNextQuestionTag(){
        return nextQuestionTag;
    }

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

    public int getDateAndInc(){
        return date++;
    }

    public void addAcademics(int academics) {
        this.academics += academics;
        if(this.academics > 100)
            this.academics = 100;
    }

    public void addHealth(int health) {
        this.health += health;
        if(this.health > 100)
            this.health = 100;
    }

    public void addFinances(int finances) {
        this.finances += finances;
        if(this.finances > 100)
            this.finances = 100;
    }

    public void addSocial(int social) {
        this.social += social;
        if(this.social > 100)
            this.social = 100;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void incrementDate(int delta){
        this.date += delta;
    }
}
