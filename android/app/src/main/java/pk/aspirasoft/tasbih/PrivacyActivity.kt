package pk.aspirasoft.tasbih

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView

class PrivacyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)

        findViewById<WebView>(R.id.web_view)?.loadUrl("file:///android_asset/privacy.html")
    }
}