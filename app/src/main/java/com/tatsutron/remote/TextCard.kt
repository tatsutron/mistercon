package com.tatsutron.remote

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView

class TextCard(
    context: Context,
    attrs: AttributeSet? = null,
) : CardView(context, attrs) {

    private var label: TextView
    private var value: TextView

    init {
        View.inflate(context, R.layout.card_text, this)
        label = findViewById(R.id.label)
        value = findViewById(R.id.value)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Card,
            0,
            0,
        ).apply {
            label.text = getString(R.styleable.Card_label)
        }
    }

    fun set(text: String?) {
        if (text?.isNotEmpty() == true) {
            value.text = text
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }
    }
}
