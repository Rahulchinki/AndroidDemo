
package com.mytaxi.android_demo.activities;


import android.Manifest;
import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.espresso.IdlingResource;

import android.support.test.rule.GrantPermissionRule;

import com.mytaxi.android_demo.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

/*  Scenario:
    Test case for DriverSearch Test
    where we have to select the second Driver in the searhed list by Name
 */
public class DriverSearchTest {
    @Rule
    public ActivityTestRule<MainActivity> mMainActivity =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Rule
    public ActivityTestRule<AuthenticationActivity> mMainActivityauth =
            new ActivityTestRule<>(AuthenticationActivity.class);


    @Rule
    public GrantPermissionRule pRule = GrantPermissionRule.grant(
            Manifest.permission.ACCESS_FINE_LOCATION);

    private String sUserName = "crazydog335";
    private String sPassword = "venture";
    private IdlingResource mIdlingResource1;



    @Before

    public void setup() {


        mIdlingResource1 = mMainActivity.getActivity().getIdlingResource();

        if (doesViewExist(R.id.textSearch))

        {
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

            onView(withText("Logout")).perform(click());
        }


    }

    @After
    public void UnRegister(){

    }


    @Test
    public void searchDriver () {



        /*Step 1 : Enter valid username in the input field with heading  username

         */
        Espresso.onView(withId(R.id.edt_username)).perform(typeText(sUserName));

        /*Step 2: Enter valid password string associated with username

         */
        Espresso.onView(withId(R.id.edt_password)).perform(typeText(sPassword));

        /*Step 3: close the   keyboard

         */
        Espresso.closeSoftKeyboard();

        /*Step 4: Press the Login Button

         */
        Espresso.onView(withId(R.id.btn_login)).perform(click());




        while(!(doesViewExist(R.id.textSearch)))
        {
            IdlingRegistry.getInstance().register(mIdlingResource1);
        }
        //Step 5: Type the string to be searched
        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));

        IdlingRegistry.getInstance().unregister(mIdlingResource1);
        onView(withId(R.id.textSearch)).perform(typeText("sa"));


        /*Step 6: Check that list of driver names is displayed in the drop down and click on second name in the list Sarah Scott

         */
        while(!(doesViewExist(R.id.searchContainer)))
        {
            IdlingRegistry.getInstance().register(mIdlingResource1);
        }

        onView(withId(R.id.searchContainer)).check(matches(isDisplayed()));
        IdlingRegistry.getInstance().unregister(mIdlingResource1);
        onView(withText(containsString("Sarah"))).inRoot(withDecorView(not(mMainActivity.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

        /*Step 7: Click on the Second Name

         */
        onView(withText("Sarah Scott")).inRoot(withDecorView(not(mMainActivity.getActivity().getWindow().getDecorView()))).perform(click());

        /*Step 8: Check that the driver Profle is of  the seond name selected

         */
        onView(withId(R.id.textViewDriverName)).check(matches(withText("Sarah Scott")));

        /*Step 9: Call the Driver

         */
        onView(withId(R.id.fab)).perform(click());


    }


    public boolean doesViewExist ( int id){
        try {
            onView(withId(id)).check(matches(isDisplayed()));
            return true;
        } catch (NoMatchingViewException e) {
            return false;
        }
    }
}







