package io.github.kmeret.frame.data.github.model

import com.google.gson.annotations.SerializedName
import io.github.kmeret.frame.domain.entity.User

data class GithubUser(
        val id: Long,
        val login: String,
        @SerializedName("avatar_url")
        val avatarUrl: String
) {
    fun map() = User(id, login, avatarUrl)
}