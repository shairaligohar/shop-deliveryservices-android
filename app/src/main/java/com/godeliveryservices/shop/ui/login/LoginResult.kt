package com.godeliveryservices.shop.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: Boolean = false,
    val code: Int = 0
)
