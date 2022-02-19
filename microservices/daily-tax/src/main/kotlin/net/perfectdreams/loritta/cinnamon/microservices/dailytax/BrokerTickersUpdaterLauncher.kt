package net.perfectdreams.loritta.cinnamon.microservices.dailytax

import io.ktor.client.*
import mu.KotlinLogging
import net.perfectdreams.loritta.cinnamon.common.utils.config.ConfigUtils
import net.perfectdreams.loritta.cinnamon.microservices.dailytax.utils.config.RootConfig
import net.perfectdreams.loritta.cinnamon.pudding.Pudding
import kotlin.concurrent.thread

object BrokerTickersUpdaterLauncher {
    private val logger = KotlinLogging.logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        val rootConfig = ConfigUtils.loadAndParseConfigOrCopyFromJarAndExit<RootConfig>(BrokerTickersUpdaterLauncher::class, System.getProperty("brokertickersupdater.config", "broker-tickers-updater.conf"))
        logger.info { "Loaded Loritta's configuration file" }

        val http = HttpClient {
            expectSuccess = false
        }

        val services = Pudding.createPostgreSQLPudding(
            rootConfig.pudding.address,
            rootConfig.pudding.database,
            rootConfig.pudding.username,
            rootConfig.pudding.password
        )

        Runtime.getRuntime().addShutdownHook(
            thread(false) {
                // Shutdown services when stopping the application
                // This is needed for the Pudding Tasks
                services.shutdown()
            }
        )

        logger.info { "Started Pudding client!" }

        val loritta = DailyTax(
            rootConfig,
            services,
            http
        )

        loritta.start()
    }
}