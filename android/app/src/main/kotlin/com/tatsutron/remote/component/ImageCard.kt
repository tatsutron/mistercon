package com.tatsutron.remote.component

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.tatsutron.remote.R
import com.tatsutron.remote.util.FragmentMaker
import com.tatsutron.remote.util.Navigator

class ImageCard(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val layout: LinearLayout
    private val image: ImageView

    constructor(context: Context, attrs: AttributeSet)
            : this(context, attrs, defStyleAttr = 0)

    constructor(context: Context)
            : this(context, attrs = null, defStyleAttr = 0)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.component_image_card, this, true)
        layout = findViewById(R.id.layout)
        image = findViewById(R.id.image)
    }

    fun set(activity: Activity, url: String) {
        Glide.with(activity.applicationContext)
            .load(Uri.parse(url))
            .into(image)
        setOnClickListener {
            Navigator.showScreen(
                activity as AppCompatActivity,
                FragmentMaker.image(url),
            )
        }
        layout.visibility = View.VISIBLE
    }
}