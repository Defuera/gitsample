package ru.justd.githubrepos.app.widgets

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import kotterknife.bindView
import ru.justd.githubrepos.R

class ErrorWidget(context: Context) : LinearLayout(context) {

    private val errorTextView by bindView<TextView>(R.id.error_text)

    @StringRes
    var errorText: Int = 0
        set(value) {
            errorTextView.setText(value)
            field = value
        }

    init {
        View.inflate(context, R.layout.widget_error, this)
        gravity = Gravity.CENTER
        orientation = VERTICAL
    }

}