package godotinc.epflsurvival;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("QUESTIONS", getIntent().getSerializableExtra("QUESTIONS"));
        startActivity(intent);
        return true;
    }
}
