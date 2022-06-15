package com.example.kotlinlib

sealed class AuthorizationState

object Unauthorized : AuthorizationState()

class Authorized(val userName: String) : AuthorizationState()

class AuthorizationPresenter {

    private var state: AuthorizationState = Unauthorized

    private val isAuthorized: Boolean
        get() = when (state) {
            is Authorized -> true
            is Unauthorized -> false
        }

    private val userName: String
        get() {
            return when (val state = this.state) {
                is Authorized -> state.userName
                is Unauthorized -> "Unknown"
            }
        }

    fun loginUser(userName: String) {
        state = Authorized(userName)
    }

    fun logoutUser() {
        state = Unauthorized
    }

    override fun toString() = "User '$userName' is logged in: $isAuthorized"
}

fun main() {
    val authorizationPresenter = AuthorizationPresenter()
    authorizationPresenter.loginUser("admin")
    println(authorizationPresenter)
    authorizationPresenter.logoutUser()
    println(authorizationPresenter)
}