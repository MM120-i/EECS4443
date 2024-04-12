package com.example.memorygame

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AppTest {
    @Rule
    var mActivityScenarioRule = ActivityScenarioRule(
        HomeActivity::class.java
    )

    @Test
    fun homeActivityTest() {
        val appCompatImageButton = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.settings_button),
                ViewMatchers.withContentDescription("Image button"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatImageButton.perform(ViewActions.click())
        val switch_ = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.vibration_switch),
                ViewMatchers.withText("Vibrate on Button Click:"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        switch_.perform(ViewActions.click())
        val materialButton = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.clear_database_button),
                ViewMatchers.withText("Clear Data"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton.perform(ViewActions.click())
        val materialButton2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(android.R.id.button1), ViewMatchers.withText("Yes"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(com.google.android.material.R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton2.perform(ViewActions.scrollTo(), ViewActions.click())
        val appCompatSpinner = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.rounds_spinner),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatSpinner.perform(ViewActions.click())
        val materialTextView = Espresso.onData(Matchers.anything())
            .inAdapterView(
                childAtPosition(
                    ViewMatchers.withClassName(Matchers.`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                    0
                )
            )
            .atPosition(0)
        materialTextView.perform(ViewActions.click())
        Espresso.pressBack()
        val materialButton3 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.rules_button), ViewMatchers.withText("Rules"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton3.perform(ViewActions.click())
        val materialButton4 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.readRulesButton), ViewMatchers.withText("Read Rules"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton4.perform(ViewActions.click())
        val materialButton5 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.readRulesButton), ViewMatchers.withText("Read Rules"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton5.perform(ViewActions.click())
        Espresso.pressBack()
        val materialButton6 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.begin_button), ViewMatchers.withText("Begin"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton6.perform(ViewActions.click())
        val materialButton7 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.startButton), ViewMatchers.withText("Start Round"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton7.perform(ViewActions.click())
        val materialButton8 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button7),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    6
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton8.perform(ViewActions.click())
        val materialButton9 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button9),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    8
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton9.perform(ViewActions.click())
        val materialButton10 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button3),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton10.perform(ViewActions.click())
        val materialButton11 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.startButton), ViewMatchers.withText("Start Round"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton11.perform(ViewActions.click())
        val materialButton12 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button6),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    5
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton12.perform(ViewActions.click())
        val materialButton13 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button7),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    6
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton13.perform(ViewActions.click())
        val materialButton14 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button5),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    4
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton14.perform(ViewActions.click())
        val materialButton15 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button12),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    11
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton15.perform(ViewActions.click())
        val materialButton16 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.startButton), ViewMatchers.withText("Start Round"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton16.perform(ViewActions.click())
        val materialButton17 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button8),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    7
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton17.perform(ViewActions.click())
        val materialButton18 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button2),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton18.perform(ViewActions.click())
        val materialButton19 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button8),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    7
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton19.perform(ViewActions.click())
        val materialButton20 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button10),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    9
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton20.perform(ViewActions.click())
        val materialButton21 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button12),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    11
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton21.perform(ViewActions.click())
        val materialButton22 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.startButton), ViewMatchers.withText("Start Round"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton22.perform(ViewActions.click())
        val materialButton23 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.restartButton), ViewMatchers.withText("Restart"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    7
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton23.perform(ViewActions.click())
        Espresso.pressBack()
        val appCompatImageButton2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.settings_button),
                ViewMatchers.withContentDescription("Image button"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatImageButton2.perform(ViewActions.click())
        val switch_2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.vibration_switch),
                ViewMatchers.withText("Vibrate on Button Click:"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        switch_2.perform(ViewActions.click())
        val appCompatSpinner2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.rounds_spinner),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatSpinner2.perform(ViewActions.click())
        val materialTextView2 = Espresso.onData(Matchers.anything())
            .inAdapterView(
                childAtPosition(
                    ViewMatchers.withClassName(Matchers.`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                    0
                )
            )
            .atPosition(1)
        materialTextView2.perform(ViewActions.click())
        val materialButton24 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.clear_database_button),
                ViewMatchers.withText("Clear Data"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton24.perform(ViewActions.click())
        val materialButton25 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(android.R.id.button1), ViewMatchers.withText("Yes"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(com.google.android.material.R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton25.perform(ViewActions.scrollTo(), ViewActions.click())
        Espresso.pressBack()
        val materialButton26 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.begin_button), ViewMatchers.withText("Begin"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton26.perform(ViewActions.click())
        val materialButton27 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.startButton), ViewMatchers.withText("Start Round"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton27.perform(ViewActions.click())
        val materialButton28 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button10),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    9
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton28.perform(ViewActions.click())
        val materialButton29 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button11),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    10
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton29.perform(ViewActions.click())
        val materialButton30 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button10),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    9
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton30.perform(ViewActions.click())
        val materialButton31 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.startButton), ViewMatchers.withText("Start Round"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton31.perform(ViewActions.click())
        val materialButton32 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button4),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    3
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton32.perform(ViewActions.click())
        val materialButton33 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button7),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    6
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton33.perform(ViewActions.click())
        val materialButton34 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button9),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    8
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton34.perform(ViewActions.click())
        val materialButton35 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.startButton), ViewMatchers.withText("Start Round"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton35.perform(ViewActions.click())
        val materialButton36 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button9),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    8
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton36.perform(ViewActions.click())
        val materialButton37 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button6),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    5
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton37.perform(ViewActions.click())
        val materialButton38 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.startButton), ViewMatchers.withText("Start Round"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton38.perform(ViewActions.click())
        val materialButton39 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button3),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton39.perform(ViewActions.click())
        val materialButton40 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button7),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        1
                    ),
                    6
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton40.perform(ViewActions.click())
        val materialButton41 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.startButton), ViewMatchers.withText("Start Round"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton41.perform(ViewActions.click())
        val materialButton42 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.restartButton), ViewMatchers.withText("Restart"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    7
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton42.perform(ViewActions.click())
        Espresso.pressBack()
    }

    companion object {
        private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int
        ): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("Child at position $position in parent ")
                    parentMatcher.describeTo(description)
                }

                public override fun matchesSafely(view: View): Boolean {
                    val parent = view.parent
                    return parent is ViewGroup && parentMatcher.matches(parent) && view == parent.getChildAt(
                        position
                    )
                }
            }
        }
    }
}