package com.example.penelope.readingroom;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)

public class LoginActivityTest {

    private LoginActivity mLoginActivity;

    @Rule
    public final ActivityTestRule<LoginActivity> main = new ActivityTestRule<>
            (LoginActivity.class);

    @Test
    public void shouldBeAbleToLaunchLoginActivity() {
        onView(withText("Enter your name below")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testChangeText() {
        // Type text and then press the button
//        onView(withId(R.id.enter_name_field)).perform(typeText("Penelope"), closeSoftKeyboard());
//        onView(withId(R.id.get_started_button)).perform(click());

        onView(withId(R.id.enter_name_field)).perform(ViewActions.typeText("Penelope"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.get_started_button)).perform(click());

        assertTrue(Preferences.hasUsername(mLoginActivity));

    }

}
