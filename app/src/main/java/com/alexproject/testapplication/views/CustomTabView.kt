package com.alexproject.testapplication.views

import android.content.Context
import android.icu.text.DisplayContext
import android.print.PrintAttributes
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginStart
import com.alexproject.testapplication.R
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout


class CustomTabView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : TabLayout(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)


    val tabItemView = TabItem(context)

    init {
        this.TabView(context).addView(tabItemView)
        tabItemView.text
    }

}