package net.perfectdreams.loritta.cinnamon.platform.commands.economy.transactions.transactiontransformers

import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.loritta.cinnamon.common.emotes.Emotes
import net.perfectdreams.loritta.cinnamon.platform.LorittaCinnamon
import net.perfectdreams.loritta.cinnamon.platform.commands.economy.declarations.TransactionsCommand
import net.perfectdreams.loritta.cinnamon.pudding.data.CachedUserInfo
import net.perfectdreams.loritta.cinnamon.pudding.data.SonhosBundlePurchaseSonhosTransaction
import net.perfectdreams.loritta.cinnamon.pudding.data.UserId

object SonhosBundlePurchaseSonhosTransactionTransformer : SonhosTransactionTransformer<SonhosBundlePurchaseSonhosTransaction> {
    override suspend fun transform(
        loritta: LorittaCinnamon,
        i18nContext: I18nContext,
        cachedUserInfo: CachedUserInfo,
        cachedUserInfos: MutableMap<UserId, CachedUserInfo?>,
        transaction: SonhosBundlePurchaseSonhosTransaction
    ): suspend StringBuilder.() -> (Unit) = {
        appendMoneyEarnedEmoji()
        append(
            i18nContext.get(
                TransactionsCommand.I18N_PREFIX.Types.SonhosBundlePurchase.PurchasedSonhos(
                    transaction.sonhos,
                    Emotes.LoriKiss
                )
            )
        )
    }
}