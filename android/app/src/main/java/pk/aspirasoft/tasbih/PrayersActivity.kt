package pk.aspirasoft.tasbih

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
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