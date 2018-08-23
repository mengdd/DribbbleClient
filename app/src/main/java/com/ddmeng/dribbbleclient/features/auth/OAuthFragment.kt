package com.ddmeng.dribbbleclient.features.auth

import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ddmeng.dribbbleclient.BuildConfig
import com.ddmeng.dribbbleclient.MainActivity
import com.ddmeng.dribbbleclient.R
import com.ddmeng.dribbbleclient.data.model.OAuthToken
import com.ddmeng.dribbbleclient.data.remote.ApiConstants
import com.ddmeng.dribbbleclient.data.remote.OAuthService
import com.ddmeng.dribbbleclient.data.remote.ServiceGenerator
import com.ddmeng.dribbbleclient.databinding.FragmentAuthBinding
import com.ddmeng.dribbbleclient.utils.LogUtils
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OAuthFragment : Fragment() {
    private lateinit var webview: WebView
    private lateinit var preferencesUtils: PreferencesUtils
    private lateinit var serviceGenerator: ServiceGenerator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentAuthBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth,
                container, false)
        webview = binding.webview
        webview.loadUrl(ApiConstants.LOGIN_OAUTH_URL)
        webview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                LogUtils.i("url $url")
                val uri = Uri.parse(url)
                val code = uri.getQueryParameter("code")
                code?.let { getToken(code) }
                return false
            }


        }
        preferencesUtils = PreferencesUtils(activity?.application!!) // TODO: Ugly
        serviceGenerator = ServiceGenerator(preferencesUtils)
        return binding.root
    }

    private fun getToken(code: String) {
        LogUtils.i("getToken with code: $code")
        serviceGenerator.changeBaseUrl(ServiceGenerator.OAUTH_BASE_URL) // TODO: lame, refactor with dagger
        val oauthService: OAuthService = serviceGenerator.createService(OAuthService::class.java)
        oauthService.getToken(BuildConfig.DRIBBBLE_CLIENT_ID, BuildConfig.DRIBBBLE_CLIENT_SECRET, code, BuildConfig.DRIBBBLE_CALLBACK_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token ->
                    saveToken(token)
                    exit()
                }, {
                    LogUtils.e("error in getToken", it)
                    exit()
                })


    }

    private fun saveToken(oauthToken: OAuthToken) {
        preferencesUtils.saveUserLoggedIn(true)
        preferencesUtils.saveUserToken(oauthToken.accessToken)
    }

    private fun exit() {
        (activity as MainActivity).showHomeFragment()
    }
}