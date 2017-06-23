package com.example.bhatt.baking;

import android.os.RemoteException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.security.RunAs;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by bhatt on 23-06-2017.
 */


/** Test for TabletView */

@RunWith(AndroidJUnit4.class)
public class MainActivityGridviewsw600ScreenTest {

    public static final String FirstItemingredients = "Graham Cracker crumbs";
    public static final String SecondItemingredients = "Recipe Introduction";
    public static final int Postion = 0;
    public static final int Postion2 = 1;


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
        onView(withId(R.id.fragment2)).check(matches(isDisplayed()));

        /********************************************************************************/


        onView(withText("Finish filling prep")).perform(ViewActions.scrollTo());
        onView(withId(R.id.stepsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(Postion,click()));



        /***************************************************************************************/

        onView(withId(R.id.exo_pause)).perform(click());
        onView(withId(R.id.exo_play)).perform(click());
        onView(withId(R.id.exo_ffwd)).perform(click());

        /****************************************************************************************/

        onView(withId(R.id.stepsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(Postion2,click()));

        onView(withId(R.id.exo_player_description)).check(matches(isDisplayed()));


        /********************************************************************************************/

        pressBack();



    }

}
