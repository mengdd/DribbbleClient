package com.ddmeng.dribbbleclient.features.auth

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ddmeng.dribbbleclient.AppExecutors
import com.ddmeng.dribbbleclient.BuildConfig
import com.ddmeng.dribbbleclient.MainActivity
import com.ddmeng.dribbbleclient.R
import com.ddmeng.dribbbleclient.data.model.OAuthToken
import com.ddmeng.dribbbleclient.data.remote.ApiConstants
import com.ddmeng.dribbbleclient.data.remote.ApiResponse
import com.ddmeng.dribbbleclient.data.remote.OAuthService
import com.ddmeng.dribbbleclient.data.repository.NetworkResource
import com.ddmeng.dribbbleclient.data.valueobject.Status
import com.ddmeng.dribbbleclient.databinding.FragmentAuthBinding
import com.ddmeng.dribbbleclient.di.Injectable
import com.ddmeng.dribbbleclient.utils.LogUtils
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import javax.inject.Inject

class OAuthFragment : Fragment(), Injectable {
    private lateinit var webview: WebView
    @Inject
    lateinit var preferencesUtils: PreferencesUtils
    @Inject
    lateinit var oAuthService: OAuthService
    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAuthBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth,
                container, false)
        webview = binding.webview
        webview.loadUrl(ApiConstants.LOGIN_OAUTH_URL)
        webview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                LogUtils.i("url $url")
                val uri = Uri.parse(url)
                val code = uri.getQueryParameter("code")
                code.takeUnless { it.isNullOrEmpty() }?.let { getToken(it) }
                return false
            }
        }
        return binding.root
    }

    @SuppressLint("CheckResult")
    private fun getToken(code: String) {
        LogUtils.i("getToken with code: $code")
        object : NetworkResource<OAuthToken>() {
            override fun createCall(): LiveData<ApiResponse<OAuthToken>> {
                return oAuthService.getToken(BuildConfig.DRIBBBLE_CLIENT_ID,
                        BuildConfig.DRIBBBLE_CLIENT_SECRET,
                        code, BuildConfig.DRIBBBLE_CALLBACK_URL)
            }
        }.asLiveData().observe(this, Observer {
                when(it?.status) {
                    Status.SUCCESS -> {
                        saveToken(it.data)
                        exit()
                    }
                    Status.ERROR -> {
                        LogUtils.e("error in getToken")
                        exit()
                    }
                    Status.LOADING -> {}
                }
        })
    }

    private fun saveToken(oauthToken: OAuthToken?) {
        preferencesUtils.saveUserLoggedIn(true)
        preferencesUtils.saveUserToken(oauthToken?.accessToken)
    }

    private fun exit() {
        (activity as MainActivity).showHomeFragment()
    }
}
