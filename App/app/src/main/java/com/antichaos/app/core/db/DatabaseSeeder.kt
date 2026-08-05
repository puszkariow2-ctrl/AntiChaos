package com.antichaos.app.core.db

import com.antichaos.app.data.local.dao.CoachDao
import com.antichaos.app.data.local.dao.SettingsDao
import com.antichaos.app.data.local.entity.DefaultDailyAnchors
import com.antichaos.app.data.local.entity.DefaultLifeAreas
import com.antichaos.app.data.local.entity.TechniqueEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Seeds initial data on first app launch:
 * - Default daily anchors (schedule)
 * - Default life areas
 * - Starter techniques for AI coach
 */
class DatabaseSeeder @Inject constructor(
    private val settingsDao: SettingsDao,
    private val coachDao: CoachDao
) {
    suspend fun seedInitialData() = withContext(Dispatchers.IO) {
        // Check if already seeded
        val existingAnchors = settingsDao.getActiveAnchors().firstOrNull()?.size ?: 0
        if (existingAnchors > 0) return@withContext // Already done

        // Seed daily anchors
        DefaultDailyAnchors.anchors.forEach { anchor ->
            settingsDao.insertAnchor(anchor)
        }

        // Seed life areas
        DefaultLifeAreas.areas.forEach { area ->
            settingsDao.insertLifeArea(area)
        }

        // Seed starter techniques (core set for AI coach)
        seedTechniques()
    }

    private suspend fun seedTechniques() {
        val techniques = listOf(
            // STABILIZATION techniques
            TechniqueEntity(
                code = "breathing_478",
                name = "Дихання 4-7-8",
                category = 0, // STABILIZATION
                description = "Швидка техніка для заспокоєння нервової системи. Допомагає при стресі та безсонні.",
                stepsJson = "[\"Видихни повністю через рот\", \"Заткни ніс і вдихни носом на 4 секунди\", \"Затримай дихання на 7 секунд\", \"Повільно видихни через рот на 8 секунд\", \"Повтори 3-4 цикли\"]",
                durationMinutes = 3,
                triggersJson = "[\"stress\", \"insomnia\", \"anxiety\"]"
            ),
            TechniqueEntity(
                code = "grounding_54321",
                name = "Заземлення 5-4-3-2-1",
                category = 0, // STABILIZATION
                description = "Повертає тебе в момент \"зараз\" коли розум блукає у тривозі або хаосі.",
                stepsJson = "[\"Подивись навколо і назви 5 речей які ти бачиш\", \"Торкнись 4 речей навколо тебе\", \"Почуй 3 звуки\", \"Понюхай 2 запахи (або уяви)\", \"Почуй 1 смак або емоцію в тілі\"]",
                durationMinutes = 2,
                triggersJson = "[\"anxiety\", \"overwhelm\", \"panic\"]"
            ),
            TechniqueEntity(
                code = "box_breathing",
                name = "Квадратне дихання",
                category = 0, // STABILIZATION
                description = "Військова техніка для швидкої стабілізації. Використовують Navy SEALs.",
                stepsJson = "[\"Вдихни на 4 секунди\", \"Затримай дихання на 4 секунди\", \"Видихни на 4 секунди\", \"Затримай без повітря на 4 секунди\", \"Повтори 5-10 разів\"]",
                durationMinutes = 3,
                triggersJson = "[\"stress\", \"focus\", \"anger\"]"
            ),

            // COGNITIVE techniques
            TechniqueEntity(
                code = "reframe_friend",
                name = "Що б ти сказав другу?",
                category = 1, // COGNITIVE
                description = "Зменшує самокритику. Ми часто жорстокіші до себе ніж до інших.",
                stepsJson = "[\"Подумай про свою ситуацію\", \"Уяви що твій найкращий друг розповідає тобі те саме\", \"Що б ти йому сказав? Запиши ці слова\", \"Тепер скажи це собі\"]",
                durationMinutes = 5,
                triggersJson = "[\"self_criticism\", \"low_mood\", \"failure\"]"
            ),
            TechniqueEntity(
                code = "evidence_check",
                name = "Перевірка доказів",
                category = 1, // COGNITIVE
                description = "Боротьба з негативними думками через логіку. Що правда, а що тривога?",
                stepsJson = "[\"Запиши свою негативну думку\", \"Які докази ЩО ВОНА ПРАВДИВА?\", \"Які докази ЩО ВОНА НЕПРАВДИВА?\", \"Який більш об'єктивний погляд на ситуацію?\"]",
                durationMinutes = 5,
                triggersJson = "[\"anxiety\", \"negative_thinking\", \"self_doubt\"]"
            ),

            // ACTION techniques (for procrastination)
            TechniqueEntity(
                code = "five_minute_rule",
                name = "Правило 5 хвилин",
                category = 2, // ACTION
                description = "Для прокрастинації. Зобов'язуй себе лише на 5 хвилин — почати найскладніше.",
                stepsJson = "[\"Обери одну задачу яку відкладаєш\", \"Постав таймер на 5 хвилин\", \"Роби задачу ЛИШЕ 5 хвилин\", \"Після таймера: або продовжуй, або зупиняйся без провини\"]",
                durationMinutes = 5,
                triggersJson = "[\"procrastination\", \"overwhelm\", \"resistance\"]"
            ),
            TechniqueEntity(
                code = "break_it_down",
                name = "Розбий на кроки",
                category = 2, // ACTION
                description = "Коли задача занадто велика і лякає — розбий її до смішно малих кроків.",
                stepsJson = "[\"Напиши задачу як вона є\", \"Запитай: 'Який перший фізичний крок?'\", \"Розбий кожен крок ще дрібніше\", \"Почни з найменшого кроку — той що займає менше 2 хвилин\"]",
                durationMinutes = 5,
                triggersJson = "[\"overwhelm\", \"procrastination\", \"big_task\"]"
            ),

            // EMOTIONAL techniques
            TechniqueEntity(
                code = "gratitude_3",
                name = "3 речі за які вдячний",
                category = 3, // EMOTIONAL
                description = "Швидкий зсув уваги від проблем до того що йде добре.",
                stepsJson = "[\"Зупинись на 1 хвилину\", \"Назви 3 конкретні речі за які ти вдячний сьогодні\", \"Не 'здоров'я' — а конкретно: 'гарна кава вранці', 'дзвінок друга'\", \"Почуй вдячність хоча б на 5 секунд\"]",
                durationMinutes = 2,
                triggersJson = "[\"low_mood\", \"gratitude\", \"perspective\"]"
            ),
            TechniqueEntity(
                code = "self_compassion_break",
                name = "Пауза самоспівчуття",
                category = 3, // EMOTIONAL
                description = "Коли тобі боляче або ти помилився — будь ласкавим до себе.",
                stepsJson = "[\"Скажи: 'Це важкий момент'\", \"Скажи: 'Страждання — це частина людського досвіду'\", \"Положи руку на серце і скажи собі щось ласкаве\", \"Наприклад: 'Все буде добре', 'Я роблю що можу'\"]",
                durationMinutes = 3,
                triggersJson = "[\"failure\", \"self_criticism\", \"pain\"]"
            )
        )

        techniques.forEach { coachDao.insertTechnique(it) }
    }
}
