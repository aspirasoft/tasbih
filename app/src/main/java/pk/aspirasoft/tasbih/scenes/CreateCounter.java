package pk.aspirasoft.tasbih.scenes;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import pk.aspirasoft.tasbih.R;
import pk.aspirasoft.tasbih.data.Counter;
import pk.aspirasoft.tasbih.data.CounterManager;

public class CreateCounter extends AppCompatActivity {

    private CounterManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter_create);

        manager = CounterManager.getInstance(this);

        // Action bar
        setSupportActionBar((Toolbar) findViewById(R.id.actionBar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.label_add_description);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                String title = ((EditText) findViewById(R.id.title)).getText().toString();
                String about = ((EditText) findViewById(R.id.description)).getText().toString();

                // Replace line breaks with spaces
                title = title.replace("\n", " ");
                about = about.replace("\n", " ");

                if (title.replace(" ", "").equals("")) {
                    Toast.makeText(this, "Title is required.", Toast.LENGTH_SHORT).show();
                    return true;
                }

                Counter counter = new Counter(title, about);
                if (manager.add(counter)) {
                    manager.diskOut();
                    onBackPressed();
                } else {
                    Toast.makeText(this, "Another counter with this name already exists.", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}