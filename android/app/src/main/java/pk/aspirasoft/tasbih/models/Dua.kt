package pk.aspirasoft.tasbih.models

import androidx.annotation.Keep
import com.google.gson.Gson

class Dua {

    var ar: String?
        set(value) {
            field = value?.replace(".", "\n")
        }
    var ar_en: String?
    var en: String?
    var source: String?
    var title: String?

    @Keep
    constructor() {
        ar = null
        ar_en = null
        en = null
        source = null
        title = null
    }

    constructor(original: String, translation: String, transliteration: String? = null, source: String? = null, title: String? = null) {
        this.ar = original
        this.ar_en = transliteration
        this.en = translation
        this.source = source
        this.title = title
    }

    @Throws(InstantiationException::class)
    constructor(json: String) {
        try {
            val dua = Gson().fromJson(json, Dua::class.java)
            this.ar = dua.ar
            this.ar_en = dua.ar_en
            this.en = dua.en
            this.source = dua.source
            this.title = dua.title
        } catch (ex: Exception) {
            throw InstantiationException("Error parsing string")
        }
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }

}