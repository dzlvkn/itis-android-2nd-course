package com.example.android2ndcourse.first_test

import kotlinx.serialization.Serializable

@Serializable
data class Song(

    val imageLink: String,
    val songTitle: String,
    val singer: String

)


