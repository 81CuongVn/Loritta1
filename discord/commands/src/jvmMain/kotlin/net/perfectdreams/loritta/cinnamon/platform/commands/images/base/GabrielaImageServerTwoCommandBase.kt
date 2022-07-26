package net.perfectdreams.loritta.cinnamon.platform.commands.images.base

import net.perfectdreams.gabrielaimageserver.client.GabrielaImageServerClient
import net.perfectdreams.gabrielaimageserver.data.TwoImagesRequest
import net.perfectdreams.gabrielaimageserver.data.URLImageData
import net.perfectdreams.loritta.cinnamon.platform.commands.ApplicationCommandContext
import net.perfectdreams.loritta.cinnamon.platform.commands.SlashCommandExecutor
import net.perfectdreams.loritta.cinnamon.platform.commands.images.gabrielaimageserver.handleExceptions
import net.perfectdreams.loritta.cinnamon.platform.commands.options.SlashCommandArguments

open class GabrielaImageServerTwoCommandBase(
    val client: GabrielaImageServerClient,
    val block: suspend GabrielaImageServerClient.(TwoImagesRequest) -> (ByteArray),
    val fileName: String
) : SlashCommandExecutor() {
    override suspend fun execute(context: ApplicationCommandContext, args: SlashCommandArguments) {
        context.deferChannelMessage() // Defer message because image manipulation is kinda heavy

        val imageReference1 = args[TwoImagesOptions.imageReference1]
        val imageReference2 = args[TwoImagesOptions.imageReference2]

        val result = client.handleExceptions(context) {
            block.invoke(
                client,
                TwoImagesRequest(
                    URLImageData(
                        imageReference1.url
                    ),
                    URLImageData(
                        imageReference2.url
                    )
                )
            )
        }

        context.sendMessage {
            addFile(fileName, result.inputStream())
        }
    }
}