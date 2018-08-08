package pk.aspirasoft.tasbih.scenes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import pk.aspirasoft.tasbih.R;
import pk.aspirasoft.tasbih.data.Counter;
import pk.aspirasoft.tasbih.data.CounterManager;
import pk.aspirasoft.tasbih.views.CounterView;

public class ActiveCounter extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private Counter counter;
    private CounterView counterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter_details);

        // Integrate AdMob
        final AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setVisibility(View.GONE);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        mAdView.loadAd(adRequest);

        counter = new Counter(getIntent().getStringExtra("counter"));
        counterView = (CounterView) findViewById(R.id.counter);
        counterView.setOnLongClickListener(this);
        counterView.setOnClickListener(this);
        counterView.setMax(counter.getMax());

        setSupportActionBar((Toolbar) findViewById(R.id.actionBar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(counter.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ((TextView) findViewById(R.id.description)).setText(counter.getDescription());
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remove:
                final CounterManager manager = CounterManager.getInstance(this);

                Counter object = null;
                for (Counter c : manager) {
                    if (c.getName().equals(counter.getName())) {
                        object = c;
                        break;
                    }
                }

                if (object != null) {
                    final Counter found = object;
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.confirmation_prompt);
                    builder.setMessage(R.string.delete_confirmation);
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            manager.remove(found);
                            manager.diskOut();
                            finish();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    });
                    dialog = builder.create();
                    dialog.show();
                }
                return true;
            case R.id.reset:
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.confirmation_prompt);
                builder.setMessage(R.string.reset_confirmation);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        counter.reset();
                        updateUI();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        counterView.setCompleted(counter.getValue() / counter.getMax());
        counterView.setProgress(counter.getValue() % counter.getMax());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.counter:
                counter.increment();
                updateUI();
                break;
        }
    }


    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.counter:
                counter.decrement();
                updateUI();
                return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        CounterManager manager = CounterManager.getInstance(this);
        for (Counter c : manager) {
            if (c.getName().equals(counter.getName())) {
                c.setValue(counter.getValue());
                break;
            }
        }
        manager.diskOut();
        super.onPause();
    }

}