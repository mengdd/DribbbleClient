package com.ddmeng.dribbbleclient

import android.arch.lifecycle.MediatorLiveData
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.ddmeng.dribbbleclient.data.model.User
import com.ddmeng.dribbbleclient.data.valueobject.Resource
import com.ddmeng.dribbbleclient.features.auth.OAuthFragment
import com.ddmeng.dribbbleclient.features.home.HomeFragment
import com.ddmeng.dribbbleclient.util.ViewModelUtil
import com.ddmeng.dribbbleclient.util.createFakeActivityInjector
import com.ddmeng.dribbbleclient.util.createFakeFragmentsInjector
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import com.ddmeng.dribbbleclient.viewmodel.UserViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@LargeTest
@RunWith(AndroidJUnit4::class)
class LogoutTest {
    private val viewModel = mock(UserViewModel::class.java)
    private val userData = MediatorLiveData<Resource<User>>()
    private val mockPreferencesUtils = mock(PreferencesUtils::class.java)
    private val user = User().copy(name = "Dai")

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
                `when`(viewModel.user).thenReturn(userData)
            }
        }
    }

    @Before
    fun setUp() {
        `when`(mockPreferencesUtils.isUserLoggedIn).thenReturn(true)
        val response = Resource.success(user)
        userData.postValue(response)
    }

    @Test
    fun testLogout() {
        `when`(viewModel.deleteUser(user)).then {
            userData.postValue(null)
        }

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.logout_button)).perform(ViewActions.click())
        onView(withText("OK")).perform(ViewActions.click())

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withText("LOGIN")).check(matches(isDisplayed()))
    }
}
