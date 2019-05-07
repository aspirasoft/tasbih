package pk.aspirasoft.tasbih

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import pk.aspirasoft.tasbih.models.*
import pk.aspirasoft.tasbih.utils.DateAnnoHegirae
import java.util.*

/**
 * @author saifkhichi96
 * @version 1.0.0
 * @since 1.0.0 2019-05-03 18:01
 */
class TasbihApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        Database.init(applicationContext)
        if (CounterManager.size <= 0) {
            createDefaults()
        }

        // Get current AH date
        Database.put("today", null)
        DateAnnoHegirae.now(this, object : DateAnnoHegirae.Listener {
            override fun onReceived(date: DateAnnoHegirae?) {
                date?.let {
                    Database.put("today", it.toString())
                }
            }
        })

        // Update prayers database
        PrayersDatabase.updateWith(FirebaseDatabase.getInstance())
    }

    private fun createDefaults() {
        val counters = ArrayList<Tasbih>()
        Tasbih("تسبیح الزهراء",
                "Commonly attributed to Hazrat Fatima (RA), this tasbeeh is recited once after each prayer.\n\n" +
                        "Subhan Allah (33x) Alhamdulillah (33x) Allahu Akbar (34x)")
                .apply {
                    add(Dua(
                            original = getString(R.string.dua2_ar),
                            translation = getString(R.string.dua2_en),
                            transliteration = getString(R.string.dua2_ar_en),
                            source = getString(R.string.dua2_source),
                            title = getString(R.string.counter2_name)
                    ), resources.getInteger(R.integer.dua2_max))
                    add(Dua(
                            original = getString(R.string.dua1_ar),
                            translation = getString(R.string.dua1_en),
                            transliteration = getString(R.string.dua1_ar_en),
                            source = null,
                            title = getString(R.string.counter1_name)
                    ), resources.getInteger(R.integer.dua1_max))
                    add(Dua(
                            original = getString(R.string.dua3_ar),
                            translation = getString(R.string.dua3_en),
                            transliteration = getString(R.string.dua3_ar_en),
                            source = getString(R.string.dua3_source),
                            title = getString(R.string.counter3_name)
                    ), resources.getInteger(R.integer.dua3_max))

                    counters.add(this)
                }
        CounterManager.replaceAll(counters)
    }

}