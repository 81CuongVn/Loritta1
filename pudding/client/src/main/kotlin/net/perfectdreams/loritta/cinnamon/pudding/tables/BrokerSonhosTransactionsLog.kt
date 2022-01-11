package net.perfectdreams.loritta.cinnamon.pudding.tables

import net.perfectdreams.loritta.cinnamon.pudding.services.BovespaBrokerService
import net.perfectdreams.loritta.cinnamon.pudding.utils.exposed.postgresEnumeration
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.timestamp

object BrokerSonhosTransactionsLog : LongIdTable() {
    val timestampLog = reference("timestamp_log", SonhosTransactionsLog).nullable()
    val user = reference("user", Profiles).index().nullable()
    val action = postgresEnumeration<BovespaBrokerService.BrokerSonhosTransactionsEntryAction>("action")
    val ticker = reference("ticker", TickerPrices)
    val sonhos = long("sonhos")
    val stockPrice = long("stock_price")
    val stockQuantity = long("stock_quantity")
    val timestamp = timestamp("timestamp").nullable()
}