package io.github.kmeret.frame.domain.model

import io.github.kmeret.frame.infrastructure.presentation.list.ListItem

data class User(
    override val id: Long,
    val login: String,
    val avatarUrl: String
) : ListItem