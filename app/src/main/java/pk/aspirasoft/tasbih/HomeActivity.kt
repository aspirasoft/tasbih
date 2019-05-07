package pk.aspirasoft.tasbih

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import pk.aspirasoft.tasbih.models.CounterManager
import pk.aspirasoft.tasbih.models.Database


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private var rootView: LinearLayout? = null

    private var dl: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Integrate AdMob
        val mAdView = findViewById<View>(R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        mAdView.visibility = View.GONE
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                mAdView.visibility = View.VISIBLE
            }
        }
        mAdView.loadAd(adRequest)

        findViewById<View>(R.id.create_button)?.setOnClickListener(this)

        dl = findViewById<DrawerLayout>(R.id.activity_home)
        val t = ActionBarDrawerToggle(this, dl, R.string.drawer_open, R.string.drawer_close)

        dl?.addDrawerListener(t)
        t.syncState()

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<NavigationView>(R.id.nv).setNavigationItemSelectedListener(
                object : NavigationView.OnNavigationItemSelectedListener {
                    override fun onNavigationItemSelected(item: MenuItem): Boolean {
                        when (item.itemId) {
                            R.id.quranic_passages,
                            R.id.darood_collection,
                            R.id.rabbana_prayers,
                            R.id.other_prayers -> {
                                val i = Intent(applicationContext, PrayersActivity::class.java)
                                i.putExtra("category", item.title.toString())
                                startActivity(i)
                            }
                        }
                        return true
                    }
                }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            dl?.openDrawer(Gravity.START)
            true
        } else if (item?.itemId == R.id.privacy_policy) {
            startActivity(Intent(this@HomeActivity, PrivacyActivity::class.java))
            true
        } else {
            super.onOptionsItemSelected(item)
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
        }
    }

    private fun updateDisplay() {
        // Get root layout and clear all its children
        rootView = findViewById<View>(R.id.rootView) as LinearLayout
        rootView!!.removeAllViews()

        for (i in CounterManager.indices) {
            // Inflate cell
            layoutInflater.inflate(R.layout.listitem_tasbih, rootView)

            val cell = rootView!!.getChildAt(rootView!!.childCount - 1)
            val label = cell.findViewById<View>(R.id.title) as TextView
            val description = cell.findViewById<View>(R.id.description) as TextView

            val counter = CounterManager[i]
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