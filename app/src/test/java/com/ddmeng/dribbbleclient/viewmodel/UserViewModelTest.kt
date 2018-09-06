package com.ddmeng.dribbbleclient.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.ddmeng.dribbbleclient.data.model.User
import com.ddmeng.dribbbleclient.data.repository.UserRepository
import com.ddmeng.dribbbleclient.data.valueobject.Resource
import com.ddmeng.dribbbleclient.utils.ImmediateSchedulerRule
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import com.ddmeng.dribbbleclient.utils.TestUtil
import com.ddmeng.dribbbleclient.utils.mock
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserViewModelTest {
    @Mock
    private lateinit var preferencesUtils: PreferencesUtils
    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var userViewModel: UserViewModel



    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        userViewModel = UserViewModel(userRepository, preferencesUtils)
    }

    companion object {
        @ClassRule
        @JvmField
        val schedulers = ImmediateSchedulerRule()
    }

    @Test
    fun testNull() {
        MatcherAssert.assertThat(userViewModel.user, CoreMatchers.notNullValue())
        Mockito.verify(userRepository, Mockito.never()).getUser(Mockito.anyBoolean())
        userViewModel.fetch(false)
        Mockito.verify(userRepository, Mockito.never()).getUser(Mockito.anyBoolean())
    }

    @Test
    fun testCallRepo() {
        userViewModel.user.observeForever(mock())
        userViewModel.fetch(true)
        Mockito.verify(userRepository).getUser(true)
    }

    @Test
    fun sendResultToUI() {
        val foo = MutableLiveData<Resource<User>>()
        Mockito.`when`(userRepository.getUser(anyBoolean())).thenReturn(foo)
        val observer = mock<Observer<Resource<User>>>()
        userViewModel.user.observeForever(observer)

        userViewModel.fetch(true)

        Mockito.verify(observer, Mockito.never()).onChanged(Mockito.any())
        val fooUser = TestUtil.createUser("foo")
        val fooValue = Resource.success(fooUser)

        foo.value = fooValue
        Mockito.verify(observer).onChanged(fooValue)
    }


    @Test
    fun testDeleteUser() {
        val observer = mock<Observer<Resource<User>>>()
        val user = TestUtil.createUser("foo")
        userViewModel.user.observeForever(observer)

        userViewModel.deleteUser(user)

        Mockito.verify(userRepository).deleteUser(user)
        Mockito.verify(preferencesUtils).saveUserLoggedIn(false)
        Mockito.verify(preferencesUtils).deleteToken()
    }

}
