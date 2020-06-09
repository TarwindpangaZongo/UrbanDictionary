package com.example.urbandictionary.view

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.urbandictionary.R
import com.example.urbandictionary.view.CustomAssertions.Companion.hasItemCount
import com.example.urbandictionary.view.CustomMatchers.Companion.withRecyclerView
import com.example.urbandictionary.views.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_ui_display(){
        onView(withId(R.id.term_search)).check(matches(isDisplayed()))
    }

    @Test
    fun test_recyclerView_contents() {
        onView(withId(R.id.term_search)).perform(typeSearchViewText("pokemon"))

        //verify recyclerView items count
        onView(withId(R.id.termRV)).check(hasItemCount(2))

        //verify item content at first position of recyclerView
        onView(withRecyclerView(R.id.termRV)?.atPosition(0))
            .check(matches(hasDescendant(withText("definition 1"))))
            .check(matches(hasDescendant(withText("author"))))
    }


    fun typeSearchViewText(text: String?): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                //Ensure that only apply if it is a SearchView and if it is visible.
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun getDescription(): String {
                return "Change view text"
            }

            override fun perform(uiController: UiController?, view: View) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }
}