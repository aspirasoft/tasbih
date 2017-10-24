package pk.aspirasoft.tasbih.scenes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import pk.aspirasoft.tasbih.R;
import pk.aspirasoft.tasbih.data.Counter;
import pk.aspirasoft.tasbih.data.CounterManager;

public class CounterDrawer extends AppCompatActivity implements View.OnClickListener {

    private boolean gridView = true;
    private CounterManager manager;
    private LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

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

        // Action bar
        setSupportActionBar((Toolbar) findViewById(R.id.actionBar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_counters);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // Create counter manager
        manager = CounterManager.getInstance(this);

        // Read view preferences
        gridView = getSharedPreferences("count-drawer", MODE_PRIVATE).getBoolean("GridView", false);
    }

    private void toggleView(MenuItem item) {
        // Toggle view
        gridView = !gridView;

        // Change action bar icon
        if (gridView) {
            item.setIcon(R.drawable.ic_action_list);
        } else {
            item.setIcon(R.drawable.ic_action_grid);
        }

        // Update preferences
        SharedPreferences.Editor editor = getSharedPreferences("count-drawer", MODE_PRIVATE).edit();
        editor.putBoolean("GridView", gridView);
        editor.apply();

        // Update view
        updateDisplay();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateDisplay();
    }

    public void onClick(View v) {

    }

    private void showGrid() {
        // Get root layout and clear all its children
        rootView = (LinearLayout) findViewById(R.id.rootView);
        rootView.removeAllViews();

        // Get number of cells (counters + new counter button)
        int noOfCells = manager.size() + 1;

        // Calculate num of rows that can accommodate these cells (3 cells/row)
        int noOfRows = noOfCells / 3;
        if (noOfCells % 3 != 0) noOfRows++;

        // Inflate required rows
        for (int i = 0; i < noOfRows; i++)
            getLayoutInflater().inflate(R.layout.drawer_row, rootView);

        // Populate rows
        int count = 0;
        LinearLayout row = (LinearLayout) rootView.getChildAt(0);

        // Inflate new counter button
        getLayoutInflater().inflate(R.layout.drawer_grid_cell, row);

        LinearLayout cell = (LinearLayout) row.getChildAt(row.getChildCount() - 1);
        ImageView logo = (ImageView) cell.getChildAt(0);
        TextView label = (TextView) cell.getChildAt(1);

        logo.setImageResource(android.R.drawable.ic_menu_add);
        label.setText(getResources().getString(R.string.label_add));

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateCounter.class));
            }
        });

        int j = 1;
        for (int i = 0; i < rootView.getChildCount(); i++) {
            row = (LinearLayout) rootView.getChildAt(i);

            for (; j < 3; j++, count++) {
                if (count >= manager.size()) break;

                // Inflate cell
                getLayoutInflater().inflate(R.layout.drawer_grid_cell, row);

                // Get current counter
                final Counter counter = manager.get(count);

                // Display counter name
                cell = (LinearLayout) row.getChildAt(row.getChildCount() - 1);
                logo = (ImageView) cell.getChildAt(0);
                label = (TextView) cell.getChildAt(1);

                label.setText(counter.getName());

                logo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ActiveCounter.class);
                        intent.putExtra("counter", counter.toString());
                        startActivity(intent);
                    }
                });

                logo.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final CounterManager manager = CounterManager.getInstance(CounterDrawer.this);

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
                            AlertDialog.Builder builder = new AlertDialog.Builder(CounterDrawer.this);
                            builder.setTitle(R.string.confirmation_prompt);
                            builder.setMessage(R.string.delete_confirmation);
                            builder.setPositiveButton(R.string.label_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    manager.remove(found);
                                    manager.diskOut();
                                    updateDisplay();
                                }
                            });
                            builder.setNegativeButton(R.string.label_no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }

                            });
                            dialog = builder.create();
                            dialog.show();
                            return true;
                        }

                        return false;
                    }
                });
            }
            j = 0;
        }
    }

    private void showList() {
        // Get root layout and clear all its children
        rootView = (LinearLayout) findViewById(R.id.rootView);
        rootView.removeAllViews();

        // Inflate new counter button
        getLayoutInflater().inflate(R.layout.drawer_list_item, rootView);

        LinearLayout cell = (LinearLayout) rootView.getChildAt(rootView.getChildCount() - 1);
        ImageButton logo = (ImageButton) cell.findViewById(R.id.logo);
        TextView label = (TextView) cell.findViewById(R.id.title);
        TextView about = (TextView) cell.findViewById(R.id.description);

        logo.setImageResource(android.R.drawable.ic_menu_add);
        label.setText(getString(R.string.label_add));
        about.setText(getString(R.string.label_add_description));

        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateCounter.class));
            }
        });

        for (int i = 0; i < manager.size(); i++) {
            // Inflate cell
            getLayoutInflater().inflate(R.layout.drawer_list_item, rootView);

            cell = (LinearLayout) rootView.getChildAt(rootView.getChildCount() - 1);
            logo = (ImageButton) cell.findViewById(R.id.logo);
            label = (TextView) cell.findViewById(R.id.title);
            about = (TextView) cell.findViewById(R.id.description);

            final Counter counter = manager.get(i);
            label.setText(counter.getName());
            about.setText(counter.getDescription());

            cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ActiveCounter.class);
                    intent.putExtra("counter", counter.toString());
                    startActivity(intent);
                }
            });
        }
    }

    private void updateDisplay() {
        if (gridView) {
            showGrid();
        } else {
            showList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        MenuItem item = menu.getItem(0);

        // Read view preferences
        gridView = getSharedPreferences("count-drawer", MODE_PRIVATE)
                .getBoolean("GridView", true);

        // Change action bar icon
        if (gridView) {
            item.setIcon(R.drawable.ic_action_list);
        } else {
            item.setIcon(R.drawable.ic_action_grid);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggleView:
                toggleView(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}