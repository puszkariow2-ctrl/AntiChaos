package com.antichaos.app.core.nlp

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Natural Language Parser for quick capture input.
 * Ports logic from the original bot's ai_parser.py but runs locally without API.
 * 
 * Detects intent: task, reminder, idea, journal entry
 * Extracts: title, due time, priority hints
 */
class NaturalLanguageParser {

    data class ParseResult(
        val intent: Intent,
        val title: String,
        val description: String?,
        val remindAt: LocalDateTime?,
        val priorityHint: PriorityHint?
    )

    enum class Intent { TASK, REMINDER, IDEA, JOURNAL, UNKNOWN }
    enum class PriorityHint { HIGH, NORMAL, LOW }

    fun parse(input: String): ParseResult {
        val text = input.trim()
        if (text.isEmpty()) return ParseResult(Intent.UNKNOWN, "", null, null, null)

        // Check for reminder patterns first
        val remindAt = extractRemindTime(text)
        if (remindAt != null || isReminderIntent(text)) {
            return ParseResult(
                intent = Intent.REMINDER,
                title = cleanTitle(text),
                description = null,
                remindAt = remindAt ?: LocalDateTime.now().plusMinutes(5),
                priorityHint = detectPriority(text)
            )
        }

        // Check for journal/mood patterns
        if (isJournalIntent(text)) {
            return ParseResult(
                intent = Intent.JOURNAL,
                title = "Запис",
                description = text,
                remindAt = null,
                priorityHint = null
            )
        }

        // Check for idea patterns
        if (isIdeaIntent(text)) {
            return ParseResult(
                intent = Intent.IDEA,
                title = cleanTitle(text),
                description = null,
                remindAt = null,
                priorityHint = PriorityHint.LOW
            )
        }

        // Default: treat as task
        return ParseResult(
            intent = Intent.TASK,
            title = cleanTitle(text),
            description = null,
            remindAt = null,
            priorityHint = detectPriority(text)
        )
    }

    private fun isReminderIntent(text: String): Boolean {
        val lower = text.lowercase(Locale.getDefault())
        return listOf(
            "нагадай", "нагадати", "через", "о ", "в ", "завтра о", "сьогодні о"
        ).any { it in lower }
    }

    private fun isJournalIntent(text: String): Boolean {
        val lower = text.lowercase(Locale.getDefault())
        return listOf(
            "почуваюся", "відчуваю", "мені ", "сьогодні був", "хочу записати", "думки"
        ).any { it in lower } || 
        (lower.startsWith("я ") && text.length > 50) // long personal thoughts
    }

    private fun isIdeaIntent(text: String): Boolean {
        val lower = text.lowercase(Locale.getDefault())
        return listOf(
            "ідея", "можливо", "а якби", "треба спробувати", "цікаво було б"
        ).any { it in lower }
    }

    private fun extractRemindTime(text: String): LocalDateTime? {
        val lower = text.lowercase(Locale.getDefault())
        val now = LocalDateTime.now()

        // "через X хвилин/годин"
        val relativeMinutes = Regex("через\\s+(\\d+)\\s*хв", RegexOption.IGNORE_CASE).find(lower)
        if (relativeMinutes != null) {
            return now.plusMinutes(relativeMinutes.groupValues[1].toInt())
        }

        val relativeHours = Regex("через\\s+(\\d+)\\s*год", RegexOption.IGNORE_CASE).find(lower)
        if (relativeHours != null) {
            return now.plusHours(relativeHours.groupValues[1].toInt())
        }

        // "о HH:MM" or "в HH:MM"
        val timePattern = Regex("(?:о|в)\\s*(\\d{1,2}):(\\d{2})", RegexOption.IGNORE_CASE).find(lower)
        if (timePattern != null) {
            val hour = timePattern.groupValues[1].toInt()
            val minute = timePattern.groupValues[2].toInt()
            var result = now.withHour(hour).withMinute(minute)
            if (result.isBefore(now)) result = result.plusDays(1) // tomorrow if passed
            return result
        }

        // "завтра"
        if ("завтра" in lower) {
            return now.plusDays(1)
        }

        return null
    }

    private fun detectPriority(text: String): PriorityHint? {
        val lower = text.lowercase(Locale.getDefault())
        if (listOf("терміново", "важливо", "критично", "якнайшвидше").any { it in lower }) {
            return PriorityHint.HIGH
        }
        if (listOf("коли-небудь", "можливо пізніше", "не поспішає").any { it in lower }) {
            return PriorityHint.LOW
        }
        return null
    }

    private fun cleanTitle(text: String): String {
        // Remove common prefixes to get clean task/reminder title
        val cleaned = text
            .replace(Regex("^нагадай(?:\\s+мені)?\\s*", RegexOption.IGNORE_CASE), "")
            .replace(Regex("^треба\\s+", RegexOption.IGNORE_CASE), "")
            .replace(Regex("^потрібно\\s+", RegexOption.IGNORE_CASE), "")
            .trim()
        return cleaned.takeIf { it.isNotEmpty() } ?: text
    }
}
