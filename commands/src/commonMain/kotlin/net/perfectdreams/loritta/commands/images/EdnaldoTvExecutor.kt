package net.perfectdreams.loritta.commands.images

import io.ktor.client.*
import net.perfectdreams.loritta.commands.images.base.GabrielaImageServerSingleCommandBase
import net.perfectdreams.loritta.commands.images.base.SingleImageOptions
import net.perfectdreams.loritta.common.commands.declarations.CommandExecutorDeclaration

class EdnaldoTvExecutor(
    http: HttpClient
) : GabrielaImageServerSingleCommandBase(
    http,
    "/api/v1/images/ednaldo-tv",
    "ednaldo_tv.png"
) {
    companion object : CommandExecutorDeclaration(EdnaldoTvExecutor::class) {
        override val options = SingleImageOptions
    }
}