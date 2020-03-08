package com.android.basics.core.utils;

import android.app.Activity;
import android.widget.DatePicker;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Calendar;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.android.basics.features.todo.presentation.robot.CustomMatchers.withResourceName;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

@SuppressWarnings("unchecked")
public abstract class ScreenRobot<T extends ScreenRobot> {

    private Activity activityContext; // Only required for some calls

    public static <T extends ScreenRobot> T withRobot(Class<T> screenRobotClass) {
        if (screenRobotClass == null) {
            throw new IllegalArgumentException("instance class == null");
        }

        try {
            return screenRobotClass.newInstance();
        } catch (IllegalAccessException iae) {
            throw new RuntimeException("IllegalAccessException", iae);
        } catch (InstantiationException ie) {
            throw new RuntimeException("InstantiationException", ie);
        }
    }

    public T checkIsDisplayed(@IdRes int... viewIds) {
        for (int viewId : viewIds) {
            onView(withId(viewId)).check(matches(isDisplayed()));
        }
        return (T) this;
    }

    public T checkIsHidden(@IdRes int... viewIds) {
        for (int viewId : viewIds) {
            onView(withId(viewId)).check(matches(not(isDisplayed())));
        }
        return (T) this;
    }

    public T checkViewHasText(@IdRes int viewId, String expected) {
        onView(withId(viewId)).check(matches(withText(expected)));
        return (T) this;
    }

    public T checkViewHasText(@IdRes int viewId, @StringRes int messageResId) {
        onView(withId(viewId)).check(matches(withText(messageResId)));
        return (T) this;
    }

    public T checkViewHasHint(@IdRes int viewId, @StringRes int messageResId) {
        onView(withId(viewId)).check(matches(withHint(messageResId)));
        return (T) this;
    }

    public T clickOnView(@IdRes int viewId) {
        onView(withId(viewId)).perform(click());
        return (T) this;
    }

    public T enterTextIntoView(@IdRes int viewId, String text) {
        onView(withId(viewId)).perform(typeText(text));
        return (T) this;
    }

    public T clearTextFromView(@IdRes int viewId) {
        onView(withId(viewId)).perform(clearText());
        return (T) this;
    }

    public T enterTextIntoViewAndCloseKeyBoard(@IdRes int viewId, String text) {
        onView(withId(viewId)).perform(typeText(text)).perform(closeSoftKeyboard());
        return (T) this;
    }

    public T provideActivityContext(Activity activityContext) {
        this.activityContext = activityContext;
        return (T) this;
    }

    public T checkDialogWithTextIsDisplayed(@StringRes int messageResId) {
        onView(withText(messageResId))
                .inRoot(withDecorView(not(activityContext.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        return (T) this;
    }

    public T checkDialogWithButtonTextIsDisplayed(String text) {
        onView(withText(text))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
        return (T) this;
    }

    public T clickButtonTextOnDialog(String text) {
        onView(withText(text))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        return (T) this;
    }

    public T swipeLeftOnView(@IdRes int viewId) {
        onView(withId(viewId)).perform(swipeLeft());
        return (T) this;
    }

    public T checkActionBarTitle(@StringRes int messageResId) {
        onView(allOf(isDescendantOfA(withResourceName("R.id.action_bar_container")), withText(messageResId)))
                .check(matches(isDisplayed()));
        return (T) this;
    }

    public T checkToolbarTitle(CharSequence title) {
        onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(title))));

        return (T) this;
    }

    private Matcher<Object> withToolbarTitle(
            final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }

    public String getString(@StringRes int messageResId) {
        return ApplicationProvider.getApplicationContext().getResources().getString((messageResId));
    }

    public T enterDateIntoView(@IdRes int viewId, final int year, final int monthOfYear, final int dayOfMonth) {
        onView(withId(viewId)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withText(android.R.string.ok)).perform(click());

        return (T) this;
    }

}