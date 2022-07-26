package net.perfectdreams.loritta.cinnamon.platform.commands.`fun`.declarations

import net.perfectdreams.loritta.cinnamon.common.utils.TodoFixThisData
import net.perfectdreams.loritta.cinnamon.i18n.I18nKeysData
import net.perfectdreams.loritta.cinnamon.platform.commands.CommandCategory
import net.perfectdreams.loritta.cinnamon.platform.commands.SlashCommandDeclarationWrapper
import net.perfectdreams.loritta.cinnamon.platform.commands.`fun`.BemBoladaExecutor
import net.perfectdreams.loritta.cinnamon.platform.commands.`fun`.FaustaoExecutor
import net.perfectdreams.loritta.cinnamon.platform.commands.`fun`.TioDoPaveExecutor

object SummonCommand : SlashCommandDeclarationWrapper {
    val I18N_PREFIX = I18nKeysData.Commands.Command.Summon

    override fun declaration() = slashCommand(listOf("summon"), CommandCategory.FUN, TodoFixThisData) {
        subcommand(listOf("tiodopavê"), I18N_PREFIX.Tiodopave.Description) {
            executor = TioDoPaveExecutor
        }

        subcommand(listOf("faustão"), I18N_PREFIX.Faustao.Description) {
            executor = FaustaoExecutor
        }

        subcommand(listOf("kenji"), I18N_PREFIX.Kenji.Description) {
            executor = BemBoladaExecutor
        }
    }
}