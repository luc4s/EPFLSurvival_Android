package godotinc.epflsurvival;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ArrayList<Question> questions = (ArrayList<Question>)getIntent().getSerializableExtra("QUESTIONS");
        GameState gameState = new GameState(questions, 50, 50, 50, 50, 0);

        final Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("GAME_STATE", gameState);

        Button buttonOne = (Button) findViewById(R.id.button3);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
