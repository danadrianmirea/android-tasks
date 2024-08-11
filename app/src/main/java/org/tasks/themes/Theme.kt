package org.tasks.themes

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.view.LayoutInflater
import org.tasks.R
import javax.inject.Inject

class Theme @Inject constructor(
    val themeBase: ThemeBase,
    val themeColor: ThemeColor,
    private val themeAccent: ThemeAccent
) {
    fun withThemeColor(themeColor: ThemeColor) = Theme(themeBase, themeColor, themeAccent)

    fun getLayoutInflater(context: Context) =
        wrap(context).getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun applyThemeAndStatusBarColor(activity: Activity) {
        applyTheme(activity)
        themeColor.applyToNavigationBar(activity)
        themeColor.applyTaskDescription(activity, activity.getString(R.string.app_name))
    }

    fun applyTheme(activity: Activity) {
        themeBase.set(activity)
        applyToContext(activity)
        activity.window.setFormat(PixelFormat.RGBA_8888)
    }

    fun applyToContext(context: Context) {
        val theme = context.theme
        themeColor.applyStyle(theme)
        themeAccent.applyStyle(theme)
    }

    private fun wrap(context: Context): Context {
        val wrapper = themeBase.wrap(context)
        applyToContext(wrapper)
        return wrapper
    }
}