package godotinc.epflsurvival;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question);
        TextView questionText = (TextView) findViewById(R.id.questionText);
        //questionText.setText("fuck android studio");
        Button button1 = (Button) findViewById(R.id.button8);
        button1.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum ligula. Ut in libero vitae nisi aliquam consectetur sed sit amet erat. Proin scelerisque sem vitae risus vulputate euismod. Integer vel ipsum auctor, eleifend est dapibus, pretium dolor. Mauris efficitur fermentum velit vitae porta. In auctor, ri");
        button1.setTextSize(10);
        Button button2 = (Button) findViewById(R.id.button7);
        button2.setTextSize(10);
        button2.setText("BAA");
        Button button3 = (Button) findViewById(R.id.button2);
        button3.setTextSize(10);
        button3.setText("CAA");
        Button button4 = (Button) findViewById(R.id.button);
        button4.setTextSize(10);
        button4.setText("DAA");
    }
}
