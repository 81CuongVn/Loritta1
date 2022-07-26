package net.perfectdreams.loritta.cinnamon.dashboard.frontend.components

import androidx.compose.runtime.Composable
import net.perfectdreams.i18nhelper.core.I18nContext
import net.perfectdreams.i18nhelper.core.keydata.StringI18nData
import net.perfectdreams.loritta.cinnamon.dashboard.frontend.utils.GlobalState
import net.perfectdreams.loritta.cinnamon.dashboard.frontend.utils.State
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Text

@Composable
fun LocalizedText(i18nContext: I18nContext, keyData: StringI18nData) {
    Text(i18nContext.get(keyData))
}

@Composable
fun LocalizedText(globalState: GlobalState, keyData: StringI18nData) {
    val state = globalState.i18nContext
    when (state) {
        is State.Failure -> Text(keyData.key.key)
        is State.Loading -> Text("...")
        is State.Success -> LocalizedText(state.value, keyData)
    }
}

@Composable
fun LocalizedFieldLabel(i18nContext: I18nContext, keyData: StringI18nData, forId: String) {
    Div(attrs = { classes("field-title") }) {
        Label(forId) {
            Text(i18nContext.get(keyData))
        }
    }
}

@Composable
fun TextReplaceControls(
    text: String,
    onText: @Composable (String) -> (Unit),
    onControl: @Composable (String) -> (ControlResult)
) {
    val builder = StringBuilder()
    val control = StringBuilder()
    var controlCharCount = 0

    for (ch in text) {
        if (controlCharCount != 0) {
            if (ch == '{') {
                controlCharCount++
            }

            if (ch == '}') {
                controlCharCount--

                if (controlCharCount == 0) {
                    val controlStr = control.toString()
                    when (val result = onControl.invoke(controlStr)) {
                        is ComposableFunctionResult -> result.block.invoke(controlStr)
                        AppendControlAsIsResult -> builder.append("{$control}")
                        DoNothingResult -> {}
                    }
                    control.clear()
                    continue
                }
            }

            control.append(ch)
            continue
        }

        if (ch == '{') {
            onText.invoke(builder.toString())
            builder.clear()
            controlCharCount++
            continue
        }

        builder.append(ch)
    }

    onText.invoke(builder.toString())
}

@Composable
fun appendAsFormattedText(i18nContext: I18nContext, map: Map<String, Any?>): @Composable (String) -> (Unit) = {
    Text(
        i18nContext.formatter.format(
            it,
            map
        )
    )
}

sealed class ControlResult
class ComposableFunctionResult(val block: @Composable (String) -> (Unit)) : ControlResult()
object DoNothingResult : ControlResult()
object AppendControlAsIsResult : ControlResult()

@Composable
fun LocalizedH1(i18nContext: I18nContext, keyData: StringI18nData) {
    H1 {
        LocalizedText(i18nContext, keyData)
    }
}

@Composable
fun LocalizedH2(i18nContext: I18nContext, keyData: StringI18nData) {
    H2 {
        LocalizedText(i18nContext, keyData)
    }
}