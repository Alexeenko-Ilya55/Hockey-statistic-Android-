package com.alexproject.testapplication.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.alexproject.testapplication.R


class GameEventsItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : LinearLayout(context, attrs, defStyleAttr) {

    private val timerTextView = TextView(context)
    private val imageView = ImageView(context)
    private val playerTextView = TextView(context)
    private val assistsTextView = TextView(context)
    private val linearLayout = LinearLayout(context)
    private val timerParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    private val linearLayoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    private val playerParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    private val assistsParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

    init {
        this.orientation = HORIZONTAL
        linearLayout.orientation = VERTICAL
        linearLayout.addView(playerTextView)
        playerTextView.textSize = 13f
        assistsTextView.textSize = 12f
        timerTextView.textSize = 13f
        timerTextView.gravity = Gravity.CENTER

        timerTextView.setTypeface(null, Typeface.BOLD)
        playerTextView.setTypeface(null, Typeface.BOLD)

        timerParams.gravity = Gravity.CENTER_VERTICAL
        timerTextView.layoutParams = timerParams

        linearLayoutParams.gravity = Gravity.CENTER_VERTICAL
        linearLayout.layoutParams = linearLayoutParams

        imageView.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val marginParams = MarginLayoutParams(imageView.layoutParams)
        marginParams.setMargins(
            resources.getDimension(R.dimen.small_margin).toInt(), 0,
            resources.getDimension(R.dimen.small_margin).toInt(), 0
        )
        imageView.layoutParams = marginParams
    }

    fun setSide(isHomeTeam: Boolean) {
        this.removeAllViews()
        if (isHomeTeam) {
            gravity = Gravity.START
            addView(timerTextView)
            addView(imageView)
            addView(linearLayout)
            playerParams.gravity = Gravity.START
            assistsParams.gravity = Gravity.START
        } else {
            gravity = Gravity.END
            addView(linearLayout)
            addView(imageView)
            addView(timerTextView)
            assistsParams.gravity = Gravity.END
            playerParams.gravity = Gravity.END
        }
        assistsTextView.layoutParams = assistsParams
        playerTextView.layoutParams = playerParams
    }

    fun setTimerText(text: String?) {
        timerTextView.text = text
    }

    fun setImage(resource: Int) {
        imageView.setImageResource(resource)
        imageView.updateLayoutParams {
            this.height = 90
            this.width = 90
        }
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