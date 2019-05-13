package me.mqueiroz.picpay.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.NonNull
import com.redmadrobot.inputmask.MaskedTextChangedListener

fun EditText.addTextWatcher(listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            listener(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.addMaskedTextWatcher(mask: String, listener: (String) -> Unit) {
    MaskedTextChangedListener.installOn(
            this,
            mask,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(maskFilled: Boolean, @NonNull extractedValue: String, @NonNull formattedValue: String) {
                    listener(extractedValue)
                }
            }
    )
}