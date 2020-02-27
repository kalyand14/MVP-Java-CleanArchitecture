package com.android.basics.features.todo.presentation.splash;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.android.basics.features.todo.presentation.login.LoginActivity;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> activityTestRule = new ActivityTestRule<>(
            SplashActivity.class);

    Instrumentation.ActivityMonitor monitor = InstrumentationRegistry.getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);

    private SplashActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        activity = null;
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = ApplicationProvider.getApplicationContext();
        TestCase.assertEquals("com.android.basics", appContext.getPackageName());
    }

    @Test
    public void testLaunchOfLoginScreen() {

        Activity activity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitor, 1100);
        assertNotNull(activity);

    }
}