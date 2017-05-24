package godotinc.epflsurvival;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private GameState savedGameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ArrayList<Question> questions;
        if(getIntent().hasExtra("GAME_STATE")){
            savedGameState = (GameState)getIntent().getSerializableExtra("GAME_STATE");

            Button buttonContinue = (Button) findViewById(R.id.buttonContinuer);
            buttonContinue.setVisibility(View.VISIBLE);
            questions = savedGameState.getAllQuestions();


            final Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
            intent.putExtra("GAME_STATE", savedGameState);
            Button buttonOne = (Button) findViewById(R.id.buttonContinuer);
            buttonOne.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
        }
        else{
            questions = (ArrayList<Question>)getIntent().getSerializableExtra("QUESTIONS");
        }

        GameState gameState = new GameState(questions, 50, 50, 50, 50, 0);

        final Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("GAME_STATE", gameState);

        Button buttonOne = (Button) findViewById(R.id.button3);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Button buttonInstr = (Button) findViewById(R.id.buttonInstructions);
        final ScrollView instrView = (ScrollView)findViewById(R.id.scrollViewInstructions);

        buttonInstr.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout1);
                if(instrView.getVisibility() == instrView.VISIBLE){
                    layout.setGravity(Gravity.CENTER_VERTICAL);
                    instrView.setVisibility(View.GONE);
                }
                else {
                    layout.setGravity(Gravity.TOP);
                    instrView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {}
}
