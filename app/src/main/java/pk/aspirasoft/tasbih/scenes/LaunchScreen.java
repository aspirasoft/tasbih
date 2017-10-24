package pk.aspirasoft.tasbih.scenes;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import pk.aspirasoft.tasbih.R;
import pk.aspirasoft.tasbih.data.Counter;
import pk.aspirasoft.tasbih.data.CounterManager;

public class LaunchScreen extends AppCompatActivity {

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);

        CounterManager manager = CounterManager.getInstance(this);
        if (!manager.isInitialized()) {
            ArrayList<Counter> counters = new ArrayList<>();

            Counter counter1 = new Counter(getString(R.string.counter_1), getString(R.string.counter_1_desc));
            counter1.setMax(getResources().getInteger(R.integer.counter_1_max));

            Counter counter2 = new Counter(getString(R.string.counter_2), getString(R.string.counter_2_desc));
            counter2.setMax(getResources().getInteger(R.integer.counter_2_max));

            Counter counter3 = new Counter(getString(R.string.counter_3), getString(R.string.counter_3_desc));
            counter3.setMax(getResources().getInteger(R.integer.counter_3_max));

            Counter counter4 = new Counter(getString(R.string.counter_4), getString(R.string.counter_4_desc));
            counter4.setMax(getResources().getInteger(R.integer.counter_4_max));

            Counter counter5 = new Counter(getString(R.string.counter_5), getString(R.string.counter_5_desc));
            counter5.setMax(getResources().getInteger(R.integer.counter_5_max));

            Counter counter6 = new Counter(getString(R.string.counter_6), getString(R.string.counter_6_desc));
            counter6.setMax(getResources().getInteger(R.integer.counter_6_max));

            Counter counter7 = new Counter(getString(R.string.counter_7), getString(R.string.counter_7_desc));
            counter7.setMax(getResources().getInteger(R.integer.counter_7_max));

            Counter counter8 = new Counter(getString(R.string.counter_8), getString(R.string.counter_8_desc));
            counter8.setMax(getResources().getInteger(R.integer.counter_8_max));

            counters.add(counter1);
            counters.add(counter2);
            counters.add(counter3);
            counters.add(counter4);
            counters.add(counter5);
            counters.add(counter6);
            counters.add(counter7);
            counters.add(counter8);

            manager.addDefaultCounters(counters);
        }

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
