package dev.jonaz.vacegaming.musicbot.model.config

data class ConfigDiscordModel(
    val guild: Long,
    val logChannel: Long,
    val musicChannel: Long,
    val accessRole: Long
)
