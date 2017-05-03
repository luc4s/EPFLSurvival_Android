package godotinc.epflsurvival;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;

import static android.util.Log.DEBUG;

public class QuestionActivity extends AppCompatActivity {
    private final static int COMMENT_WAIT_TIME = 1000;

    private Question q;
    private GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameState = (GameState)getIntent().getSerializableExtra("GAME_STATE");

        setContentView(R.layout.activity_question);

        //Setup custom transition
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        //Setup stats
        ProgressBar barHealth = (ProgressBar) findViewById(R.id.barHealth);
                barHealth.setProgress(gameState.getHealth());
        ProgressBar barSocial = (ProgressBar) findViewById(R.id.barSocial);
            barSocial.setProgress(gameState.getSocial());
        ProgressBar barFinances = (ProgressBar) findViewById(R.id.barFinances);
            barFinances.setProgress(gameState.getFinances());
        ProgressBar barAcademic = (ProgressBar) findViewById(R.id.barAcademic);
            barAcademic.setProgress(gameState.getAcademics());

        //Setup question
        try{
            q = gameState.getRandomQuestion();
        }
        catch(Exception e){
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("QUESTIONS", gameState.getAllQuestions());
            startActivity(intent);
            return;
        }

        ImageView image = (ImageView) findViewById(R.id.imageView7);
        String imageName = q.getImage();
        String[] parts = imageName.split("\\.");
        if(parts.length>0){
            imageName = parts[0];
        }
        //String imageName = "amphi_ce"; //marche
        System.out.println(imageName);
        int drawableID = getResources().getIdentifier(imageName, "drawable", getPackageName());
        System.out.println(drawableID);
        image.setImageResource(drawableID);

        TextView questionText = (TextView) findViewById(R.id.questionText);
        questionText.setText(q.getQuestionText());

        Question.QuestionAnswer[] qAnswers = q.getAnswers();

        Button button1 = (Button) findViewById(R.id.button8);
        Button button2 = (Button) findViewById(R.id.button7);
        Button button3 = (Button) findViewById(R.id.button2);
        Button button4 = (Button) findViewById(R.id.button);
        Button[] buttons = new Button[]{button1, button2, button3, button4};

        int i;
        for(i = 0;i < q.getAnswersCount();i++){
            buttons[i].setText(qAnswers[i].getText());
            buttons[i].setTextSize(10);

            buttons[i].setOnClickListener(new QuestionAnswerListener(gameState, qAnswers[i]));
        }

        for(;i < 4;i++)
            buttons[i].setVisibility(View.INVISIBLE);
    }

    public class QuestionAnswerListener implements View.OnClickListener {
        private Question.QuestionAnswer qa;
        private GameState gState;

        public QuestionAnswerListener(GameState gState, Question.QuestionAnswer qa){
            this.qa = qa;
            this.gState = gState;
        }

        @Override
        public void onClick(View v){
            gState.addAcademics(qa.getAcademics());
            gState.addSocial(qa.getSocial());
            gState.addFinances(qa.getFinances());
            gState.addHealth(qa.getHealth());

            boolean comment = qa.getMessage().length() > 0;
            if(comment){
                v.setVisibility(View.GONE);
                ((Button) findViewById(R.id.button)).setEnabled(false);
                ((Button) findViewById(R.id.button2)).setEnabled(false);;
                ((Button) findViewById(R.id.button7)).setEnabled(false);
                ((Button) findViewById(R.id.button8)).setEnabled(false);

                GridLayout layout = ((GridLayout) findViewById(R.id.gridLayoutButtons));
                int idx = layout.indexOfChild(v);
                layout.removeView(v);

                TextView message = new TextView(v.getContext());
                message.setWidth(v.getWidth());
                message.setHeight(v.getHeight());
                message.setGravity(17);

                message.setText(qa.getMessage());
                layout.addView(message, idx);
            }



            if(gState.isGameOver()){
                final Intent intent = new Intent(v.getContext(), GameOverActivity.class);
                intent.putExtra("QUESTIONS", gState.getAllQuestions());

                if(!comment){
                    startActivity(intent);
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, COMMENT_WAIT_TIME);
            }
            else{
                final Intent intent = new Intent(v.getContext(), QuestionActivity.class);
                intent.putExtra("GAME_STATE", gState);

                if(!comment){
                    startActivity(intent);
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, COMMENT_WAIT_TIME);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("GAME_STATE", gameState);
        startActivity(intent);
    }
}
