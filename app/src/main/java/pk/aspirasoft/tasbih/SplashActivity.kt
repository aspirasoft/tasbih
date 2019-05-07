package pk.aspirasoft.tasbih

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        countDownTimer = object : CountDownTimer(1500, 1500) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                startActivity(Intent(applicationContext, HomeActivity::class.java))
                finish()
            }
        }
        countDownTimer?.start()
    }

    override fun onBackPressed() {
        try {
            countDownTimer!!.cancel()
        } catch (ex: Exception) {
            // TODO: Log exception
        } finally {
            super.onBackPressed()
        }
    }
}
