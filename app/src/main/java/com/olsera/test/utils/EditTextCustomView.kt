package com.olsera.test.utils

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.olsera.test.R


class EditTextCustomView : LinearLayout {
    private var tvEditTextName: EditText? = null

    constructor(context: Context?) : super(context) { init() }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init() }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init() }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) { init() }

    private fun init() {
        inflate(context, R.layout.edittext_layout, this)

        tvEditTextName = findViewById(R.id.tvEditTextName)

        tvEditTextName!!.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                tvEditTextName!!.background = ContextCompat.getDrawable(context, R.drawable.border_blue)
            } else {
                tvEditTextName!!.setBackgroundColor(Color.parseColor("#19000000"))
            }
        }
    }

    fun getText(): String {
        return tvEditTextName!!.text.toString()
    }

    fun setText(text: String?) {
        tvEditTextName!!.setText(text)
    }

    fun setError(text: String?) {
        tvEditTextName!!.error = text
    }
}