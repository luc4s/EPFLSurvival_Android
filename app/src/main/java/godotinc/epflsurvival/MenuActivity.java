package godotinc.epflsurvival;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button buttonOne = (Button) findViewById(R.id.button3);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), QuestionActivity.class));
            }
        });
    }
}
