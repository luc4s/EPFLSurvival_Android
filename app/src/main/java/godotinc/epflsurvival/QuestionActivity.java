package godotinc.epflsurvival;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setProgress(gameState.getDate() * (100/30));

        ((TextView) findViewById(R.id.textView2)).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.textView3)).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.textView4)).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.textView5)).setVisibility(View.INVISIBLE);

        //Setup question
        Question q;
        try{
            MyApp appState = ((MyApp)getApplicationContext());

             q = appState.randomQuestion(gameState.getHealth(), gameState.getSocial(), gameState.getFinances(), gameState.getAcademics(), gameState.getNextQuestionTag(), gameState.getDateAndInc(), gameState.getCurrentPeriod());
        }
        catch(Exception e){
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
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
        //System.out.println(imageName);
        int drawableID = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if(drawableID == 0){
            drawableID = getResources().getIdentifier("logo2", "drawable", getPackageName());
        }
        //System.out.println(drawableID);
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
            buttons[i].setTextSize(18);
            if(qAnswers[i].getText().length() > 100)
                buttons[i].setTextSize(16);
            else if(qAnswers[i].getText().length() > 150)
                buttons[i].setTextSize(14);

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

        private String diffToString(int diff){
            String toReturn = Integer.toString(diff);
            if(diff==0){
                return "";
            }
            else if(diff > 0){
                toReturn = "+" + toReturn;
            }
            return toReturn;
        }

        @Override
        public void onClick(View v){

            int diffAcademics = qa.getAcademics();
            int diffSocial = qa.getSocial();
            int diffFinances = qa.getFinances();
            int diffHealth = qa.getHealth();

            gState.addAcademics(diffAcademics);
            gState.addSocial(diffSocial);
            gState.addFinances(diffFinances);
            gState.addHealth(diffHealth);



            ((TextView) findViewById(R.id.textView2)).setText(diffToString(diffFinances));
            ((TextView) findViewById(R.id.textView3)).setText(diffToString(diffSocial));
            ((TextView) findViewById(R.id.textView4)).setText(diffToString(diffAcademics));
            ((TextView) findViewById(R.id.textView5)).setText(diffToString(diffHealth));



            ProgressBar barHealth = (ProgressBar) findViewById(R.id.barHealth);
            ProgressBarAnimation anim = new ProgressBarAnimation(barHealth, gameState.getHealth()-diffHealth, gameState.getHealth());
            anim.setDuration(1000);
            barHealth.startAnimation(anim);

            ProgressBar barAcademic = (ProgressBar) findViewById(R.id.barAcademic);
            anim = new ProgressBarAnimation(barAcademic, gameState.getAcademics()-diffAcademics, gameState.getAcademics());
            anim.setDuration(1000);
            barAcademic.startAnimation(anim);

            ProgressBar barFinances = (ProgressBar) findViewById(R.id.barFinances);
            anim = new ProgressBarAnimation(barFinances, gameState.getFinances()-diffFinances, gameState.getFinances());
            anim.setDuration(1000);
            barFinances.startAnimation(anim);

            ProgressBar barSocial = (ProgressBar) findViewById(R.id.barSocial);
            anim = new ProgressBarAnimation(barSocial, gameState.getSocial()-diffSocial, gameState.getSocial());
            anim.setDuration(1000);
            barSocial.startAnimation(anim);

            ProgressBar day = (ProgressBar) findViewById(R.id.progressBar);
            anim = new ProgressBarAnimation(day, (gameState.getDate()-1) * (100/30), gameState.getDate() * (100/30));
            anim.setDuration(500);
            day.startAnimation(anim);

            ((TextView) findViewById(R.id.textView2)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.textView3)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.textView4)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.textView5)).setVisibility(View.VISIBLE);



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
                message.setTextColor(Color.BLACK);

                message.setText(qa.getMessage());
                layout.addView(message, idx);
            }

            ((CheckBox) findViewById(R.id.questionAnswered)).setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("GAME_STATE", gameState);
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        if(!((CheckBox)findViewById(R.id.questionAnswered)).isChecked())
            return true;

        Intent intent;
        if(gameState.isGameOver()){
            intent = new Intent(getApplicationContext(), GameOverActivity.class);
            intent.putExtra("GAME_STATE", gameState);

            int goType = 0;
            if(gameState.getAcademics() < 1)
                goType = 1;
            if(gameState.getFinances() < 1)
                goType = 2;
            if(gameState.getHealth() < 1)
                goType = 3;
            if(gameState.getSocial() < 1)
                goType = 4;

            intent.putExtra("GAME_OVER_TYPE", goType);
            ((MyApp)getApplicationContext()).reset();
        }
        else{
            intent = new Intent(getApplicationContext(), QuestionActivity.class);
            intent.putExtra("GAME_STATE", gameState);
        }

        startActivity(intent);
        return true;
    }
}
