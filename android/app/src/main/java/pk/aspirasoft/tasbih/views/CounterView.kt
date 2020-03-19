package pk.aspirasoft.tasbih.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView

import com.github.lzyzsd.circleprogress.CircleProgress
import com.github.lzyzsd.circleprogress.DonutProgress

import pk.aspirasoft.tasbih.R


class CounterView : RelativeLayout {

    var progress = 0
        set(progress) {
            field = progress

            val decimals = (max - 1).toString().length.toString()
            findViewById<TextView>(R.id.progress_counter).text = String.format("%0" + decimals + "d", this.progress)
            donutProgress?.progress = progress.toFloat()
            circleProgress?.progress = progress
        }
    var max = 10000
        set(max) {
            field = max

            donutProgress?.max = max
            circleProgress?.max = max
        }

    private var donutProgress: DonutProgress? = null
    private var circleProgress: CircleProgress? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_counter, this)

        donutProgress = findViewById(R.id.donut_progress)
        donutProgress?.progress = this.progress.toFloat()
        donutProgress?.max = this.max

        circleProgress = findViewById(R.id.circle_progress)
        circleProgress?.progress = this.progress
        circleProgress?.max = this.max

        val clockTypeface = Typeface.createFromAsset(context.assets, "fonts/alarm-clock.ttf")
        findViewById<TextView>(R.id.progress_counter).typeface = clockTypeface
        findViewById<TextView>(R.id.finished).typeface = clockTypeface

        val decimals = (max - 1).toString().length.toString()
        findViewById<TextView>(R.id.progress_counter).text = String.format("%0" + decimals + "d", this.progress)
    }

    fun setCompleted(completed: Int) {
        val finished = findViewById<TextView>(R.id.finished)

        val text = resources.getString(R.string.label_finished, completed)
        finished.text = text
    }
}
