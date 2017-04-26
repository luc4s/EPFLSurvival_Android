package godotinc.epflsurvival;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by lis on 12.04.2017.
 */

public class QuestionsManager implements Serializable {
    private static final long serialVersionUID = 1526472295622776147L;

    private ArrayList<Question> questions;

    public QuestionsManager(ArrayList<Question> questions){
        if(questions == null)
            throw new IllegalArgumentException("Null question list");

        this.questions = questions;
    }

    public Question randomQuestion(int health, int social, int money, int academics, String tag, int date, int period){
        LinkedList<Question> selected = new LinkedList<>();

        for(Question q : questions){
            if(tag != "" && q.getTag().equals(tag) ||
                    period > 0 && q.getPeriod() != period ||
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

        questions.remove(qIdx);
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
