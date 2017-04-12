package godotinc.epflsurvival;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by lis on 12.04.2017.
 */

public class QuestionsManager {
    private ArrayList<Question> questions;

    public QuestionsManager(ArrayList<Question> questions){
        this.questions = questions;
    }
}
