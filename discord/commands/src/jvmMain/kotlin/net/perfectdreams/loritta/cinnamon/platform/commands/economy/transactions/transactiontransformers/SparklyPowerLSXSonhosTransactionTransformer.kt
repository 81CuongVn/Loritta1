package net.perfectdreams.loritta.cinnamon.platform.commands.economy.transactions.transactiontransformers

import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.loritta.cinnamon.common.utils.SparklyPowerLSXTransactionEntryAction
import net.perfectdreams.loritta.cinnamon.platform.LorittaCinnamon
import net.perfectdreams.loritta.cinnamon.platform.commands.economy.declarations.TransactionsCommand
import net.perfectdreams.loritta.cinnamon.pudding.data.CachedUserInfo
import net.perfectdreams.loritta.cinnamon.pudding.data.SparklyPowerLSXSonhosTransaction
import net.perfectdreams.loritta.cinnamon.pudding.data.UserId

object SparklyPowerLSXSonhosTransactionTransformer : SonhosTransactionTransformer<SparklyPowerLSXSonhosTransaction> {
    override suspend fun transform(
        loritta: LorittaCinnamon,
        i18nContext: I18nContext,
        cachedUserInfo: CachedUserInfo,
        cachedUserInfos: MutableMap<UserId, CachedUserInfo?>,
        transaction: SparklyPowerLSXSonhosTransaction
    ): suspend StringBuilder.() -> (Unit) = {
        when (transaction.action) {
            SparklyPowerLSXTransactionEntryAction.EXCHANGED_TO_SPARKLYPOWER -> {
                appendMoneyLostEmoji()
                append(
                    i18nContext.get(
                        TransactionsCommand.I18N_PREFIX.Types.SparklyPowerLsx.ExchangedToSparklyPower(
                            transaction.sonhos,
                            transaction.playerName,
                            transaction.sparklyPowerSonhos,
                            "mc.sparklypower.net"
                        )
                    )
                )
            }
            SparklyPowerLSXTransactionEntryAction.EXCHANGED_FROM_SPARKLYPOWER -> {
                appendMoneyEarnedEmoji()
                append(
                    i18nContext.get(
                        TransactionsCommand.I18N_PREFIX.Types.SparklyPowerLsx.ExchangedFromSparklyPower(
                            transaction.sparklyPowerSonhos,
                            transaction.playerName,
                            transaction.sonhos,
                            "mc.sparklypower.net"
                        )
                    )
                )
            }
        }
    }
}