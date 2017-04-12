package godotinc.epflsurvival;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

import org.json.*;

public class SplashScreen extends AppCompatActivity {
    private static final int resId = R.raw.cards;

    private HashSet<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        questions = new HashSet<>();
        loadCards();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("QUESTIONS", questions);
        startActivity(intent);
        return true;
    }

    private void loadCards(){
        InputStream inputStream = getApplicationContext().getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load cards file");
        }

        JSONObject obj;
        try {
            obj = new JSONObject(text.toString());

            JSONObject cards = obj.getJSONObject("cards");

            for(Iterator<String> it = cards.keys();it.hasNext();){
                JSONObject card = cards.getJSONObject(it.next());

                Question.QuestionBuilder qBuilder = new Question.QuestionBuilder();
                qBuilder.setQuestionText(card.getString("question"));
                qBuilder.setDate(card.getInt("date"));
                qBuilder.setPeriod(card.getInt("period"));
                qBuilder.setImage(card.getString("image"));
                qBuilder.setTag(card.getString("tag"));
                qBuilder.setSection(card.getInt("section"));

                int min, max;
                JSONObject ranges = card.getJSONObject("ranges");
                min = (int)ranges.getJSONArray("academics").get(0);
                max = (int)ranges.getJSONArray("academics").get(1);
                qBuilder.setRange(Question.Range.Academics, min, max);

                min = (int)ranges.getJSONArray("health").get(0);
                max = (int)ranges.getJSONArray("health").get(1);
                qBuilder.setRange(Question.Range.Health, min, max);

                min = (int)ranges.getJSONArray("social").get(0);
                max = (int)ranges.getJSONArray("social").get(1);
                qBuilder.setRange(Question.Range.Social, min, max);

                min = (int)ranges.getJSONArray("finances").get(0);
                max = (int)ranges.getJSONArray("finances").get(1);
                qBuilder.setRange(Question.Range.Finances, min, max);

                JSONArray answers = card.getJSONArray("answers");
                for(int i = 0;i < answers.length();i++){
                    JSONObject answer = (JSONObject)answers.get(i);
                    JSONObject changes = answer.getJSONObject("changes");

                    String message = "";
                    if(answer.has("comment"))
                        message = answer.getString("comment");

                    qBuilder.addAnswer(answer.getString("answer"),
                                        answer.getString("suite"),
                                        message,
                                        changes.getInt("academics"),
                                        changes.getInt("social"),
                                        changes.getInt("health"),
                                        changes.getInt("finances"));
                }
                questions.add(qBuilder.build());
            }
        }
        catch (JSONException e){
            throw new RuntimeException("Malformed JSON");
        }
    }
}
