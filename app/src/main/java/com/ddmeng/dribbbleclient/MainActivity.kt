package com.ddmeng.dribbbleclient

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.ddmeng.dribbbleclient.databinding.ActivityMainBinding
import com.ddmeng.dribbbleclient.databinding.DrawerHeaderBinding
import com.ddmeng.dribbbleclient.di.InjectorUtils
import com.ddmeng.dribbbleclient.features.auth.OAuthFragment
import com.ddmeng.dribbbleclient.features.home.HomeFragment
import com.ddmeng.dribbbleclient.utils.LogUtils
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import com.ddmeng.dribbbleclient.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.drawer_header.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var headerView: View
    private lateinit var loginButton: Button

    private lateinit var preferencesUtils: PreferencesUtils
    private lateinit var userViewModel: UserViewModel
    private lateinit var drawerHeaderBinding: DrawerHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesUtils = PreferencesUtils(application)
        userViewModel = ViewModelProviders.of(this, InjectorUtils.provideUserViewModelFactory(this))
                .get(UserViewModel::class.java)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        toolbar = binding.toolbarLayout!!.toolbar
        navigationView = binding.navigationView
        headerView = navigationView.getHeaderView(0)
        drawerHeaderBinding = DrawerHeaderBinding.bind(headerView)

        loginButton = headerView.login_button
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
                    LogUtils.d("not supported yet")
                }
            }
            drawerLayout.closeDrawer(Gravity.START)
            true // TODO why can't I use "return true" here?
        }

        userViewModel.getUserInfo().observe(this, Observer { userResource ->
            LogUtils.i("getUserInfo Observer onChanged: $userResource")
            drawerHeaderBinding.user = userResource?.data
            drawerHeaderBinding.userResource = userResource
        })

        showHomeFragment()
    }

    fun showHomeFragment() {
        updateLoginStatus()
        showFragment(HomeFragment())
    }

    private fun updateLoginStatus() {
        LogUtils.i("is User logged in ${preferencesUtils.isUserLoggedIn}")
        if (!preferencesUtils.isUserLoggedIn) {
            loginButton.visibility = View.VISIBLE
        } else {
            loginButton.visibility = View.GONE
        }
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
