package pk.aspirasoft.tasbih.models

import com.google.firebase.database.*

/**
 *
 *
 * @author saifkhichi96
 * @version 1.0.0
 * @since 1.0.0 2019-05-04 10:14
 */
object PrayersDatabase : ArrayList<PrayersDatabase.PrayerCategory>(0) {

    class PrayerCategory {
        var name: String? = null
        var duas: List<Dua>? = null
            set(value) {
                field = value?.sortedWith(compareBy { it.source })
                        ?.sortedWith(compareBy { it.title })
            }
    }

    fun updateWith(db: FirebaseDatabase) {
        db.getReference("categories/")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val updated = snapshot.getValue(object : GenericTypeIndicator<ArrayList<PrayerCategory>>() {})
                        updated?.let {
                            this@PrayersDatabase.clear()
                            for (cat in it) {
                                this@PrayersDatabase.add(cat)
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // TODO: Handle errors
                    }
                })
    }

}