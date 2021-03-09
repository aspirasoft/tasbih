package pk.aspirasoft.tasbih.utils

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonElement
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author saifkhichi96
 * @version 1.0.0
 * @since 1.0.0 2019-05-02 09:36
 */
class DateAnnoHegirae {

    var date: String? = null
    var format: String? = null
    var day: String? = null
    var month: Month? = null
    var year: String? = null
    var designation: Designation? = null

    override fun toString(): String {
        return try {
            day + " " + month!!.en + ", " + year
        } catch (ignored: Exception) {
            ""
        }
    }

    interface Listener {
        fun onReceived(date: DateAnnoHegirae?)
    }

    inner class Month {
        internal var number: Int = 0
        internal var en: String? = null
        internal var ar: String? = null
    }

    inner class Designation {
        internal var abbreviated: String? = null
        internal var expanded: String? = null
    }

    companion object {
        fun now(context: Context, listener: Listener) {
            // Get current Georgian date
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            val nowAD = formatter.format(Calendar.getInstance().time)

            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(context)
            val url = "https://api.aladhan.com/v1/gToH?date=$nowAD"

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    val gson = Gson()
                    val jsonElement = gson.fromJson(response, JsonElement::class.java)
                    val jsonObject = jsonElement.asJsonObject

                    val hijriElement = jsonObject.get("data").asJsonObject.get("hijri")
                    listener.onReceived(gson.fromJson(hijriElement, DateAnnoHegirae::class.java))
                },
                {
                    Log.e("Tasbih", it?.message ?: "error")
                    listener.onReceived(null)
                })

            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }
    }

}