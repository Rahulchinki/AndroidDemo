package com.mytaxi.android_demo.activities;

import android.Manifest;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mytaxi.android_demo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

//import static com.mytaxi.android_demo.activities.recorded.childAtPosition;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.*;
import android.support.test.espresso.contrib.DrawerActions;
public class LogInTestCases {

   @Rule
 public ActivityTestRule<MainActivity> mMainActivity =
           new ActivityTestRule<MainActivity>(MainActivity.class);

    @Rule
    public ActivityTestRule<AuthenticationActivity> mMainActivityauth=
            new ActivityTestRule<>(AuthenticationActivity.class);





    @Rule
    public GrantPermissionRule pRule =GrantPermissionRule.grant(
            Manifest.permission.ACCESS_FINE_LOCATION);

    private String sUserName = "crazydog335";
    private String sPassword = "venture";
    private String sInvalidUsername = "someInvalidUSername";
    private String sInvalidPassword = "VENTURE";
    private IdlingResource mIdlingResource1;

    @Before
        public void setup()
{
    mIdlingResource1 = mMainActivity.getActivity().getIdlingResource();

if(doesViewExist(R.id.textSearch))

    {

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

        /*try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //Log ig out
        onView(withText("Logout")).perform(click());
    }



}

    @Test
    public void loginToMyTaxiPositiveScenario() {


        //Step 1 : Enter valid username in the input field with heading  username
        Espresso.onView(withId(R.id.edt_username)).perform(typeText(sUserName));

        //Step 2: Enter valid password string associated with username
        Espresso.onView(withId(R.id.edt_password)).perform(typeText(sPassword));

        //Step 3: close the   keyboard
        Espresso.closeSoftKeyboard();

        //Step 4: Press the Login Button
        Espresso.onView(withId(R.id.btn_login)).perform(click());

      /*  while(!(doesViewExist(R.id.textSearch)))
        {
            SystemClock.sleep(200);
        }
*/
        while(!(doesViewExist(R.id.textSearch)))
        {
            IdlingRegistry.getInstance().register(mIdlingResource1);
        }


        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

        IdlingRegistry.getInstance().unregister(mIdlingResource1);



        //Step 5:Verify that the user name is displayed correctly
        Espresso.onView(withText(sUserName)).check(matches(isDisplayed()));




        onView(withText("Logout")).perform(click());

    }


    @Test
    public void checkInvalidLoginUsername() {

        //Step 1 : Enter invalid username in the input field with heading  username
        onView(withId(R.id.edt_username)).perform(typeText(sInvalidUsername));

        //Step 2: Enter valid password string associated with username
        onView(withId(R.id.edt_password)).perform(typeText(sPassword));

        //Step 3: close the   keyboard
        Espresso.closeSoftKeyboard();

        //Step 4: Press the Login Button
        onView(withId(R.id.btn_login)).perform(click());


        //Step 5:Check that the login failed message pops up
        onView(withId(android.R.id.content)).check(matches(isDisplayed()));

    }


    @Test
    public void checkInvalidPassword() {
        //Step 1 : Enter valid username in the input field with heading  username
        onView(withId(R.id.edt_username)).perform(typeText(sUserName));

        //Step 2:
        // Enter invalid password string associated with username
        onView(withId(R.id.edt_password)).perform(typeText(sInvalidPassword));

        //Step 3: close the   keyboard
        Espresso.closeSoftKeyboard();

        //Step 4: Press the Login Button
        onView(withId(R.id.btn_login)).perform(click());


        //Step 5:Check that the login failed message pops up
        onView(withId(android.R.id.content)).check(matches(isDisplayed()));


    }


    public boolean doesViewExist(int id) {
        try {
            onView(withId(id)).check(matches(isDisplayed()));
            return true;
        } catch (NoMatchingViewException e) {
            return false;
        }
    }





}