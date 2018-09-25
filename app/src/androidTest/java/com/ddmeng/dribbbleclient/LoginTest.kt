package com.ddmeng.dribbbleclient

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.web.assertion.WebViewAssertions.webMatches
import android.support.test.espresso.web.model.Atoms.getCurrentUrl
import android.support.test.espresso.web.sugar.Web.onWebView
import android.support.test.espresso.web.webdriver.DriverAtoms
import android.support.test.espresso.web.webdriver.DriverAtoms.findElement
import android.support.test.espresso.web.webdriver.DriverAtoms.webClick
import android.support.test.espresso.web.webdriver.Locator
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.ddmeng.dribbbleclient.util.CountingAppExecutorsRule
import com.ddmeng.dribbbleclient.util.DataBindingIdlingResourceRule
import com.ddmeng.dribbbleclient.util.TaskExecutorWithIdlingResourceRule
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginTest {

    @Rule
    @JvmField
    var activityTestRule: ActivityTestRule<MainActivity> = object : ActivityTestRule<MainActivity>(MainActivity::class.java, false, true) {
        override fun afterActivityLaunched() {
            InstrumentationRegistry.getTargetContext().deleteDatabase("dribbble_client_database")
            InstrumentationRegistry.getTargetContext().deleteSharedPreferences("dribbble_client_shared_preferences")
        }
    }

    @Rule
    @JvmField
    val countingAppExecutors = CountingAppExecutorsRule()
    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()
    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityTestRule)

    @Before
    fun setUp() {
        activityTestRule.activity.appExecutors = countingAppExecutors.appExecutors
    }

    @Test
    fun testOpenLoginPage() {

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.login_button)).perform(click())

        onWebView(withId(R.id.webview)).forceJavascriptEnabled()
        onWebView().check(webMatches(getCurrentUrl(), containsString("https://dribbble.com/login?")))
    }

    @Test
    fun testLogin() {

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.login_button)).perform(click())

        onWebView(withId(R.id.webview)).forceJavascriptEnabled()
        onWebView().check(webMatches(getCurrentUrl(), containsString("https://dribbble.com/login?")))

        onWebView()
                .withElement(findElement(Locator.ID, "login"))
                .perform(DriverAtoms.webKeys("dribbbletest123"))
                .withElement(findElement(Locator.ID, "password"))
                .perform(DriverAtoms.webKeys("test123456"))
                .withElement(findElement(Locator.CSS_SELECTOR, "input[type=\"submit\"]"))
                .perform(webClick())

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withText("Dandan Meng")).check(matches(isDisplayed()))
    }
}
