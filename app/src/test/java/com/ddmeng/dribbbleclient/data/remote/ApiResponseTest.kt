package com.ddmeng.dribbbleclient.data.remote

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {
    @Test
    fun exception() {
        val exception = Exception("foo")
        val (errorMessage) = ApiResponse.create<String>(exception)
        MatcherAssert.assertThat<String>(errorMessage, CoreMatchers.`is`("foo"))
    }

    @Test
    fun success() {
        val apiResponse: ApiSuccessResponse<String> = ApiResponse
                .create<String>(Response.success("foo")) as ApiSuccessResponse<String>
        MatcherAssert.assertThat<String>(apiResponse.body, CoreMatchers.`is`("foo"))
        MatcherAssert.assertThat<Int>(apiResponse.nextPage, CoreMatchers.`is`(CoreMatchers.nullValue()))
    }

    @Test
    fun link() {
        val link =
                "<https://api.github.com/search/repositories?q=foo&page=2>; rel=\"next\"," +
                        " <https://api.github.com/search/repositories?q=foo&page=34>; rel=\"last\""
        val headers = okhttp3.Headers.of("link", link)
        val response = ApiResponse.create<String>(Response.success("foo", headers))
        MatcherAssert.assertThat<Int>((response as ApiSuccessResponse).nextPage, CoreMatchers.`is`(2))
    }

    @Test
    fun badPageNumber() {
        val link = "<https://api.github.com/search/repositories?q=foo&page=dsa>; rel=\"next\""
        val headers = okhttp3.Headers.of("link", link)
        val response = ApiResponse.create<String>(Response.success("foo", headers))
        MatcherAssert.assertThat<Int>((response as ApiSuccessResponse).nextPage, CoreMatchers.nullValue())
    }

    @Test
    fun badLinkHeader() {
        val link = "<https://api.github.com/search/repositories?q=foo&page=dsa>; relx=\"next\""
        val headers = okhttp3.Headers.of("link", link)
        val response = ApiResponse.create<String>(Response.success("foo", headers))
        MatcherAssert.assertThat<Int>((response as ApiSuccessResponse).nextPage, CoreMatchers.nullValue())
    }

    @Test
    fun error() {
        val errorResponse = Response.error<String>(
                400,
                ResponseBody.create(MediaType.parse("application/txt"), "blah")
        )
        val (errorMessage) = ApiResponse.create<String>(errorResponse) as ApiErrorResponse<String>
        MatcherAssert.assertThat<String>(errorMessage, CoreMatchers.`is`("blah"))
    }
}