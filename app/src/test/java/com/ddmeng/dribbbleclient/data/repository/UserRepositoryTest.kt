package com.ddmeng.dribbbleclient.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.webkit.CookieManager
import com.ddmeng.dribbbleclient.AppExecutors
import com.ddmeng.dribbbleclient.data.local.UserDao
import com.ddmeng.dribbbleclient.data.model.User
import com.ddmeng.dribbbleclient.data.remote.UserService
import com.ddmeng.dribbbleclient.data.valueobject.Resource
import com.ddmeng.dribbbleclient.utils.ApiUtil
import com.ddmeng.dribbbleclient.utils.InstantAppExecutors
import com.ddmeng.dribbbleclient.utils.TestUtil
import com.ddmeng.dribbbleclient.utils.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class UserRepositoryTest {

    private lateinit var appExecutors: AppExecutors
    @Mock
    private lateinit var userDao: UserDao
    @Mock
    private lateinit var userService: UserService
    @Mock
    private lateinit var cookieManager: CookieManager

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        appExecutors = InstantAppExecutors()

        userRepository = UserRepository(appExecutors, userDao, userService, cookieManager)
    }

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadUser() {
        userRepository.getUser(false)
        verify(userDao).query()
    }

    @Test
    fun goToNetwork() {
        val dbData = MutableLiveData<User>()
        Mockito.`when`(userDao.query()).thenReturn(dbData)
        val user = TestUtil.createUser("foo")
        val call = ApiUtil.successCall(user)
        Mockito.`when`(userService.getUser()).thenReturn(call)
        val observer = mock<Observer<Resource<User>>>()

        userRepository.getUser(false).observeForever(observer)

        verify(userService, Mockito.never()).getUser()
        val updatedDbData = MutableLiveData<User>()
        Mockito.`when`(userDao.query()).thenReturn(updatedDbData)
        dbData.value = null
        verify(userService).getUser()
    }

    @Test
    fun doNotGoToNetwork() {
        val dbData = MutableLiveData<User>()
        val user = TestUtil.createUser("foo")
        dbData.value = user
        Mockito.`when`(userDao.query()).thenReturn(dbData)
        val observer = mock<Observer<Resource<User>>>()

        userRepository.getUser(false).observeForever(observer)
        verify(userService, Mockito.never()).getUser()
        verify(observer).onChanged(Resource.success(user))
    }

    @Test
    fun shouldGoToNetworkWhenForce() {
        val dbData = MutableLiveData<User>()
        val user = TestUtil.createUser("foo")
        dbData.value = user
        Mockito.`when`(userDao.query()).thenReturn(dbData)
        val call = ApiUtil.successCall(user)
        Mockito.`when`(userService.getUser()).thenReturn(call)
        val observer = mock<Observer<Resource<User>>>()

        userRepository.getUser(true).observeForever(observer)
        verify(userService).getUser()
        verify(observer).onChanged(Resource.success(user))
    }

    @Test
    fun deleteAccount() {
        val user = TestUtil.createUser("foo")

        userRepository.deleteUser(user)

        verify(userDao).delete(user)
        verify(cookieManager).removeAllCookie()
    }
}
