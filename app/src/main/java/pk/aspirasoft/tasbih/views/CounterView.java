package pk.aspirasoft.tasbih.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;

import pk.aspirasoft.tasbih.R;


public class CounterView extends RelativeLayout {

    private int progress = 0;
    private int max = 100;

    private DonutProgress donutProgress;
    private CircleProgress circleProgress;

    public CounterView(Context context) {
        super(context);
        init();
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.counter, this);

        donutProgress = findViewById(R.id.donut_progress);
        donutProgress.setProgress(progress);
        donutProgress.setMax(max);

        circleProgress = findViewById(R.id.circle_progress);
        circleProgress.setProgress(progress);
        circleProgress.setMax(max);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;

        donutProgress.setProgress(progress);
        circleProgress.setProgress(progress);
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;

        donutProgress.setMax(max);
        circleProgress.setMax(max);
    }

    public void setCompleted(int completed) {
        String text = getResources().getString(R.string.label_finished, completed);

        TextView finished = findViewById(R.id.finished);
        finished.setText(text);
    }
}
