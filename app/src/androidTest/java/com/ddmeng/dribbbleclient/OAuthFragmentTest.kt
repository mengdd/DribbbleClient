package com.ddmeng.dribbbleclient

import android.arch.lifecycle.MediatorLiveData
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.matcher.ViewMatchers.withId
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
import com.ddmeng.dribbbleclient.data.model.OAuthToken
import com.ddmeng.dribbbleclient.data.remote.ApiResponse
import com.ddmeng.dribbbleclient.data.remote.OAuthService
import com.ddmeng.dribbbleclient.features.auth.OAuthFragment
import com.ddmeng.dribbbleclient.testing.SingleFragmentActivity
import com.ddmeng.dribbbleclient.util.TaskExecutorWithIdlingResourceRule
import com.ddmeng.dribbbleclient.util.createFakeActivityInjector
import com.ddmeng.dribbbleclient.util.createFakeFragmentInjector
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import org.hamcrest.CoreMatchers.containsString
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import retrofit2.Response

@LargeTest
@RunWith(AndroidJUnit4::class)
class OAuthFragmentTest {
    private val mockOAuthService = mock(OAuthService::class.java)
    private val mockPreferenceUtils = mock(PreferencesUtils::class.java)

    @Rule
    @JvmField
    var activityTestRule = object : ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity::class.java, false, true) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            val app = InstrumentationRegistry.getTargetContext().applicationContext as TestApp
            app.dispatchingAndroidInjector = createFakeActivityInjector<SingleFragmentActivity> {
                dispatchingAndroidInjector = createFakeFragmentInjector<TestOAuthFragment> {
                    preferencesUtils = mockPreferenceUtils
                    oAuthService = mockOAuthService
                }
            }
        }
    }

    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()

    @Test
    fun testLogin() {
        val oAuthFragment = TestOAuthFragment()
        oAuthFragment.cleanCookies()
        activityTestRule.activity.setFragment(oAuthFragment)

        val response = MediatorLiveData<ApiResponse<OAuthToken>>()
        val token = OAuthToken()
        token.accessToken = "token"
        val apiResponse = ApiResponse.create(Response.success(token))
        response.postValue(apiResponse)

        `when`(mockOAuthService.getToken(anyString(), anyString(), anyString(), anyString())).thenReturn(response)

        onWebView(withId(R.id.webview)).forceJavascriptEnabled()
        onWebView().check(webMatches(getCurrentUrl(), containsString("https://dribbble.com/login?")))

        onWebView()
                .withElement(findElement(Locator.ID, "login"))
                .perform(DriverAtoms.webKeys("dribbbletest123"))
                .withElement(findElement(Locator.ID, "password"))
                .perform(DriverAtoms.webKeys("test123456"))
                .withElement(findElement(Locator.CSS_SELECTOR, "input[type=\"submit\"]"))
                .perform(webClick())

        onWebView(withId(R.id.webview)).forceJavascriptEnabled()
        onWebView().check(webMatches(getCurrentUrl(), containsString("http://mengdd.github.io/?code=")))

        assertTrue(oAuthFragment.exit)
        verify(mockPreferenceUtils).saveUserLoggedIn(true)
        verify(mockPreferenceUtils).saveUserToken("token")
    }

    class TestOAuthFragment : OAuthFragment() {
        var exit = false
        override fun exit() {
            exit = true
        }
    }
}
