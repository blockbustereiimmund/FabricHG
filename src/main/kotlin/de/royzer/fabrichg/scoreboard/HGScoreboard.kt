package de.royzer.fabrichg.scoreboard

import de.royzer.fabrichg.data.hgplayer.HGPlayer
import de.royzer.fabrichg.game.GamePhaseManager
import de.royzer.fabrichg.game.PlayerList
import de.royzer.fabrichg.game.phase.PhaseType
import de.royzer.fabrichg.game.phase.phases.EndPhase
import de.royzer.fabrichg.game.phase.phases.LobbyPhase
import net.axay.fabrik.core.sideboard.showSideboard
import net.axay.fabrik.core.sideboard.sideboard
import net.axay.fabrik.core.text.literalText
import net.minecraft.server.network.ServerPlayerEntity

fun ServerPlayerEntity.showScoreboard() {
    val hgPlayer = PlayerList.getPlayer(uuid, name.string)
    showSideboard(
        sideboard(
            literalText("Fabric HG") { color = 0xFF00C8 }
        ) {
            lineChangingPeriodically(1000) {
                if (GamePhaseManager.currentPhaseType == PhaseType.LOBBY)
                    literalText("Start in: ${(LobbyPhase.maxPhaseTime - GamePhaseManager.timer.get()).formattedTime}")
                else if (GamePhaseManager.currentPhaseType == PhaseType.END)
                    literalText("Zeit: ${(GamePhaseManager.currentPhase as EndPhase).endTime.formattedTime}")
                else
                    literalText("Zeit: ${(GamePhaseManager.timer.get()).formattedTime}")
            }
            literalLine("")
            lineChangingPeriodically(1000) {
                literalText("Kills: ${hgPlayer.kills}") { color = 0x0032FF }
            }
            literalLine("Kit(s): nunja") { color = 0x00FFFF }
            literalLine("")
            literalLine("Spieler:") { color = 0x83CDFF }
            lineChangingPeriodically(1000) {
                literalText("${PlayerList.alivePlayers.size}/${PlayerList.maxPlayers}") { color = 0x0032FF }
            }
            literalLine("")
            lineChangingPeriodically(1000) {
                literalText(hgPlayer.status.toString()) { color = hgPlayer.status.statusColor }
            }
        }
    )
}

val Int.formattedTime: String
    get() {
        val m = this / 60
        val s = if (this % 60 >= 10) this % 60 else "0${this % 60}"
        return "$m:$s"
    }