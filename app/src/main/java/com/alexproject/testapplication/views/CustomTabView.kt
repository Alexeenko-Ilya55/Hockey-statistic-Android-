package com.alexproject.testapplication.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import com.alexproject.testapplication.R


@SuppressLint("UseCompatLoadingForDrawables")
class CustomTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var tabItemClickListener: TabItemClickListener
    var tabsNames = listOf<String>()
    private val tabs = mutableListOf<Button>()
    private val params = LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT
    )

    init {
        this.orientation = HORIZONTAL
        this.background = context.getDrawable(R.color.transparent)

        params.setMargins(
            context.resources.getDimension(R.dimen.large_margin).toInt(),
            context.resources.getDimension(R.dimen.small_margin).toInt(),
            0,
            context.resources.getDimension(R.dimen.very_small_margin).toInt()
        )
        params.height = context.resources.getDimension(R.dimen.height_tabLayout).toInt()
    }

    private fun changePositionIndicator(activeTab: Int) {
        tabs.forEachIndexed { index, tab ->
            if (index == activeTab) {
                tab.setTextColor(context.getColor(R.color.colorLiveScore))
                tab.background = context.getDrawable(R.drawable.custom_border)
            } else {
                tab.setTextColor(context.getColor(R.color.tabMenu))
                tab.background = context.getDrawable(R.color.transparent)
            }
        }
    }

    fun setTabNames(tabsNames: List<String>) {
        this.tabsNames = tabsNames
        tabsNames.forEachIndexed { index, tabName ->
            val button = Button(context)
            button.text = tabName
            addView(button)
            button.layoutParams = params
            button.gravity = Gravity.CENTER
            button.setPadding(0, 0, 0, 0)
            button.setOnClickListener {
                tabItemClickListener.positionActiveTabChanged(index)
                changePositionIndicator(index)
            }
            tabs.add(button)
        }
        changePositionIndicator(0)
    }

    fun setClickListener(tabItemClickListener: TabItemClickListener) {
        this.tabItemClickListener = tabItemClickListener
    }
}

interface TabItemClickListener {
    fun positionActiveTabChanged(activeTabIndex: Int)
}