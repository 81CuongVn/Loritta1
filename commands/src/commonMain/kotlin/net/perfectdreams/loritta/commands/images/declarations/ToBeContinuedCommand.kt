package net.perfectdreams.loritta.commands.images.declarations

import net.perfectdreams.loritta.commands.images.ToBeContinuedExecutor
import net.perfectdreams.loritta.common.commands.declarations.CommandDeclaration
import net.perfectdreams.loritta.common.commands.declarations.command
import net.perfectdreams.loritta.common.locale.LocaleKeyData

object ToBeContinuedCommand : CommandDeclaration {
    const val LOCALE_PREFIX = "commands.command.tobecontinued"

    override fun declaration() = command(listOf("tobecontinued")) {
        description = LocaleKeyData("$LOCALE_PREFIX.description")
        executor = ToBeContinuedExecutor
    }
}