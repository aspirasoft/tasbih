package pk.aspirasoft.tasbih

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import pk.aspirasoft.tasbih.models.CounterManager
import pk.aspirasoft.tasbih.models.Tasbih

class CreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        // Action bar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_add_tasbih, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                var title = (findViewById<View>(R.id.title) as EditText).text.toString()
                var about = (findViewById<View>(R.id.description) as EditText).text.toString()

                // Replace line breaks with spaces
                title = title.replace("\n", " ")
                about = about.replace("\n", " ")

                if (title.replace(" ", "") == "") {
                    Toast.makeText(this, "Title is required.", Toast.LENGTH_SHORT).show()
                    return true
                }

                val counter = Tasbih(title, about)
                if (CounterManager.add(counter)) {
                    onBackPressed()
                } else {
                    Toast.makeText(this, "Another counter with this ar already exists.", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}