package ict2105.team02.application.utils

import android.text.Editable
import android.text.TextWatcher

class TextChangeListener(private val textChanged: (s: Editable?) -> Unit,) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        textChanged(s)
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}