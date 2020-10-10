package pk.aspirasoft.tasbih

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_tasbih.*
import pk.aspirasoft.tasbih.models.CounterManager
import pk.aspirasoft.tasbih.models.Tasbih
import pk.aspirasoft.tasbih.views.CounterView
import pk.aspirasoft.tasbih.views.DuaView


class TasbihActivity : AppCompatActivity() {

    private lateinit var tasbih: Tasbih
    private lateinit var counterView: CounterView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasbih)

        // Setup action bar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get current tasbih
        val counterData = intent.getStringExtra("tasbih")
        if (counterData == null) {
            for (counter in CounterManager) {
                if (counter.name.isBlank()) {
                    tasbih = counter
                    break
                }
            }
        } else tasbih = Tasbih(counterData)

        counterView = counter
        counterView.max = tasbih.countMax

        // Show tasbih details
        findViewById<TextView>(R.id.title)?.let { titleView ->
            titleView.typeface = Typeface.createFromAsset(assets, "fonts/al-qalam.ttf")
            titleView.text = tasbih.name
        }

        findViewById<TextView>(R.id.description)?.let { descriptionView ->
            descriptionView.typeface = Typeface.createFromAsset(assets, "fonts/roboto-regular.ttf")
            descriptionView.text = tasbih.description
        }

        // Show prayers content
        for (prayer in tasbih.prayers) {
            info.visibility = View.VISIBLE
            val d = DuaView(counterView.context)
            d.updateWith(prayer)

            rootView?.addView(d)
        }

        // Show tasbih stats
        updateUI()

        plus?.setOnClickListener {
            ++tasbih
            updateUI()
        }

        minus?.setOnClickListener {
            --tasbih
            updateUI()
        }

        info.iconTint = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.material_on_background_disabled))
        infoView.visibility = View.GONE
        info.setOnClickListener {
            infoView.visibility = if (infoView.visibility == View.GONE) {
                info.iconTint = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                View.VISIBLE
            } else {
                info.iconTint = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.material_on_background_disabled))
                View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_tasbih, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reset -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.confirmation_prompt)
                builder.setMessage(R.string.reset_confirmation)
                builder.setPositiveButton(android.R.string.ok) { _, _ ->
                    tasbih.reset()
                    updateUI()
                }
                builder.setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
                builder.create().show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI() {
        counterView.setCompleted(tasbih.counter / tasbih.countMax)
        counterView.progress = tasbih.counter % tasbih.countMax
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                ++tasbih
                updateUI()
                true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                --tasbih
                updateUI()
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }

    override fun onPause() {
        for (c in CounterManager) {
            if (c.name == tasbih.name) {
                c.counter = tasbih.counter
                break
            }
        }
        CounterManager.diskOut()
        super.onPause()
    }

}