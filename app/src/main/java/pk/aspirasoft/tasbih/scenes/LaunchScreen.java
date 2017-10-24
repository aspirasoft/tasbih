package pk.aspirasoft.tasbih.scenes;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import pk.aspirasoft.tasbih.R;

public class LaunchScreen extends AppCompatActivity {

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);

        countDownTimer = new CountDownTimer(1500, 1500) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), CounterDrawer.class));
                finish();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onBackPressed() {
        try {
            countDownTimer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.onBackPressed();
        }
    }
}
