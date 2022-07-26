package net.perfectdreams.loritta.cinnamon.platform.commands.`fun`

import net.perfectdreams.loritta.cinnamon.common.utils.TodoFixThisData
import net.perfectdreams.loritta.cinnamon.platform.commands.ApplicationCommandContext
import net.perfectdreams.loritta.cinnamon.platform.commands.SlashCommandExecutor
import net.perfectdreams.loritta.cinnamon.platform.commands.SlashCommandExecutorDeclaration
import net.perfectdreams.loritta.cinnamon.platform.commands.options.ApplicationCommandOptions
import net.perfectdreams.loritta.cinnamon.platform.commands.options.SlashCommandArguments
import net.perfectdreams.loritta.cinnamon.pudding.tables.bomdiaecia.BomDiaECiaMatches
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll

class BomDiaECiaExecutor : SlashCommandExecutor() {
    companion object : SlashCommandExecutorDeclaration() {
        object Options : ApplicationCommandOptions() {
            val text = string("text", TodoFixThisData)
                .register()
        }

        override val options = Options
    }

    override suspend fun execute(context: ApplicationCommandContext, args: SlashCommandArguments) {
        context.deferChannelMessageEphemerally()

        val text = args[Options.text]

        val currentActiveBomDiaECia = context.loritta.services.transaction {
            BomDiaECiaMatches.selectAll()
                .orderBy(BomDiaECiaMatches.id, SortOrder.DESC)
                .limit(1)
                .first()
        }

        // Wrong
        if (text != currentActiveBomDiaECia[BomDiaECiaMatches.text]) {
            context.sendEphemeralMessage {
                content = "Texto errado!"
            }
            return
        }

        // Correct
        context.sendEphemeralMessage {
            content = "oloco você ganhou"
        }

        // TODO: Get current active bom dia & cia
        // TODO: Trigger win/lose scenario, if needed
        // TODO:
        context.sendEphemeralMessage {
            content = "oloco"
        }
    }
}