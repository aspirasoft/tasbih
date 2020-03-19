package pk.aspirasoft.tasbih.views

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import pk.aspirasoft.tasbih.R
import pk.aspirasoft.tasbih.models.Dua


/**
 * @author saifkhichi96
 * @version 1.0.0
 * @since 1.0.0 2019-05-06 18:34
 */
class DuaView : LinearLayout, View.OnClickListener {

    private lateinit var mTranslationView: AppCompatTextView
    private lateinit var mTranslationButton: ImageButton

    private lateinit var mTransliterationView: AppCompatTextView
    private lateinit var mTransliterationButton: ImageButton

    private lateinit var mTitleView: AppCompatTextView
    private lateinit var mContentView: AppCompatTextView
    private lateinit var mSourceView: AppCompatTextView

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        View.inflate(context, R.layout.listitem_prayer, this)
        val tfArabic = Typeface.createFromAsset(context.assets, "fonts/al-qalam.ttf")
        val tfEnglish = Typeface.createFromAsset(context.assets, "fonts/roboto-regular.ttf")

        mContentView = findViewById(R.id.ar)
        mSourceView = findViewById(R.id.source)
        mTitleView = findViewById(R.id.title)

        mTranslationView = findViewById(R.id.en)
        mTranslationButton = findViewById(R.id.show_translation)
        mTranslationButton.setOnClickListener(this)

        mTransliterationView = findViewById(R.id.ar_en)
        mTransliterationButton = findViewById(R.id.show_transliteration)
        mTransliterationButton.setOnClickListener(this)

        mTranslationView.visibility = View.GONE
        mTransliterationView.visibility = View.GONE
        mTitleView.visibility = View.GONE

        mContentView.typeface = tfArabic
        mTranslationView.typeface = tfEnglish
        mTransliterationView.typeface = tfEnglish
        mSourceView.typeface = tfEnglish
    }

    fun updateWith(dua: Dua) {
        mContentView.text = dua.ar
        mTransliterationView.text = dua.ar_en
        mTranslationView.text = dua.en
        mSourceView.text = dua.source
        if (dua.title != null && !dua.title!!.trim().isEmpty()) {
            mTitleView.text = dua.title
            mTitleView.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it) {
                mTranslationButton -> {
                    mTransliterationView.visibility = View.GONE
                    mTransliterationButton.setColorFilter(
                            ContextCompat.getColor(context, android.R.color.darker_gray),
                            PorterDuff.Mode.SRC_IN)

                    if (mTranslationView.visibility == View.VISIBLE) {
                        mTranslationView.visibility = View.GONE
                        mTranslationButton.setColorFilter(
                                ContextCompat.getColor(context, android.R.color.darker_gray),
                                PorterDuff.Mode.SRC_IN)
                    } else {
                        mTranslationView.visibility = View.VISIBLE
                        mTranslationButton.setColorFilter(
                                ContextCompat.getColor(context, R.color.colorAccent),
                                PorterDuff.Mode.SRC_IN)
                    }
                }
                mTransliterationButton -> {
                    mTranslationView.visibility = View.GONE
                    mTranslationButton.setColorFilter(
                            ContextCompat.getColor(context, android.R.color.darker_gray),
                            PorterDuff.Mode.SRC_IN)

                    if (mTransliterationView.visibility == View.VISIBLE) {
                        mTransliterationView.visibility = View.GONE
                        mTransliterationButton.setColorFilter(
                                ContextCompat.getColor(context, android.R.color.darker_gray),
                                PorterDuff.Mode.SRC_IN)
                    } else {
                        mTransliterationView.visibility = View.VISIBLE
                        mTransliterationButton.setColorFilter(
                                ContextCompat.getColor(context, R.color.colorAccent),
                                PorterDuff.Mode.SRC_IN)
                    }
                }
            }
        }
    }

}