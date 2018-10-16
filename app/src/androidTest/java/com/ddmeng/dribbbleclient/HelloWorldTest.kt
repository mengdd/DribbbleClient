package com.ddmeng.dribbbleclient

import android.arch.lifecycle.MediatorLiveData
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.ddmeng.dribbbleclient.data.model.User
import com.ddmeng.dribbbleclient.data.valueobject.Resource
import com.ddmeng.dribbbleclient.features.home.HomeFragment
import com.ddmeng.dribbbleclient.util.ViewModelUtil
import com.ddmeng.dribbbleclient.util.createFakeActivityInjector
import com.ddmeng.dribbbleclient.util.createFakeFragmentInjector
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import com.ddmeng.dribbbleclient.viewmodel.UserViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@LargeTest
@RunWith(AndroidJUnit4::class)
class HelloWorldTest {
    private val viewModel = mock(UserViewModel::class.java)
    private val userData = MediatorLiveData<Resource<User>>()

    @Rule
    @JvmField
    var activityRule = object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            val app = InstrumentationRegistry.getTargetContext().applicationContext as TestApp
            app.dispatchingAndroidInjector = createFakeActivityInjector<MainActivity> {
                userViewModelFactory = ViewModelUtil.createFor(viewModel)
                preferencesUtils = mock(PreferencesUtils::class.java)
                dispatchingAndroidInjector = createFakeFragmentInjector<HomeFragment> { }
                `when`(viewModel.user).thenReturn(userData)
            }
        }
    }

    @Test
    fun testHelloWorldShown() {
        onView(withText("Hello World")).check(matches(isDisplayed()))
    }
}
