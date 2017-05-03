package godotinc.epflsurvival;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {
    private Question q;
    private GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameState = (GameState)getIntent().getSerializableExtra("GAME_STATE");

        setContentView(R.layout.activity_question);

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

            if(gState.isGameOver()){
                Intent intent = new Intent(v.getContext(), GameOverActivity.class);
                intent.putExtra("QUESTIONS", gState.getAllQuestions());
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(v.getContext(), QuestionActivity.class);
                intent.putExtra("GAME_STATE", gState);
                startActivity(intent);
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
