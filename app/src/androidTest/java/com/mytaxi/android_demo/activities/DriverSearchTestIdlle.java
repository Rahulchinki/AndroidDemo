package com.mytaxi.android_demo.activities;


import android.Manifest;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;
import android.support.test.espresso.IdlingResource;

public class DriverSearchTestIdlle {
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
    private IdlingResource mIdlingResource;
    private IdlingResource mIdlingResource2;


    @Before

    public void setup() {

        mIdlingResource = mMainActivity.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
      //  Espresso.registerIdlingResources(mIdlingResource);
        IdlingRegistry.getInstance().register(mIdlingResource);


        mIdlingResource2 = mMainActivityauth.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
      //  Espresso.registerIdlingResources(mIdlingResource2);
        IdlingRegistry.getInstance().register(mIdlingResource2);



        if (doesViewExist(R.id.textSearch))

        {
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            //onView(withId(R.id.drawer_layout)).perform(swipeLeft());
            /*try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            //Log ig out
            onView(withText("Logout")).perform(click());
        }
    }


    @After
    public void TearDown()

    {
        if (mIdlingResource != null) {
                //Espresso.unregisterIdlingResources(mIdlingResource);
            IdlingRegistry.getInstance().unregister(mIdlingResource);


        }

        if (mIdlingResource2 != null) {
            Espresso.unregisterIdlingResources(mIdlingResource2);
            IdlingRegistry.getInstance().unregister(mIdlingResource2);


        }

    }
        @Test
        public void doMainActivityTest () {



            //Step 1 : Enter valid username in the input field with heading  username
            Espresso.onView(withId(R.id.edt_username)).perform(typeText(sUserName));

            //Step 2: Enter valid password string associated with username
            Espresso.onView(withId(R.id.edt_password)).perform(typeText(sPassword));

            //Step 3: close the   keyboard
            Espresso.closeSoftKeyboard();

            //Step 4: Press the Login Button
            Espresso.onView(withId(R.id.btn_login)).perform(click());

         /*   //Step 5:Verify that the user name is displayed correctly
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }

           onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
            Espresso.onView(withText(sUserName)).check(matches(isDisplayed()));
            onView(withId(R.id.drawer_layout)).perform(DrawerActions.close());
        */
           try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //click to search driver
            onView(withId(R.id.textSearch)).perform(typeText("sa")); //enter text "sa" search text

            /*try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }*/


            onView(withId(R.id.searchContainer)).check(matches(isDisplayed()));


            /*try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }*/


            onView(withText(containsString("Sarah"))).inRoot(withDecorView(not(mMainActivity.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
            onView(withText("Sarah Scott")).inRoot(withDecorView(not(mMainActivity.getActivity().getWindow().getDecorView()))).perform(click());

            onView(withId(R.id.textViewDriverName)).check(matches(withText("Sarah Scott")));

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
