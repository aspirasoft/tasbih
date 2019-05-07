package pk.aspirasoft.tasbih.models

import com.google.gson.Gson

class Tasbih {

    /**
     * Name of the Tasbih
     */
    val name: String

    /**
     * About the Tasbih
     */
    val description: String

    /**
     * Prayers in this Tasbih
     */
    val prayers: ArrayList<Dua> = ArrayList()

    /**
     * Number of times each prayer has to be recited
     */
    val prayerCounts: ArrayList<Int> = ArrayList()

    /**
     * Recitation count for this Tasbih
     */
    var counter: Int = 0
        set(value) {
            if (value < 0)
                throw ArithmeticException()

            field = value
        }

    /**
     * Maximum counter size
     */
    val countMax: Int
        get() {

            this.prayerCounts.sum().apply {
                return when {
                    this > 0 -> this
                    else -> 10000
                }
            }
        }

    /**
     * Creates a new Tasbih object from its string representation.
     *
     * @param [json] string representation of Tasbih returned by [Tasbih.toString] method
     * @throws InstantiationException if an invalid string representation is given
     */
    @Throws(InstantiationException::class)
    constructor(json: String) {
        try {
            val counter = Gson().fromJson(json, Tasbih::class.java)
            this.name = counter.name
            this.description = counter.description
            this.prayers.addAll(counter.prayers)
            this.counter = counter.counter
            this.prayerCounts.addAll(counter.prayerCounts)
        } catch (ex: Exception) {
            throw InstantiationException("Error parsing string")
        }
    }

    /**
     * Creates a new Tasbih object.
     *
     * @param [name] name of the counter
     * @param [description] about the counter
     */
    constructor(name: String, description: String) {
        this.name = name
        this.description = description
        this.counter = 0
    }

    fun add(dua: Dua, max: Int) {
        this.prayers.add(dua)
        this.prayerCounts.add(max)
    }

    operator fun inc(): Tasbih {
        this.counter++
        return this
    }

    operator fun dec(): Tasbih {
        if (this.counter > 0) this.counter--
        return this
    }

    fun reset() {
        this.counter = 0
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }

}