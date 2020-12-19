package com.vacegaming.musicbot.reaction.control

import com.vacegaming.musicbot.reaction.Reaction
import com.vacegaming.musicbot.reaction.ReactionHandler
import com.vacegaming.musicbot.reaction.ReactionMessageCase
import com.vacegaming.musicbot.service.LogService
import com.vacegaming.musicbot.service.MusicService
import com.vacegaming.musicbot.util.data.Translation
import com.vacegaming.musicbot.util.koin.genericInject
import net.dv8tion.jda.api.entities.Member
import java.awt.Color

@Reaction(1, "U+25b6", ReactionMessageCase.STATIC)
class PlayReaction: ReactionHandler {
    private val musicService by genericInject<MusicService>()
    private val logService by genericInject<LogService>()

    override fun execute(member: Member) {
        musicService.setResume()
        logService.sendLog(
            title = Translation.LOG_RESUMED_TITLE,
            description = Translation.LOG_RESUMED_DESCRIPTION,
            member = member,
            color = Color.CYAN
        )
    }
}
