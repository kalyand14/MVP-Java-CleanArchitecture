package com.android.basics.features.todo.presentation.registration;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.android.basics.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@RunWith(AndroidJUnit4.class)
@Config(manifest = Config.NONE)
public class RegisterUserActivityTest {

    @Rule
    public ActivityTestRule<RegisterUserActivity> activityTestRule = new ActivityTestRule<>(
            RegisterUserActivity.class);

    @Test
    public void testUIElements() {
        Espresso.onView(ViewMatchers.withId(R.id.txt_login_msg)).check(ViewAssertions.matches(ViewMatchers.withText("Sign Up")));
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).check(ViewAssertions.matches(ViewMatchers.withText("Log In")));
        Espresso.onView(ViewMatchers.withId(R.id.btn_signup)).check(ViewAssertions.matches(ViewMatchers.withText("Sign Up")));
        Espresso.onView(ViewMatchers.withId(R.id.edt_username)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.edt_password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.txt_login_msg)).check(ViewAssertions.matches(ViewMatchers.withText("Sign Up")));
        Espresso.onView(ViewMatchers.withId(R.id.txt_already_signup)).check(ViewAssertions.matches(ViewMatchers.withText("Already have an account?")));
    }

    @Test
    public void tes_register() {
        Espresso.onView(ViewMatchers.withId(R.id.edt_username)).perform(ViewActions.typeText("kalyan"));
        Espresso.onView(ViewMatchers.withId(R.id.edt_password)).perform(ViewActions.typeText("1234"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_signup)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Registering ...")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }
}