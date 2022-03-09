package com.githubstarredusers.view.mappers

import android.content.res.Resources
import com.githubstarredusers.view.R
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UiErrorsMapper(private val resources: Resources) {

    fun map(error: Throwable?): String =
        when (error) {
            is ConnectException -> resources.getString(R.string.connect_error)
            is UnknownHostException -> resources.getString(R.string.unknown_host_error)
            is SocketTimeoutException -> resources.getString(R.string.socket_time_out_error)
            is HttpException -> {
                when (error.code()) {
                    403 -> resources.getString(R.string.reached_git_hub_limit_api)
                    else -> resources.getString(R.string.unknown_network_error)
                }
            }
            else -> resources.getString(R.string.unknown_network_error)
        }
}
