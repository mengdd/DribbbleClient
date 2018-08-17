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
import android.widget.Button
import com.ddmeng.dribbbleclient.databinding.ActivityMainBinding
import com.ddmeng.dribbbleclient.features.auth.OAuthFragment
import com.ddmeng.dribbbleclient.features.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        toolbar = binding.toolbarLayout!!.toolbar
        navigationView = binding.navigationView
        val headerView = navigationView.getHeaderView(0)
        loginButton = headerView.findViewById(R.id.login_button)
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
        showFragment(HomeFragment())
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
