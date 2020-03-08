package com.android.basics.features.todo.presentation.todo.edit;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;

import com.android.basics.TestUtil;
import com.android.basics.core.utils.EspressoIdlingResource;
import com.android.basics.di.ApplicationScope;
import com.android.basics.features.todo.data.repository.FakeTodoDataRepository;
import com.android.basics.features.todo.domain.model.Todo;
import com.android.basics.features.todo.domain.model.User;
import com.android.basics.features.todo.domain.repository.TodoRepository;
import com.android.basics.features.todo.presentation.components.TodoSession;
import com.android.basics.features.todo.presentation.components.UserSession;
import com.android.basics.features.todo.presentation.robot.EditTodoRobot;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static com.android.basics.core.utils.ScreenRobot.withRobot;

public class EditTodoActivityTest {

    @Rule
    public ActivityTestRule<EditTodoActivity> activityRule =
            new ActivityTestRule<EditTodoActivity>(EditTodoActivity.class, false, false);


    private void launchActivity() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), EditTodoActivity.class);
        activityRule.launchActivity(intent);
    }

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
        FakeTodoDataRepository todoDataRepository = new FakeTodoDataRepository();
        ApplicationScope.getInstance().getContainer().destroy(TodoRepository.class);
        ApplicationScope.getInstance().getModule().setTodoRepository(todoDataRepository);
        todoDataRepository.deleteAllTodo();
        UserSession.getInstance().setUser(new User(TestUtil.USER_ID, TestUtil.USER_NAME, TestUtil.PASSWORD));
        Todo newTodo = new Todo(TestUtil.TODO_ID, TestUtil.USER_ID, TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.getFormattedDateString(1), false);
        TodoSession.getInstance().setTodo(newTodo);
        todoDataRepository.addTodo(newTodo);
    }

    @Test
    public void shouldDisplayScreenTitle() {
        launchActivity();
        withRobot(EditTodoRobot.class).checkIsScreenTitleShown();
    }

    @Test
    public void shouldDisplayScreenHeader() {
        launchActivity();
        withRobot(EditTodoRobot.class).checkIsScreenHeaderShown();
    }

    @Test
    public void checkLabelsAreDisplayed() {
        launchActivity();
        withRobot(EditTodoRobot.class).checkLabelsAreDisplayed();
    }

    @Test
    public void checkButtonLabels() {
        launchActivity();
        withRobot(EditTodoRobot.class).checkButtonLabels();
    }

    @Test
    public void checEditTextValues() {
        launchActivity();
        withRobot(EditTodoRobot.class).checkEditTextValues(TestUtil.NAME, TestUtil.DESCRIPTION, TestUtil.getFormattedDateString(1));
    }

    @Test
    public void shouldDisplaySuccessDialogWhenFieldsAreValid() {
        launchActivity();
        withRobot(EditTodoRobot.class)
                .provideActivityContext(activityRule.getActivity())
                .clearText()
                .fillFormAndSubmit(TestUtil.EDIT_NAME, TestUtil.EDIT_DESCRIPTION, TestUtil.getDateString(2))
                .isUpdateSuccess();
    }

    @Test
    public void shouldDisplayErrorDialogWhenFieldsAreNotValid() {
        launchActivity();
        withRobot(EditTodoRobot.class)
                .provideActivityContext(activityRule.getActivity())
                .clearText()
                .fillFormAndSubmit("", TestUtil.EDIT_DESCRIPTION, TestUtil.getDateString(2))
                .isUpdateFailure();

    }

    @Test
    public void checkForDelete() {
        launchActivity();
        withRobot(EditTodoRobot.class)
                .provideActivityContext(activityRule.getActivity())
                .deleteTodo()
                .isDeleteSuccess();
    }
}