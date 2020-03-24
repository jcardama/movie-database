@file:Suppress("PrivatePropertyName", "unused")

package com.jcardama.moviedatabase.core.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun <T> EditText.changeListener(listener: (field: EditText?, s: String) -> T?) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener.invoke(this@changeListener, s.toString())
        }
    })
}
