package com.amonteiro.a06_ynov_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform