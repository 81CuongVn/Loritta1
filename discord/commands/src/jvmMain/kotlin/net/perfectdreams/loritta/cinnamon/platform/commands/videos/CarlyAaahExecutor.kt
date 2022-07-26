package net.perfectdreams.loritta.cinnamon.platform.commands.videos

import net.perfectdreams.gabrielaimageserver.client.GabrielaImageServerClient
import net.perfectdreams.loritta.cinnamon.platform.commands.SlashCommandExecutorDeclaration
import net.perfectdreams.loritta.cinnamon.platform.commands.images.base.GabrielaImageServerSingleCommandBase
import net.perfectdreams.loritta.cinnamon.platform.commands.images.base.SingleImageOptions

class CarlyAaahExecutor(
    client: GabrielaImageServerClient
) : GabrielaImageServerSingleCommandBase(
    client,
    { client.videos.carlyAaah(it) },
    "carly_aaah.mp4"
) {
    companion object : SlashCommandExecutorDeclaration() {
        override val options = SingleImageOptions
    }
}