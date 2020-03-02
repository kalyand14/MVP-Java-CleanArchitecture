package com.android.basics.features.todo.presentation.registration;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.android.basics.R;

import org.junit.Rule;
import org.junit.Test;

public class RegisterUserActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<RegisterUserActivity> activityTestRule = new ActivityTestRule<>(
            RegisterUserActivity.class);

    @Test
    public void testUIElements() {
        Espresso.onView(ViewMatchers.withId(R.id.txt_login_msg)).check(ViewAssertions.matches(ViewMatchers.withText("Sign Up")));
    }

}