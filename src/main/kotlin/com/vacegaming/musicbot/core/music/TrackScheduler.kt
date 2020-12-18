package com.vacegaming.musicbot.core.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import com.vacegaming.musicbot.core.ChannelManager
import com.vacegaming.musicbot.core.VoiceChannelManager
import java.awt.Color

object TrackScheduler : AudioEventAdapter() {

    fun queue(track: AudioTrack) {
        val wasStarted = MusicManager.audioPlayer.startTrack(track, true)
        val title = MusicManager.audioPlayer.playingTrack.info.title
        val volume = MusicManager.audioPlayer.volume

        if (wasStarted.not()) {
            MusicQueue.queue.offer(track)
        }

        ChannelManager.editStaticMessage(title, null, Color.green, volume)
    }

    fun nextTrack() {
        MusicQueue.queue.poll()?.let {
            MusicManager.audioPlayer.startTrack(it, false)
        }
    }

    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
        if (MusicQueue.queue.size <= 0 && endReason.name != "REPLACED") {
            return VoiceChannelManager.leave()
        }

        if (endReason.mayStartNext) {
            nextTrack()
        }
    }

    override fun onPlayerPause(player: AudioPlayer) {
        val title = player.playingTrack.info.title

        ChannelManager.editStaticMessage(title, null, Color.ORANGE, player.volume)
    }

    override fun onPlayerResume(player: AudioPlayer?) {
        val title = player?.playingTrack?.info?.title ?: ""

        ChannelManager.editStaticMessage(title, null, Color.GREEN, player?.volume)
    }

    override fun onTrackStart(player: AudioPlayer, track: AudioTrack) {
        val title = player.playingTrack.info.title

        ChannelManager.editStaticMessage(title, null, Color.GREEN, player.volume)
    }

    override fun onTrackException(player: AudioPlayer?, track: AudioTrack?, exception: FriendlyException?) {
        VoiceChannelManager.leave()
    }

    override fun onTrackStuck(player: AudioPlayer?, track: AudioTrack?, thresholdMs: Long) {
        VoiceChannelManager.leave()
    }
}