package com.example.todo.todoui
import android.content.Context
import androidx.core.content.edit


class SharedPreferenceManager(
    context: Context
) {
    private val preference = context.getSharedPreferences(
        context.packageName,
        Context.MODE_PRIVATE
    )

    private val keyName = "Tasks"

    var tasks: List<String>
        get() = preference.getStringSet(keyName, emptySet())?.toList() ?: emptyList()
        set(value) {
            preference.edit {
                putStringSet(keyName, value.toSet())
            }
        }

    fun clear() {
        preference.edit().clear().apply()
    }
}