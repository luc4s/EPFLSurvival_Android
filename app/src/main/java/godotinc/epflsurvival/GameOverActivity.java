package godotinc.epflsurvival;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView txt = (TextView) findViewById(R.id.textViewScrolling);
        txt.startAnimation((Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scrolling));
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("QUESTIONS", getIntent().getSerializableExtra("QUESTIONS"));
        startActivity(intent);
        return true;
    }
}
