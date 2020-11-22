package com.vacegaming.james.musicbot.model

data class BotConfigModel(
    val botLogChannelID: Long,
    val clubMemberID: Long,
    val memberID: Long,
    val musicBotChannelID: Long,
    val musicBotID: Long,
    val vaceGamingGuildId: Long,
    var botToken: String
)