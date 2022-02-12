package com.sampleNote.work.ui

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.ViewMatchers.withResourceName
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.sampleNote.work.view.NoteListActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Abhishek Pathak on 03/10/2021.
 */
@RunWith(AndroidJUnit4::class)
class NoteListActivityTest {

    // Created a rule for Note List Activity
    @get:Rule
    public val activityRule: ActivityTestRule<NoteListActivity> =
        ActivityTestRule(NoteListActivity::class.java, true, true)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        //launching the activity that is required to test
        activityRule.launchActivity(Intent())
        // initializing init for intents
        Intents.init()
    }

    /*
        Test to verify our recyclerview is inflating data fine or not also item click is working fine or not
    */
    @Test
    fun verifyListItemClick() {
        onView(withResourceName("notes_recycler_view")).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
        )
    }

    /*
        Verifying a click operation on Fab button
    */
    @Test
    fun verifyFabButtonClick() {
        onView(withResourceName("fab")).perform(click())
    }


    @After
    @Throws(Exception::class)
    fun tearDown() {
        Intents.release()
    }
}