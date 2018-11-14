package com.ddmeng.dribbbleclient

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.ddmeng.dribbbleclient.data.model.User
import com.ddmeng.dribbbleclient.databinding.ActivityMainBinding
import com.ddmeng.dribbbleclient.databinding.DrawerHeaderBinding
import com.ddmeng.dribbbleclient.features.auth.OAuthFragment
import com.ddmeng.dribbbleclient.features.home.HomeFragment
import com.ddmeng.dribbbleclient.utils.LogUtils
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import com.ddmeng.dribbbleclient.viewmodel.UserViewModel
import com.ddmeng.dribbbleclient.viewmodel.UserViewModelFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.drawer_header.view.login_button
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var headerView: View
    private lateinit var loginButton: Button

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var preferencesUtils: PreferencesUtils
    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory
    private lateinit var userViewModel: UserViewModel
    private lateinit var drawerHeaderBinding: DrawerHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProviders.of(this, userViewModelFactory)
                .get(UserViewModel::class.java)

        val binding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding

        drawerLayout = binding.drawerLayout
        toolbar = binding.toolbarLayout!!.toolbar
        navigationView = binding.navigationView
        headerView = navigationView.getHeaderView(0)
        drawerHeaderBinding = DrawerHeaderBinding.bind(headerView)
        drawerHeaderBinding.callback = this@MainActivity

        loginButton = headerView.login_button

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
            true
        }

        userViewModel.user.observe(this, Observer { userResource ->
            LogUtils.i("getUserInfo Observer onChanged: $userResource")
            drawerHeaderBinding.user = userResource?.data
            drawerHeaderBinding.userResource = userResource
        })

        userViewModel.fetch(false)

        showHomeFragment()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    fun onLogInClick() {
        val oAuthFragment = OAuthFragment()
        showFragment(oAuthFragment)
        drawerLayout.closeDrawer(Gravity.START)
    }

    fun onLogOutClick(user: User) {
        AlertDialog.Builder(this@MainActivity)
                .setTitle(getString(R.string.log_out_dialog_title))
                .setMessage(getString(R.string.log_out_dialog_content))
                .setCancelable(false)
                .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                    userViewModel.deleteUser(user)
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                }
                .show()
    }

    fun showHomeFragment() {
        updateLoginStatus()
        showFragment(HomeFragment())
    }

    private fun updateLoginStatus() {
        LogUtils.i("is User logged in: ${preferencesUtils.isUserLoggedIn}")
        if (preferencesUtils.isUserLoggedIn) {
            loginButton.visibility = View.GONE
            userViewModel.fetch(true)
        } else {
            loginButton.visibility = View.VISIBLE
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_container, fragment)
                .commitAllowingStateLoss()
    }
}
