package com.lisap.equus;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.lisap.equus.ui.main.MainActivity;
import com.lisap.equus.ui.main.addupdatehorse.AddUpdateHorseActivity;
import com.lisap.equus.ui.main.navdrawer.healthcares.HealthCareListActivity;
import com.lisap.equus.ui.main.navdrawer.owners.OwnerListActivity;
import com.lisap.equus.ui.main.notes.NoteListActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.lisap.equus", appContext.getPackageName());
    }

    @Test
    public void launchAddHorseActivityTest() {
        onView(withId(R.id.activity_main_bottom_navigation)).perform(click());
        intended(hasComponent(AddUpdateHorseActivity.class.getName()));
    }

    @Test
    public void launchNoteListActivityTest() {
        onView(withId(R.id.menu_note)).perform(click());
        intended(hasComponent(NoteListActivity.class.getName()));
    }

    @Test
    public void openNavigationDrawerTest() {
        // check navigation view not visible
        onView(withId(R.id.activity_main_nav_view)).check(matches(not(isDisplayed())));

        // open navigation drawer
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        // check navigation view visible
        onView(withId(R.id.activity_main_nav_view)).check(matches(isDisplayed()));
    }

    @Test
    public void launchHealthCareListActivityTest() {
        // open nav drawer
        openNavigationDrawerTest();

        onView(withId(R.id.activity_main_drawer_recap)).perform(click());
        intended(hasComponent(HealthCareListActivity.class.getName()));
    }

    @Test
    public void launchOwnerListActivityActivityTest() {
        // open nav drawer
        openNavigationDrawerTest();

        onView(withId(R.id.activity_main_drawer_owners)).perform(click());
        intended(hasComponent(OwnerListActivity.class.getName()));
    }
}