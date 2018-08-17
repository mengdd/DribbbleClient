package com.ddmeng.dribbbleclient

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.ddmeng.dribbbleclient.data.model.User
import com.ddmeng.dribbbleclient.data.remote.ServiceGenerator
import com.ddmeng.dribbbleclient.data.remote.UserService
import com.ddmeng.dribbbleclient.databinding.ActivityMainBinding
import com.ddmeng.dribbbleclient.features.auth.OAuthFragment
import com.ddmeng.dribbbleclient.features.home.HomeFragment
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.drawer_header.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var headerView: View
    private lateinit var loginButton: Button
    private lateinit var userNameText: TextView

    private lateinit var preferencesUtils: PreferencesUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesUtils = PreferencesUtils(application)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        toolbar = binding.toolbarLayout!!.toolbar
        navigationView = binding.navigationView
        headerView = navigationView.getHeaderView(0)
        loginButton = headerView.login_button
        userNameText = headerView.user_name
        loginButton.setOnClickListener {
            startLogin()
            drawerLayout.closeDrawer(Gravity.START)
        }

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp)
        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(Gravity.START) }

        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showHomeFragment()
                }
                else -> {
                    Log.d("Main", "not supported yet")
                }
            }
            drawerLayout.closeDrawer(Gravity.START)
            true // TODO why can't I use "return true" here?
        }

        showHomeFragment()
    }

    fun showHomeFragment() {
        updateLoginStatus()
        showFragment(HomeFragment())
    }

    private fun updateLoginStatus() {
        if (preferencesUtils.isUserLoggedIn) {
            getUserInfo()

        } else {
            loginButton.visibility = View.VISIBLE
        }
    }

    private fun getUserInfo() {
        val serviceGenerator = ServiceGenerator(preferencesUtils)
        serviceGenerator.changeBaseUrl(ServiceGenerator.API_BASE_URL)
        val userService = serviceGenerator.createService(UserService::class.java)
        userService.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user: User ->
                    loginButton.visibility = View.GONE
                    populateUser(user)
                }, {
                    loginButton.visibility = View.VISIBLE
                    Log.e("User", "get user info failed", it)
                })

    }

    private fun populateUser(user: User) {
        userNameText.text = user.name
    }

    private fun startLogin() {
        val oAuthFragment = OAuthFragment()
        showFragment(oAuthFragment)
    }


    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_container, fragment)
                .commitAllowingStateLoss()
    }
}
