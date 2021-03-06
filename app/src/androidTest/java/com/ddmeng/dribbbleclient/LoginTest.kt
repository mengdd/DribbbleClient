package com.ddmeng.dribbbleclient

import android.arch.lifecycle.MediatorLiveData
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
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.ddmeng.dribbbleclient.data.model.User
import com.ddmeng.dribbbleclient.data.valueobject.Resource
import com.ddmeng.dribbbleclient.features.auth.OAuthFragment
import com.ddmeng.dribbbleclient.features.home.HomeFragment
import com.ddmeng.dribbbleclient.util.CountingAppExecutorsRule
import com.ddmeng.dribbbleclient.util.DataBindingIdlingResourceRule
import com.ddmeng.dribbbleclient.util.ViewModelUtil
import com.ddmeng.dribbbleclient.util.createFakeActivityInjector
import com.ddmeng.dribbbleclient.util.createFakeFragmentsInjector
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import com.ddmeng.dribbbleclient.viewmodel.UserViewModel
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginTest {
    private val viewModel = Mockito.mock(UserViewModel::class.java)
    private val userData = MediatorLiveData<Resource<User>>()
    private val mockPreferencesUtils = Mockito.mock(PreferencesUtils::class.java)

    @Rule
    @JvmField
    var activityRule = object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            val app = InstrumentationRegistry.getTargetContext().applicationContext as TestApp
            app.dispatchingAndroidInjector = createFakeActivityInjector<MainActivity> {
                userViewModelFactory = ViewModelUtil.createFor(viewModel)
                preferencesUtils = mockPreferencesUtils
                dispatchingAndroidInjector = createFakeFragmentsInjector(listOf(OAuthFragment::class, HomeFragment::class)) { }
                Mockito.`when`(viewModel.user).thenReturn(userData)
            }
        }
    }

    @JvmField
    @Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    @JvmField
    @Rule
    val countingAppExecutorsRule = CountingAppExecutorsRule()

    @Test
    fun testOpenLoginFragmentShown() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.login_button)).perform(click())

        onWebView(withId(R.id.webview)).forceJavascriptEnabled()
        onWebView().check(webMatches(getCurrentUrl(), containsString("https://dribbble.com/login?")))
    }

    @Test
    fun testUpdateLoginState() {
        `when`(mockPreferencesUtils.isUserLoggedIn).thenReturn(true)

        val user = User().copy(name = "Dai")
        val response = Resource.success(user)
        userData.postValue(response)

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withText("Dai")).check(matches(isDisplayed()))
    }
}
