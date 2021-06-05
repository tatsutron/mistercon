package com.tatsutron.remote

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView

class EditCard(
    context: Context,
    attrs: AttributeSet? = null,
) : CardView(context, attrs) {

    private var label: TextView
    private var value: EditText

    init {
        View.inflate(context, R.layout.card_edit, this)
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
}
