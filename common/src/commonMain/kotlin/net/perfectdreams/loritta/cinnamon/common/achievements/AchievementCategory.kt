package net.perfectdreams.loritta.cinnamon.common.achievements

import net.perfectdreams.i18nhelper.core.keydata.StringI18nData
import net.perfectdreams.loritta.cinnamon.common.emotes.Emote
import net.perfectdreams.loritta.cinnamon.common.emotes.Emotes
import net.perfectdreams.loritta.cinnamon.common.utils.Color
import net.perfectdreams.loritta.cinnamon.i18n.I18nKeysData

enum class AchievementCategory(
    val title: StringI18nData,
    val description: StringI18nData,
    val emote: Emote,
    val color: Color
) {
    SHIP(
        I18nKeysData.Achievements.Category.Ship.Title,
        I18nKeysData.Achievements.Category.Ship.Description("/ship"),
        Emotes.LoriHeart,
        Color(255, 46, 119) // Same color as the 100% ship background
    ),
    RATE(
        I18nKeysData.Achievements.Category.Rate.Title,
        I18nKeysData.Achievements.Category.Rate.Description("/rate"),
        Emotes.LoriReading,
        Color(127, 0, 255)
    )
}