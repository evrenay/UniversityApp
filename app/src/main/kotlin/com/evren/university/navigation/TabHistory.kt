package com.evren.university.navigation

import java.io.Serializable
import java.util.*

class TabHistory : Serializable {


    private val stack: ArrayList<Int> = ArrayList()

    var activeTab = -1

    private val isEmpty: Boolean
        get() = stack.size == 0

    val size: Int
        get() = stack.size

    fun push(entry: Int) {
        stack.add(activeTab)
        activeTab = entry
    }

    fun popPrevious(): Int {
        var entry = -1

        if (!isEmpty) {
            entry = stack[stack.size - 1]
            stack.removeAt(stack.size - 1)
            activeTab = entry
        }
        return entry
    }

    fun clear() {
        stack.clear()
    }
}