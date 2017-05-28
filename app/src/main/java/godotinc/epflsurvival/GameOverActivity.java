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
                msg = "Endetté(e) jusqu’au cou, tu dois abandonner tes études afin de de prendre 2 jobs à 100% pour t’en sortir.";
                img = "go_fin";
                break;
            case 3://health
                msg = "Ton état de santé est tellement bas que tu fais une attaque en plein examen final et t’effondres. Le prof refuse de croire que c’était pour de vrai et te colle un 0, rendant la branche impossible à rattraper.";
                img = "go_health";
                break;
            case 4://social
                msg = "A force d’être isolé(e), tu pars en dépression clinque et n’arrives plus à te concentrer sur tes études.";
                img = "go_social";
                break;
            default:
                msg = "Félicitations ! Tu as réussi à survivre à ta première année à l’EPFL ! Prêt à attaquer la suite?";
                img = "go_win";
        }

        ((TextView)findViewById(R.id.textView7)).setText(msg);

        int drawableID = getResources().getIdentifier(img, "drawable", getPackageName());
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(drawableID);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
        return true;
    }


    @Override
    public void onBackPressed() {}
}
