package com.example.penelope.readingroom;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class LoginActivityTest {

    @Rule
    public IntentsTestRule<LoginActivity> mActivityRule =
            new IntentsTestRule<>(LoginActivity.class);

    private LoginActivity mLoginActivity;
    private TextView appName;
    private Button loginButton;

    /**
     * Set up variable declarations.
     */
    @Before
    public void setUp() {
        mLoginActivity = mActivityRule.getActivity();
        appName = (TextView) mLoginActivity.
                findViewById(R.id.enter_name_field);
        loginButton = (Button) mLoginActivity.
                findViewById(R.id.get_started_button);
    }

    //what is this doing? anything?
    @Rule
    public final ActivityTestRule<LoginActivity> main = new ActivityTestRule<>
            (LoginActivity.class);



    @Test
    public void testLaunchingLoginActivityWhenNoUserIsCurrentlyLoggedIn() {
//        if (Preferences.hasUsername(LoginActivity.class)) {
//            gotoSelectBookListActivity();
//            return;
        onView(withId(R.id.enter_name_field)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testLaunchingLoginActivityWhenCurrentUserIsLoggedIn() {

    }

//    @Test
//    public void testChangeText() {
//        // Type text and then press the button
////        onView(withId(R.id.enter_name_field)).perform(typeText("Penelope"), closeSoftKeyboard());
////        onView(withId(R.id.get_started_button)).perform(click());
//
//        onView(withId(R.id.enter_name_field)).perform(ViewActions.typeText("Penelope"),
//                ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.get_started_button)).perform(click());
//
//        assertTrue(Preferences.hasUsername(mLoginActivity));
//
//    }
}