package com.antichaos.app.core.ai

import com.antichaos.app.data.local.dao.CoachDao
import com.antichaos.app.data.local.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * AI Coach service — handles communication with OpenAI API for emotional support.
 * Falls back to rule-based suggestions when offline or API unavailable.
 */
@Singleton
class AiCoachService @Inject constructor(
    private val coachDao: CoachDao
) {
    // TODO: Inject actual API key from secure storage / settings
    private var openAiApiKey: String? = null
    private var isEnabled: Boolean = false

    fun configure(apiKey: String?, enabled: Boolean) {
        this.openAiApiKey = apiKey
        this.isEnabled = enabled && !apiKey.isNullOrBlank()
    }

    /**
     * Main entry point: user sends a message, AI responds with empathy + technique suggestions.
     */
    suspend fun getCoachResponse(
        userMessage: String,
        recentContext: List<String>? = null // recent tasks/habits/journal for context
    ): CoachResponse {
        if (!isEnabled) {
            return ruleBasedResponse(userMessage)
        }

        return try {
            val prompt = buildPrompt(userMessage, recentContext)
            val response = callOpenAi(prompt)
            
            // Parse AI response and extract technique suggestions
            val techniques = extractTechniquesFromResponse(response)
            
            CoachResponse(
                text = response,
                suggestedTechniqueCodes = techniques,
                source = ResponseSource.AI
            )
        } catch (e: Exception) {
            // Fallback to rule-based on any error
            ruleBasedResponse(userMessage)
        }
    }

    /**
     * Rule-based fallback when AI is unavailable.
     */
    private suspend fun ruleBasedResponse(message: String): CoachResponse {
        val lower = message.lowercase()
        
        // Detect emotional state from keywords
        val emotion = detectEmotion(lower)
        
        // Get relevant techniques from local database
        val trigger = getTriggerForEmotion(emotion)
        val techniques = coachDao.getTechniquesForTrigger(trigger)
        
        val responseText = buildRuleBasedResponse(emotion, techniques)
        
        return CoachResponse(
            text = responseText,
            suggestedTechniqueCodes = techniques.take(3).map { it.code },
            source = ResponseSource.RULE_BASED
        )
    }

    private fun detectEmotion(text: String): Emotion {
        val stressWords = listOf("стрес", "напруга", "тиск", "нерви")
        val overwhelmWords = listOf("задушує", "не встигаю", "занадто багато", "хаос")
        val anxietyWords = listOf("тривога", "хвилююся", "страшно", "боязнь")
        val sadWords = listOf("сумно", "погано", "безнадія", "втомився")
        val procrastinationWords = listOf("відкладаю", "не можу почати", "прокрастину")

        if (stressWords.any { it in text }) return Emotion.STRESSED
        if (overwhelmWords.any { it in text }) return Emotion.OVERWHELMED
        if (anxietyWords.any { it in text }) return Emotion.ANXIOUS
        if (sadWords.any { it in text }) return Emotion.SAD
        if (procrastinationWords.any { it in text }) return Emotion.CONFUSED // maps to procrastination trigger
        
        return Emotion.NEUTRAL
    }

    private fun getTriggerForEmotion(emotion: Emotion): String {
        return when (emotion) {
            Emotion.STRESSED -> "stress"
            Emotion.OVERWHELMED -> "overwhelm"
            Emotion.ANXIOUS -> "anxiety"
            Emotion.SAD -> "low_mood"
            Emotion.CONFUSED -> "procrastination"
            else -> "general"
        }
    }

    private fun buildRuleBasedResponse(emotion: Emotion, techniques: List<TechniqueEntity>): String {
        val greeting = when (emotion) {
            Emotion.STRESSED -> "Чую тебе. Стрес заважає мислити чітко."
            Emotion.OVERWHELMED -> "Зрозумів — зараз занадто багато всього."
            Emotion.ANXIOUS -> "Тривога говорить голосніше, ніж ти хочеш."
            Emotion.SAD -> "Іноді буває важко. Це нормально."
            else -> "Розкажи більше — я тут щоб допомогти."
        }

        val techniqueSuggestions = if (techniques.isNotEmpty()) {
            "\n\nСпробуй одну з цих технік:\n" +
                techniques.take(2).joinToString("\n") { "• ${it.name}: ${it.description}" }
        } else ""

        return "$greeting$techniqueSuggestions"
    }

    private suspend fun callOpenAi(prompt: String): String = withContext(Dispatchers.IO) {
        // TODO: Implement actual OpenAI API call
        // For now, placeholder structure
        throw NotImplementedError("OpenAI integration pending")
    }

    private fun buildPrompt(userMessage: String, context: List<String>?): String {
        val contextBlock = context?.takeIf { it.isNotEmpty() }
            ?.let { "Останні події користувача:\n${it.joinToString("\n")}" } ?: ""

        return """
        Ти — AntiChaos Coach. Твоя роль: підтримати, зрозуміти емоційний стан і запропонувати КОНКРЕТНУ техніку зі списку.
        
        Правила:
        1. Відповідай українською мовою
        2. Будь коротким (3-5 речень)
        3. Не засуджуй, не давай загальних порад типу "треба більше спати"
        4. Завжди пропонуй 1-3 конкретні техніки з master list
        5. Формат технік: [TECHNIQUE:code] наприклад [TECHNIQUE:breathing_478]
        
        $contextBlock
        
        Користувач пише: "$userMessage"
        
        Твоя відповідь:
        """.trimIndent()
    }

    private fun extractTechniquesFromResponse(response: String): List<String> {
        val pattern = Regex("\\[TECHNIQUE:(\\w+)\\]")
        return pattern.findAll(response)
            .map { it.groupValues[1] }
            .distinct()
            .toList()
    }

    data class CoachResponse(
        val text: String,
        val suggestedTechniqueCodes: List<String>,
        val source: ResponseSource
    )

    enum class ResponseSource { AI, RULE_BASED }
}
