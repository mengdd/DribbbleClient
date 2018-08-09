package com.ddmeng.dribbbleclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        toolbar.setNavigationOnClickListener { drawer_layout.openDrawer(Gravity.START) }
    }
}
