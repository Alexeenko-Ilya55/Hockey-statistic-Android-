package com.alexproject.testapplication.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import com.alexproject.testapplication.R


@SuppressLint("UseCompatLoadingForDrawables")
class CustomTabView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : LinearLayout(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)


    private val tabFirst = Button(context)
    private val tabSecond = Button(context)
    private val params = LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT,
        0f
    )
    lateinit var tabItemClickListener: TabItemClickListener

    init {
        this.orientation = HORIZONTAL
        this.background = context.getDrawable(R.color.transparent)

        tabFirst.setText(R.string.reviewTabMenu)
        tabSecond.setText(R.string.h2hTabMenu)
        params.setMargins(24,16,0,8)
        tabFirst.layoutParams = params
        tabFirst.setPadding(0,0,0,0)
        tabFirst.gravity = Gravity.CENTER
        tabSecond.layoutParams = params
        tabSecond.setPadding(0,0,0,0)
        tabSecond.layoutParams.height = 110

        buttonReviewClicked()

        tabFirst.setOnClickListener {
            buttonReviewClicked()
            tabItemClickListener.firstTabClicked()
        }

        tabSecond.setOnClickListener {
            tabSecond.setTextColor(context.getColor(R.color.colorLiveScore))
            tabSecond.background = context.getDrawable(R.drawable.custom_border)

            tabFirst.setTextColor(context.getColor(R.color.tabMenu))
            tabFirst.background = context.getDrawable(R.color.transparent)

            tabItemClickListener.secondTabClicked()
        }

        this.addView(tabFirst)
        this.addView(tabSecond)
    }
    private fun buttonReviewClicked(){
        tabFirst.setTextColor(context.getColor(R.color.colorLiveScore))
        tabFirst.background = context.getDrawable(R.drawable.custom_border)

        tabSecond.setTextColor(context.getColor(R.color.tabMenu))
        tabSecond.background = context.getDrawable(R.color.transparent)
    }

    fun setClickListener(tabItemClickListener: TabItemClickListener) {
        this.tabItemClickListener = tabItemClickListener
    }
}

interface TabItemClickListener{
    fun firstTabClicked()
    fun secondTabClicked()
}