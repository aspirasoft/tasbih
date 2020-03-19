package pk.aspirasoft.tasbih.models

import com.google.gson.Gson
import java.util.*

object CounterManager : ArrayList<Tasbih>() {

    init {
        diskIn()
    }

    override fun add(element: Tasbih): Boolean {
        return when {
            this.any { it.name.equals(element.name, true) } -> false
            super.add(element) -> {
                diskOut()
                true
            }
            else -> false
        }
    }

    override fun add(index: Int, element: Tasbih) {
        super.add(index, element)
        diskOut()
    }

    override fun addAll(elements: Collection<Tasbih>): Boolean {
        val r = super.addAll(elements)
        diskOut()
        return r
    }

    override fun addAll(index: Int, elements: Collection<Tasbih>): Boolean {
        val r = super.addAll(index, elements)
        diskOut()
        return r
    }

    override fun remove(element: Tasbih): Boolean {
        val r = super.remove(element)
        diskOut()
        return r
    }

    override fun removeAll(elements: Collection<Tasbih>): Boolean {
        val r = super.removeAll(elements)
        diskOut()
        return r
    }

    override fun removeAt(index: Int): Tasbih {
        val r = super.removeAt(index)
        diskOut()
        return r
    }

    override fun removeRange(fromIndex: Int, toIndex: Int) {
        super.removeRange(fromIndex, toIndex)
        diskOut()
    }

    override fun clear() {
        super.clear()
        diskOut()
    }

    fun replaceAll(elements: ArrayList<Tasbih>) {
        this.clear()
        this.addAll(elements)

        diskOut()
    }

    private fun diskIn() {
        Database.get("counters")?.let {
            fromJson(it)
        }
    }

    fun diskOut() {
        Database.put("counters", toString())
    }

    private fun fromJson(json: String) {
        Gson().fromJson(json, CounterManager::class.java)?.let {
            this.replaceAll(it)
        }
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }

    override val size: Int
        get() {
            diskIn()
            return super.size
        }

}