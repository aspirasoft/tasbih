package pk.aspirasoft.tasbih

import android.content.Intent
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import pk.aspirasoft.tasbih.models.Database
import pk.aspirasoft.tasbih.models.PrayersDatabase
import pk.aspirasoft.tasbih.views.DuaView


class PrayersActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var rootView: LinearLayout
    private var category: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prayers)
        rootView = findViewById(R.id.rootView)

        category = intent.getStringExtra("category")
        if (category == null) {
            finish()
            return
        }

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

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        rootView.removeAllViews()
        for (i in PrayersDatabase.indices) {
            val category = PrayersDatabase[i]
            if (category.name.equals(this.category, true)) {
                category.duas?.let { prayers ->
                    for (j in prayers.indices) {
                        val v = DuaView(rootView.context)
                        v.updateWith(prayers[j])

                        rootView.addView(v)
                    }
                }
                return
            }
        }
    }

}