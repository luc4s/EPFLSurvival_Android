package godotinc.epflsurvival;

import android.app.Application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Luc4s on 25.05.2017.
 */

public class MyApp extends Application {
    private ArrayList<Question> questions;
    private ArrayList<Question> active_questions;

    public void setQuestions(ArrayList<Question> q){
        questions = q;
        active_questions = (ArrayList<Question>)q.clone();
    }

    public ArrayList<Question> getQuestions(){
        return questions;
    }

    public ArrayList<Question> getActiveQuestions(){
        return active_questions;
    }

    public void removeQuestion(Question q){
        active_questions.remove(q);
    }

    public void reset(){
        active_questions = (ArrayList<Question>)questions.clone();
    }



    public Question randomQuestion(int health, int social, int money, int academics, String tag, int date, int period){
        LinkedList<Question> selected = new LinkedList<>();


        for(Question q : getActiveQuestions()){
            if(tag != "" && q.getTag().equals(tag) ||
                    period > -1 && period == q.getPeriod() ||
                    q.getDate() != -1 && q.getDate() == date){
                selected.push(q);
                continue;
            }

            if(!sheckRanges(new int[]{health, social, money, academics},
                    q.getMins(),
                    q.getMaxs()))
                continue;

            selected.push(q);
        }

        if(selected.size() < 1)
            throw new RuntimeException("Aucune question disponible!");

        Random rng = new Random();
        int qIdx = rng.nextInt(selected.size());
        Question q = selected.get(qIdx);

        removeQuestion(q);

        if(q == null)
            throw new RuntimeException("Null question");

        return q;
    }

    private boolean sheckRanges(int[] vals, int[] mins, int[] maxs){
        for(int i = 0;i < vals.length;i++){
            if(vals[i] < mins[i] || vals[i] > maxs[i])
                return false;
        }
        return true;
    }
}
