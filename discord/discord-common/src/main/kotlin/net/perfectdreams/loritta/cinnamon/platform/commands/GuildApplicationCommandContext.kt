package net.perfectdreams.loritta.cinnamon.platform.commands

import dev.kord.common.entity.Snowflake
import net.perfectdreams.discordinteraktions.common.commands.GuildApplicationCommandContext
import net.perfectdreams.discordinteraktions.common.entities.Member
import net.perfectdreams.discordinteraktions.common.entities.User
import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.loritta.cinnamon.platform.LorittaCinnamon

class GuildApplicationCommandContext(
    loritta: LorittaCinnamon,
    i18nContext: I18nContext,
    user: User,
    override val interaKTionsContext: GuildApplicationCommandContext,
    val guildId: Snowflake,
    val member: Member
) : ApplicationCommandContext(loritta, i18nContext, user, interaKTionsContext)