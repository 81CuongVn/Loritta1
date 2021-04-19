package net.perfectdreams.loritta.commands.images

import io.ktor.client.*
import net.perfectdreams.loritta.commands.images.base.GabrielaImageServerSingleCommandBase
import net.perfectdreams.loritta.commands.images.base.SingleImageOptions
import net.perfectdreams.loritta.common.commands.declarations.CommandExecutorDeclaration

class CepoDeMadeiraExecutor(
    http: HttpClient
) : GabrielaImageServerSingleCommandBase(
    http,
    "/api/v1/images/cepo-de-madeira",
    "cepo_de_madeira.gif"
) {
    companion object : CommandExecutorDeclaration(CepoDeMadeiraExecutor::class) {
        override val options = SingleImageOptions
    }
}