package pk.aspirasoft.tasbih

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import pk.aspirasoft.tasbih.models.CounterManager
import pk.aspirasoft.tasbih.models.Database


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private var rootView: LinearLayout? = null
    private var dl: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<View>(R.id.create_button)?.setOnClickListener(this)
        findViewById<View>(R.id.count_button)?.setOnClickListener(this)

        dl = findViewById(R.id.activity_home)
        val t = ActionBarDrawerToggle(this, dl, R.string.drawer_open, R.string.drawer_close)

        dl?.addDrawerListener(t)
        t.syncState()

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<NavigationView>(R.id.nv).setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.quranic_passages,
                R.id.darood_collection,
                R.id.rabbana_prayers,
                R.id.other_prayers
                -> {
                    val i = Intent(applicationContext, PrayersActivity::class.java)
                    i.putExtra("category", item.title.toString())
                    startActivity(i)
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                dl?.openDrawer(GravityCompat.START)
                true
            }
            R.id.privacy_policy -> {
                startActivity(Intent(this@HomeActivity, PrivacyActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    public override fun onStart() {
        super.onStart()
        updateDisplay()
    }

    override fun onResume() {
        super.onResume()
        Database.get("today")?.let { date ->
            findViewById<MaterialButton>(R.id.date_view).text = date
            findViewById<MaterialButton>(R.id.date_view).visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.create_button -> startActivity(Intent(applicationContext, CreateActivity::class.java))
            R.id.count_button -> startActivity(Intent(applicationContext, TasbihActivity::class.java))
        }
    }

    private fun updateDisplay() {
        // Get root layout and clear all its children
        rootView = findViewById<View>(R.id.rootView) as LinearLayout
        rootView!!.removeAllViews()

        for (counter in CounterManager) {
            if (counter.name.isEmpty()) continue

            // Inflate cell
            layoutInflater.inflate(R.layout.listitem_tasbih, rootView)

            val cell = rootView!!.getChildAt(rootView!!.childCount - 1)
            val label = cell.findViewById<View>(R.id.title) as TextView
            val description = cell.findViewById<View>(R.id.description) as TextView

            label.text = counter.name
            label.typeface = Typeface.createFromAsset(assets, "fonts/al-qalam.ttf")

            description.text = counter.description
            description.typeface = Typeface.createFromAsset(assets, "fonts/roboto-regular.ttf")

            cell.setOnClickListener {
                val intent = Intent(applicationContext, TasbihActivity::class.java)
                intent.putExtra("tasbih", counter.toString())
                startActivity(intent)
            }
        }
    }

}