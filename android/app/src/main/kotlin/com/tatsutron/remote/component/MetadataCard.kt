package com.tatsutron.remote.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.tatsutron.remote.R

class MetadataCard(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val layout: LinearLayout
    private val bodyText: TextView

    constructor(context: Context, attrs: AttributeSet)
            : this(context, attrs, defStyleAttr = 0)

    constructor(context: Context)
            : this(context, attrs = null, defStyleAttr = 0)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.component_metadata_card, this, true)
        layout = findViewById(R.id.layout)
        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.MetadataCard,
            defStyleAttr,
            0, // defStyleRes
        )
        findViewById<TextView>(R.id.header_text).apply {
            text = context.getString(
                attributes.getResourceId(
                    R.styleable.MetadataCard_hint,
                    0, // defValue
                ),
            )
        }
        attributes.recycle()
        bodyText = findViewById(R.id.body_text)
    }

    fun set(text: String) {
        bodyText.text = text
        layout.visibility = View.VISIBLE
    }
}