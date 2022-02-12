package com.sampleNote.work.ui

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.ViewMatchers.withResourceName
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.sampleNote.work.view.EditTextActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Abhishek Pathak on 03/10/2021.
 */
@RunWith(AndroidJUnit4::class)
class EditTextActviityTest {

    // Created a rule for Note List Activity
    @get:Rule
    public val activityRule: ActivityTestRule<EditTextActivity> =
        ActivityTestRule(EditTextActivity::class.java, true, true)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        //launching the activity that is required to test
        activityRule.launchActivity(Intent())
        // initializing init for intents
        Intents.init()
    }

    @Test
    fun changeText_sameActivity() {
        // Type text and then press the button.
        onView(withResourceName("edittext"))
            .perform(typeText(MessageChanged), closeSoftKeyboard())
        onView(withResourceName("clickBtn")).perform(click())

        // Check that the text was changed.
        onView(withResourceName("TextLabel"))
            .check(matches(withText(MessageChanged)))
    }


    @After
    @Throws(Exception::class)
    fun tearDown() {
        Intents.release()
    }

    companion object {
        const val MessageChanged = "2nd Message"
    }
}