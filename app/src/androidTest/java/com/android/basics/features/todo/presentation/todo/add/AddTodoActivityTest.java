package com.android.basics.features.todo.presentation.todo.add;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;

import com.android.basics.TestUtil;
import com.android.basics.core.utils.EspressoIdlingResource;
import com.android.basics.di.ApplicationScope;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.presentation.components.UserSession;
import com.android.basics.features.todo.presentation.robot.AddTodoRobot;
import com.android.basics.features.todo.presentation.robot.HomeScreenRobot;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static com.android.basics.core.utils.ScreenRobot.withRobot;

public class AddTodoActivityTest {


    @Rule
    public ActivityTestRule<AddTodoActivity> activityRule =
            new ActivityTestRule<AddTodoActivity>(AddTodoActivity.class);

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests
     * significantly more reliable.
     */
    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    @BeforeClass
    public static void doCleanUp() {
        ApplicationScope.getInstance().todoRepository().deleteAllTodo();
        UserSession.getInstance().setUser(new User(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD));
    }

    @Test
    public void shouldDisplayScreenTitle() {
        withRobot(AddTodoRobot.class).checkIsScreenTitleShown();
    }

    @Test
    public void shouldDisplayScreenHeader() {
        withRobot(AddTodoRobot.class).checkIsScreenHeaderShown();
    }

    @Test
    public void checkLabelsAreDisplayed() {
        withRobot(AddTodoRobot.class).checkLabelsAreDisplayed();
    }

    @Test
    public void checkButtonLabels() {
        withRobot(AddTodoRobot.class).checkButtonLabels();
    }

    @Test
    public void shouldDisplaySuccessDialogWhenFieldsAreValid() {
        withRobot(AddTodoRobot.class)
                .provideActivityContext(activityRule.getActivity())
                .fillFormAndSubmit(TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.getDateString())
                .isSuccess();
    }

    @Test
    public void shouldDisplayErrorDialogWhenFieldsAreNotValid() {
        withRobot(AddTodoRobot.class)
                .provideActivityContext(activityRule.getActivity())
                .fillFormAndSubmit("", TestUtil.DESCRIPTION, TestUtil.getDateString())
                .isFailure();
    }

    @Test
    public void checkCancelButtonFlow() {

        HomeScreenRobot homeScreenRobot = withRobot(AddTodoRobot.class)
                .cancel();

        homeScreenRobot.checkIsScreenTitleShown(TestUtil.USER_NAME);

    }

}