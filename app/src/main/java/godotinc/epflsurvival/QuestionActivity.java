package godotinc.epflsurvival;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {
    private Question q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GameState gameState = (GameState)getIntent().getSerializableExtra("GAME_STATE");

        setContentView(R.layout.activity_question);

        //Setup stats
        TextView healthText = (TextView) findViewById(R.id.healthText);
            healthText.setText(Integer.toString(gameState.getHealth()));
        TextView socialText = (TextView) findViewById(R.id.social);
            socialText.setText(Integer.toString(gameState.getSocial()));
        TextView moneyText = (TextView) findViewById(R.id.finances);
            moneyText.setText(Integer.toString(gameState.getFinances()));
        TextView academicText = (TextView) findViewById(R.id.academics);
            academicText.setText(Integer.toString(gameState.getAcademics()));

        //Setup question
        q = gameState.getRandomQuestion();

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

            Intent intent = new Intent(v.getContext(), QuestionActivity.class);
            intent.putExtra("GAME_STATE", gState);
            startActivity(intent);
        }
    }
}
