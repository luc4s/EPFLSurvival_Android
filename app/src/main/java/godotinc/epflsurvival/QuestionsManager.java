package godotinc.epflsurvival;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by lis on 12.04.2017.
 */

public class QuestionsManager implements Serializable {
    private static final long serialVersionUID = 1526472295622776147L;
    private MyApp appState;

    public QuestionsManager(MyApp appState){
        this.appState = appState;
    }
}
