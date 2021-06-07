package com.tatsutron.remote

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide

class ImageCard(
    context: Context,
    attrs: AttributeSet? = null,
) : CardView(context, attrs) {

    private var label: TextView
    private var image: ImageView

    init {
        View.inflate(context, R.layout.card_image, this)
        label = findViewById(R.id.label)
        image = findViewById(R.id.image)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Card,
            0,
            0,
        ).apply {
            label.text = getString(R.styleable.Card_label)
        }
    }

    fun set(imageUrl: String?, activity: FragmentActivity?) {
        if (imageUrl?.isNotEmpty() == true) {
            visibility = View.VISIBLE
            Glide.with(this)
                .load(Uri.parse(imageUrl))
                .into(image)
            image.setOnClickListener {
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.setTransition(FragmentTransaction.TRANSIT_NONE)
                    ?.add(R.id.root, FragmentMaker.image(imageUrl))
                    ?.addToBackStack(null)
                    ?.commit()
            }
        } else {
            visibility = View.GONE
        }
    }
}
