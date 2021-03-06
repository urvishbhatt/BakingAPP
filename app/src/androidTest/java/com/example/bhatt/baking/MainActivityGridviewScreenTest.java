package com.example.bhatt.baking;

import android.os.RemoteException;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ReplaceTextAction;
import android.support.test.espresso.action.ScrollToAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;


import 	android.support.test.espresso.contrib.RecyclerViewActions;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static com.example.bhatt.baking.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

/**
 * Created by bhatt on 20-06-2017.
 */

/** Test for MobileView */

@RunWith(AndroidJUnit4.class)
public class MainActivityGridviewScreenTest {

    public static final String FirstItemingredients = "Graham Cracker crumbs";
    public static final String SecondItemingredients = "Recipe Introduction";
    public static final int Postion = 0;

    @Rule
    public ActivityTestRule<MainActivity> mMainActivity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onclick_gridview(){



        onView(withId(R.id.RecyclerView)).check(matches(isDisplayed()));

        /********************************************************************************/

        onView(withId(R.id.RecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(Postion,click()));


        /***************************************************************************/

        onView(withText(FirstItemingredients)).check(matches(isDisplayed()));

        /************************************************************************/

        onView(withId(R.id.stepsList)).check(matches(hasDescendant(withText(SecondItemingredients))));

        /******************************************************************************/

        onView(withId(R.id.fragment1)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment2)).check(doesNotExist());

        /********************************************************************************/


        onView(withText("Finish filling prep")).perform(ViewActions.scrollTo());
        onView(withId(R.id.stepsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(Postion,click()));

        /***********************************************************************************/




        /************************************************************************************/

        UiDevice device = UiDevice.getInstance(getInstrumentation());

        try {
            device.setOrientationLeft();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        /***************************************************************************************/


        onView(withId(R.id.exo_player_description)).check(matches(not(isDisplayed())));

        onView(withId(R.id.exo_pause)).perform(click());
        onView(withId(R.id.exo_play)).perform(click());
        onView(withId(R.id.exo_ffwd)).perform(click());

        /****************************************************************************************/

        pressBack();


    }
}