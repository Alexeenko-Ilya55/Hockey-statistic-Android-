package com.alexproject.testapplication.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


class GameEventsItemView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)


    private val timerTextView = TextView(context)
    private val imageView = ImageView(context)

    private val playerTextView = TextView(context)
    private val assistsTextView = TextView(context)
    private val linearLayout = LinearLayout(context)

    val paramsLinearLayout = LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT,
        1f
    )
    val paramsImage = LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT,
        0f
    )
    val paramsTimer = LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT,
        0f
    )


    init {
        this.weightSum = 1f
        linearLayout.orientation = VERTICAL
        linearLayout.addView(playerTextView)
        playerTextView.textSize = 14f
        assistsTextView.textSize = 12f
        timerTextView.textSize = 13f
        playerTextView.layoutParams = paramsTimer
        linearLayout.layoutParams = paramsLinearLayout
        imageView.layoutParams = paramsImage


    }

    fun setSide(isHomeTeam: Boolean) {
        this.removeAllViews()
        with(this) {
            if (isHomeTeam) {
                gravity = Gravity.START
                addView(timerTextView)
                //timerTextView.gravity = Gravity.CENTER_VERTICAL
                addView(imageView)
                addView(linearLayout)
                //linearLayout.setVerticalGravity(Gravity.CENTER_VERTICAL)
            } else {
                gravity = Gravity.END
                addView(linearLayout)
                addView(imageView)
                addView(timerTextView)
                playerTextView.gravity = Gravity.END
            }
        }
    }

    fun setTimerText(text: String?) {
        timerTextView.text = text
    }

    fun setImage(resource: Int) {
        imageView.setImageResource(resource)
        imageView.layoutParams.height = 90
        imageView.layoutParams.height = 90
    }

    fun setPlayerText(text: String?) {
        playerTextView.text = text
    }

    fun setAssistsText(text: String?) {
        if (!text.isNullOrEmpty()) {
            linearLayout.removeView(assistsTextView)
            linearLayout.addView(assistsTextView)
            assistsTextView.text = text
        } else
            linearLayout.removeView(assistsTextView)
    }
}