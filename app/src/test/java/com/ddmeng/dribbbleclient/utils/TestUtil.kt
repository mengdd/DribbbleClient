package com.ddmeng.dribbbleclient.utils

import com.ddmeng.dribbbleclient.data.model.User

object TestUtil {
    fun createUser(name: String) = User(
            name = name
    )
}