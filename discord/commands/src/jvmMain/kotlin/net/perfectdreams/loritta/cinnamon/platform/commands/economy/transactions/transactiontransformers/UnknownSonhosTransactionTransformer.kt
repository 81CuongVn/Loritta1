package net.perfectdreams.loritta.cinnamon.platform.commands.economy.transactions.transactiontransformers

import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.loritta.cinnamon.common.emotes.Emotes
import net.perfectdreams.loritta.cinnamon.platform.LorittaCinnamon
import net.perfectdreams.loritta.cinnamon.pudding.data.CachedUserInfo
import net.perfectdreams.loritta.cinnamon.pudding.data.SonhosBundlePurchaseSonhosTransaction
import net.perfectdreams.loritta.cinnamon.pudding.data.UnknownSonhosTransaction
import net.perfectdreams.loritta.cinnamon.pudding.data.UserId

object UnknownSonhosTransactionTransformer : SonhosTransactionTransformer<UnknownSonhosTransaction> {
    override suspend fun transform(
        loritta: LorittaCinnamon,
        i18nContext: I18nContext,
        cachedUserInfo: CachedUserInfo,
        cachedUserInfos: MutableMap<UserId, CachedUserInfo?>,
        transaction: UnknownSonhosTransaction
    ): suspend StringBuilder.() -> (Unit) = {
        append("${Emotes.LoriShrug} Unknown Transaction (Bug?)")
    }
}