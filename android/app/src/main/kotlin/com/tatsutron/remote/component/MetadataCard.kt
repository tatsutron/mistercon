package com.tatsutron.remote.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tatsutron.remote.R

class MetadataCard(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val layout: TextInputLayout
    private val editText: TextInputEditText

    constructor(context: Context, attrs: AttributeSet)
            : this(context, attrs, defStyleAttr = 0)

    constructor(context: Context)
            : this(context, attrs = null, defStyleAttr = 0)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.component_metadata_card, this, true)
        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.MetadataCard,
            defStyleAttr,
            0, // defStyleRes
        )
        layout = findViewById<TextInputLayout>(R.id.layout).apply {
            hint = context.getString(
                attributes.getResourceId(
                    R.styleable.MetadataCard_hint,
                    0, // defValue
                ),
            )
        }
        attributes.recycle()
        editText = findViewById(R.id.edit_text)
    }

    fun set(text: String) {
        editText.setText(text)
        layout.visibility = View.VISIBLE
    }
}