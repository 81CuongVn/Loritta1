package net.perfectdreams.loritta.cinnamon.platform.commands.discord

import dev.kord.common.Color
import dev.kord.common.entity.optional.value
import dev.kord.rest.request.KtorRequestException
import dev.kord.rest.service.RestClient
import net.perfectdreams.discordinteraktions.common.builder.message.actionRow
import net.perfectdreams.discordinteraktions.common.builder.message.embed
import net.perfectdreams.discordinteraktions.common.utils.footer
import net.perfectdreams.discordinteraktions.common.utils.thumbnailUrl
import net.perfectdreams.loritta.cinnamon.common.emotes.Emotes
import net.perfectdreams.loritta.cinnamon.common.utils.text.TextUtils.shortenAndStripCodeBackticks
import net.perfectdreams.loritta.cinnamon.platform.commands.ApplicationCommandContext
import net.perfectdreams.loritta.cinnamon.platform.commands.SlashCommandExecutor
import net.perfectdreams.loritta.cinnamon.platform.commands.SlashCommandExecutorDeclaration
import net.perfectdreams.loritta.cinnamon.platform.commands.discord.declarations.InviteCommand
import net.perfectdreams.loritta.cinnamon.platform.commands.discord.declarations.ServerCommand
import net.perfectdreams.loritta.cinnamon.platform.commands.options.ApplicationCommandOptions
import net.perfectdreams.loritta.cinnamon.platform.commands.options.SlashCommandArguments
import net.perfectdreams.loritta.cinnamon.platform.commands.styled
import net.perfectdreams.loritta.cinnamon.platform.utils.DiscordInviteUtils
import net.perfectdreams.loritta.cinnamon.platform.utils.RawToFormated.toLocalized

class InviteInfoExecutor(val rest: RestClient) : SlashCommandExecutor() {
    companion object : SlashCommandExecutorDeclaration() {
        object Options : ApplicationCommandOptions() {
            val invite = string("invite", InviteCommand.I18N_PREFIX.Info.Options.Invite)
                .register()
        }

        override val options = Options
    }

    override suspend fun execute(context: ApplicationCommandContext, args: SlashCommandArguments) {
        val text = args[Options.invite]
        val inviteCode = if (text.contains("/")) {
            DiscordInviteUtils.getInviteCodeFromUrl(text)
        } else {
            text
        }

        // Not a invite code!
        // TODO: Change error message
        if (inviteCode == null || !DiscordInviteUtils.inviteCodeRegex.matches(inviteCode))
            context.failEphemerally {
                styled(
                    context.i18nContext.get(
                        InviteCommand.I18N_PREFIX.Info.DoesntExists(
                            args[Options.invite].shortenAndStripCodeBackticks(100)
                        )
                    ),
                    prefix = Emotes.Error
                )
            }

        // Defer now because we will query Discord's API
        context.deferChannelMessage()

        val invite = try {
            rest.invite.getInvite(inviteCode, true)
        } catch (e: KtorRequestException) {
            context.fail {
                styled(
                    context.i18nContext.get(
                        InviteCommand.I18N_PREFIX.Info.DoesntExists(
                            args[Options.invite].shortenAndStripCodeBackticks(100)
                        )
                    ),
                    prefix = Emotes.Error
                )
            }
        }

        val discordGuild = try {
            rest.guild.getGuild(invite.guild.value!!.id)
        } catch (e: KtorRequestException) {
            null
        }

        val extension = if (invite.guild.value?.icon?.startsWith("a_") == true) "gif" else "png"
        val iconUrl = "https://cdn.discordapp.com/icons/${invite.guild.value!!.id.value}/${invite.guild.value!!.icon}.$extension?size=2048"

        context.sendMessage {
            embed {
                title = "${Emotes.Discord} ${invite.guild.value?.name}"
                color = Color(114, 137, 218) // TODO: Move this to an object

                if (invite.guild.value?.icon != null)
                    thumbnailUrl = iconUrl

                field("${Emotes.Computer} ID", true) {
                    "`${invite.guild.value?.id?.value.toString()}`"
                }

                field {
                    name = "${Emotes.BustsInSilhouette} " + context.i18nContext.get(
                        InviteCommand.I18N_PREFIX.Info.ServerMembers
                    )
                    value = "${Emotes.PersonTippingHand} " + context.i18nContext.get(
                        InviteCommand.I18N_PREFIX.Info.OnlineMembersPresence(
                            invite.approximatePresenceCount.value!!.toString(),
                        )
                    ) + "\n${Emotes.Sleeping} " + context.i18nContext.get(
                        InviteCommand.I18N_PREFIX.Info.OfflineMembersPresence(
                            (invite.approximateMemberCount.value!! - invite.approximatePresenceCount.value!!).toString()
                        )
                    )

                    inline = true
                }

                val inviteChannel = invite.channel
                if (inviteChannel != null) {
                    if (inviteChannel.name.value != null) field {
                        name = "${Emotes.SpeakingHead} " + context.i18nContext.get(
                            InviteCommand.I18N_PREFIX.Info.InvitationChannel
                        )
                        value = "#${inviteChannel.name.value} (`${inviteChannel.id.value}`)"

                        inline = true
                    }
                }

                if (invite.inviter.value != null) field {
                    name = "${Emotes.Wave}" + context.i18nContext.get(
                        InviteCommand.I18N_PREFIX.Info.WhoInvited
                    )
                    value = "${invite.inviter.value!!.username}#${invite.inviter.value!!.discriminator} " +
                            "(`${invite.inviter.value!!.id.value}`)"

                    inline = true
                }

                val featuresToLocalized = invite.guild.value?.features?.toLocalized()
                field {
                    name = "${Emotes.Sparkles} " + context.i18nContext.get(
                        InviteCommand.I18N_PREFIX.Info.GuildFeatures
                    )
                    value = featuresToLocalized?.joinToString(
                        ", ",
                        transform = { "`${context.i18nContext.get(it)}`" })
                        ?: context.i18nContext.get(InviteCommand.I18N_PREFIX.Info.GuildFeatures)
                }

                footer(
                    if (discordGuild != null) "${Emotes.Blush} " + context.i18nContext.get(InviteCommand.I18N_PREFIX.Info.InThisServer)
                    else "${Emotes.Sob} " + context.i18nContext.get(InviteCommand.I18N_PREFIX.Info.NotOnTheServer)
                )
            }

            if (invite.guild.value?.icon != null)
                actionRow {
                    linkButton(iconUrl) {
                        label = context.i18nContext.get(ServerCommand.I18N_PREFIX.Icon.OpenIconInBrowser)
                    }
                }
        }
    }
}
