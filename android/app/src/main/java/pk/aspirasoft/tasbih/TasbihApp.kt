package pk.aspirasoft.tasbih

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import pk.aspirasoft.tasbih.models.*
import pk.aspirasoft.tasbih.utils.DateAnnoHegirae

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
        val defaults = createDefaults()
        if (CounterManager.size != defaults.size) {
            CounterManager.replaceAll(defaults)
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

    private fun createDefaults(): ArrayList<Tasbih> {
        val counters = ArrayList<Tasbih>()
        Tasbih(name = "", description = "").apply {
            counters.add(this)
        }

        Tasbih(
            "تسبیح الزهراء",
            "Commonly attributed to Hazrat Fatima (RA), this tasbeeh is recited once after each prayer.\n\n" +
                    "Subhan Allah (33x) Alhamdulillah (33x) Allahu Akbar (33x)"
        )
            .apply {
                add(
                    Dua(
                        original = getString(R.string.dua2_ar),
                        translation = getString(R.string.dua2_en),
                        transliteration = getString(R.string.dua2_ar_en),
                        source = getString(R.string.dua2_source),
                        title = getString(R.string.counter2_name)
                    ), 33
                )
                add(
                    Dua(
                        original = getString(R.string.dua1_ar),
                        translation = getString(R.string.dua1_en),
                        transliteration = getString(R.string.dua1_ar_en),
                        source = null,
                        title = getString(R.string.counter1_name)
                    ), 33
                )
                add(
                    Dua(
                        original = getString(R.string.dua3_ar),
                        translation = getString(R.string.dua3_en),
                        transliteration = getString(R.string.dua3_ar_en),
                        source = getString(R.string.dua3_source),
                        title = getString(R.string.counter3_name)
                    ), 33
                )
                add(
                    Dua(
                        original = "لاَ إلَهَ إلاَّ اللّهُ وَحْـدَهُ لاَ شَـرِيكَ لَهُ، لَهُ المُـلْكُ ولَهُ الحَمْـدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ.",
                        translation = "None has the right to be worshipped except Allah, alone, without partner, to Him belongs all sovereignty and praise and He is over all things omnipotent.",
                        transliteration = "Lâ ilâha illallâhu wahdahu lâ sharîka lahu, lahu'l-mulku wa lahu'l-hamdu, wa huwa `alâ kulli shay'in qadîr.",
                        source = "Muslim 16/12",
                        title = ""
                    ), 1
                )

                counters.add(this)
            }

        Tasbih(
            "Ayat al-Kursi",
            "A tasbeeh to be recited after each prayer."
        )
            .apply {
                add(
                    Dua(
                        original = "اَسْتَغْفِرُالله",
                        translation = "I beg Allah for forgiveness",
                        transliteration = "Astaghfirullah",
                        source = null,
                        title = "اَسْتَغْفِرُالله"
                    ), 3
                )
                add(
                    Dua(
                        original = "اللَّهُـمَّ أَنْـتَ السَّلاَمُ، وَمِـنْكَ السَّلاَمُ، تَبَارَكْتَ يَا ذَا الجَـلاَلِ وَالإِكْـرَامِ",
                        translation = "You are As-Salam and from You is all peace, blessed are You, O Possessor of majesty and honour.",
                        transliteration = "Allahumma anta-s-salaam wa minka-s-salaam tabaarakta ya dhal jalaali wa-l ikraam",
                        source = "Muslim 1/414",
                        title = ""
                    ), 1
                )
                add(
                    Dua(
                        original = "اللّهُ لاَ إِلَـهَ إِلاَّ هُوَ الْحَيُّ الْقَيُّومُ لاَ تَأْخُذُهُ سِنَةٌ وَلاَ نَوْمٌ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الأَرْضِ مَنْ ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلاَّ بِإِذْنِهِ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ وَلاَ يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلاَّ بِمَا شَاءَ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالأَرْضَ وَلاَ يَؤُودُهُ حِفْظُهُمَا وَهُوَ الْعَلِيُّ الْعَظِيمُ.",
                        translation = "Allah! There is no god but He - the Living, The Self-subsisting, Eternal. No slumber can seize Him Nor Sleep. His are all things In the heavens and on earth. Who is there can intercede In His presence except As he permitteth? He knoweth What (appeareth to His creatures As) Before or After or Behind them. Nor shall they encompass Aught of his knowledge Except as He willeth. His throne doth extend Over the heavens And on earth, and He feeleth No fatigue in guarding And preserving them, For He is the Most High. The Supreme",
                        transliteration = "Allahu la ilaha illa Huwa, Al-Haiyul-Qaiyum La ta'khudhuhu sinatun wa la nawm, lahu ma fis-samawati wa ma fil-'ard Man dhal-ladhi yashfa'u 'indahu illa bi-idhnihi Ya'lamu ma baina aidihim wa ma khalfahum, wa la yuhituna bi shai'im-min 'ilmihi illa bima sha'a Wasi'a kursiyuhus-samawati wal ard, wa la ya'uduhu hifdhuhuma Wa Huwal 'Aliyul-Adheem",
                        source = "Quran 2:255",
                        title = ""
                    ), 1
                )

                counters.add(this)
            }

        return counters
    }

}