package godotinc.epflsurvival;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView txt = (TextView) findViewById(R.id.textViewScrolling);
        txt.startAnimation((Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scrolling));


        GameState gameState = (GameState)getIntent().getSerializableExtra("GAME_STATE");
        txt = (TextView) findViewById(R.id.textviewA);
        txt.setText(Integer.toString(gameState.getAcademics()));
        txt = (TextView) findViewById(R.id.textviewS);
        txt.setText(Integer.toString(gameState.getSocial()));
        txt = (TextView) findViewById(R.id.textviewH);
        txt.setText(Integer.toString(gameState.getHealth()));
        txt = (TextView) findViewById(R.id.textViewF);
        txt.setText(Integer.toString(gameState.getFinances()));

        String msg = "",
                img = "";
        switch(getIntent().getIntExtra("GAME_OVER_TYPE", 0)){
            //juste une stat
            case 1: //acad
                msg = "Bon courage pour la MAN";
                img = "go_aca";
                break;
            case 2: //finances
                msg = "Il va falloir apprendre à gérer ton portefeuille...";
                img = "go_fin";
                break;
            case 3://health
                msg = "Il faudra penser à faire un peu plus de sport et à dormir la nuit...";
                img = "go_health";
                break;
            case 4://social
                msg = "Tu es un cas social, tu devrait sortir un peu...";
                img = "go_social";
                break;
            default:
                msg = "Félicitations ! Tu as réussi à survivre à ta première année à l’EPFL ! Prêt à attaquer la suite …?";
                img = "go_win";
        }

        ((TextView)findViewById(R.id.textView7)).setText(msg);

        int drawableID = getResources().getIdentifier(img, "drawable", getPackageName());
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(drawableID);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        GameState gameState = (GameState) getIntent().getSerializableExtra("GAME_STATE");
        intent.putExtra("QUESTIONS", gameState.getAllQuestions());
        startActivity(intent);
        return true;
    }
}
