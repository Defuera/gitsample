package ru.justd.githubrepos.repositories.robots

import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import ru.justd.githubrepos.R

class RepositoriesRobot {

    fun checkScreenDisplayed() {
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
    }

    fun checkLoaderIsDisplayed() {
        onView(withId(ru.justd.lilwidgets.R.id.progress_bar)).check(matches(isDisplayed()))
    }

    fun checkErrorIsDisplayed(@StringRes errorText: Int) {
        onView(
            allOf(
                withId(R.id.error_text),
                withText(errorText)
            )
        ).check(matches(isDisplayed()))
    }

}