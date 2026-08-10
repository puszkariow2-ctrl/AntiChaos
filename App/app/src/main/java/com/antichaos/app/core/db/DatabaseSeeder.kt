package com.antichaos.app.core.db

import com.antichaos.app.data.local.dao.PracticeDao
import com.antichaos.app.data.local.entity.PracticeEntity

object DatabaseSeeder {

    suspend fun seedPracticesIfEmpty(practiceDao: PracticeDao) {
        if (practiceDao.count() > 0L) return // Already seeded

        val practices = listOf(
            // ─── BREATHING (category 0) ──────────────────────
            PracticeEntity(
                title = "Box Breathing",
                category = 0,
                durationSeconds = 120,
                difficulty = 0,
                instructions = """
                    1. Видихни повністю
                    2. Вдихни на 4 секунди
                    3. Затримай дихання на 4 секунди
                    4. Видихни на 4 секунди
                    5. Затримай на 4 секунди
                    Повторюй цикл
                """.trimIndent(),
                whenToUse = "anxiety,stress,focus"
            ),
            PracticeEntity(
                title = "Дихання 4-7-8",
                category = 0,
                durationSeconds = 180,
                difficulty = 0,
                instructions = """
                    1. Видихни повністю через рот
                    2. Закрий рот, вдихни носом на 4 секунди
                    3. Затримай дихання на 7 секунд
                    4. Повільно видихни через рот на 8 секунд
                    Зроби 4 цикли
                """.trimIndent(),
                whenToUse = "sleep,anxiety,stress"
            ),
            PracticeEntity(
                title = "Когерентне дихання",
                category = 0,
                durationSeconds = 300,
                difficulty = 1,
                instructions = """
                    Дихай рівномірно: 5 секунд вдих, 5 секунд видих.
                    Без затримок. Ритмічно, як хвилі.
                    Фокусуйся тільки на диханні.
                """.trimIndent(),
                whenToUse = "stress,focus,balance"
            ),
            PracticeEntity(
                title = "Вілька капала (Bhramari)",
                category = 0,
                durationSeconds = 120,
                difficulty = 0,
                instructions = """
                    Закрий очі. Постав пальці на крила носа.
                    Глибоко вдихни носом.
                    Видихни носом, бурмотячи як бджола (ммм).
                    Відчуй вібрацію в голові. Повтори 5-7 разів.
                """.trimIndent(),
                whenToUse = "anxiety,stress,sleep"
            ),

            // ─── GROUNDING (category 1) ──────────────────────
            PracticeEntity(
                title = "Заземлення 5-4-3-2-1",
                category = 1,
                durationSeconds = 180,
                difficulty = 0,
                instructions = """
                    Знайди навколо себе:
                    • 5 речей які бачиш
                    • 4 речі які можеш торкнутися
                    • 3 звуки які чуєш
                    • 2 запахи які відчуваєш
                    • 1 смак який відчуваєш
                """.trimIndent(),
                whenToUse = "anxiety,panic,overwhelm"
            ),
            PracticeEntity(
                title = "Сканування тіла",
                category = 1,
                durationSeconds = 300,
                difficulty = 0,
                instructions = """
                    Закрий очі. Повільно пройди увагою від пальців ніг до макушки.
                    Звертай увагу на кожну частину тіла: де напруга? де спокій?
                    Не змінюй нічого — просто помічай.
                """.trimIndent(),
                whenToUse = "stress,sleep,body-awareness"
            ),
            PracticeEntity(
                title = "Холодна вода",
                category = 1,
                durationSeconds = 60,
                difficulty = 0,
                instructions = """
                    Умийся холодною водою або приложи холодний компрес до шиї/обличчя.
                    Це миттєво активує dive reflex і знижує тривогу.
                """.trimIndent(),
                whenToUse = "panic,anxiety,overwhelm"
            ),

            // ─── PHYSICAL (category 2) ──────────────────────
            PracticeEntity(
                title = "Розтяжка для спини (5 хв)",
                category = 2,
                durationSeconds = 300,
                difficulty = 0,
                instructions = """
                    Для тих хто сидить весь день:
                    1. Наклони голову до плеча (ліва/права) — по 20 сек
                    2. Обійми себе за плечі і розтягни спину — 20 сек
                    3. Стоячи, підніми руки вгору і потягнися — 15 сек
                    4. Сидячи, нахил вперед до колін — 20 сек
                """.trimIndent(),
                whenToUse = "desk-work,tension,fatigue"
            ),
            PracticeEntity(
                title = "Прогресивна релаксація",
                category = 2,
                durationSeconds = 300,
                difficulty = 1,
                instructions = """
                    Починаючи з ніг: напруж м'язи на 5 секунд, потім різко розслаб.
                    Піднімайся вище: гомілки → стегна → живіт → руки → плечі → обличчя.
                    Відчуй різницю між напругою і розслабленням.
                """.trimIndent(),
                whenToUse = "stress,sleep,tension"
            ),
            PracticeEntity(
                title = "5 хв руху",
                category = 2,
                durationSeconds = 300,
                difficulty = 0,
                instructions = """
                    Встань і просто рухайся:
                    • Пройдись по кімнаті
                    • Зроби кілька присідань
                    • Потягнися вгору
                    Рух змінює стан — навіть мінімум.
                """.trimIndent(),
                whenToUse = "fatigue,stuck,low-energy"
            ),

            // ─── MENTAL (category 3) ──────────────────────
            PracticeEntity(
                title = "Медитація уваги (3 хв)",
                category = 3,
                durationSeconds = 180,
                difficulty = 1,
                instructions = """
                    Закрий очі. Фокусуйся на диханні.
                    Коли розум відволікається — м'яко повертай увагу до дихання.
                    Не осуджуй себе за відволікання — це нормально. Просто повертайся.
                """.trimIndent(),
                whenToUse = "focus,mindfulness,daily-practice"
            ),
            PracticeEntity(
                title = "Візуалізація дня",
                category = 3,
                durationSeconds = 180,
                difficulty = 0,
                instructions = """
                    Закрий очі. Уяви свій день ідеально: ти спокійний, фокусований, продуктивний.
                    Побач як ти легко робиш важливі справи. Відчуй цей стан тілом.
                    Це не магія — це налаштування мозку на успіх.
                """.trimIndent(),
                whenToUse = "morning,focus,motivation"
            ),
            PracticeEntity(
                title = "Переформулювання думок",
                category = 3,
                durationSeconds = 120,
                difficulty = 1,
                instructions = """
                    Запиши негативну думку (напр. "Я нічого не вмію").
                    Запитай: чи це правда? Які докази проти?
                    Переформулюй: "Я ще вчусь, і я вже зробив X".
                """.trimIndent(),
                whenToUse = "self-criticism,negative-thoughts,anxiety"
            ),

            // ─── EMOTIONAL (category 4) ──────────────────────
            PracticeEntity(
                title = "Назвай емоцію",
                category = 4,
                durationSeconds = 120,
                difficulty = 0,
                instructions = """
                    Зупинись на хвилину. Запитай себе: "Що я зараз відчуваю?"
                    Спробуй назвати емоцію якомога точніше: не просто "погано", а "розчарований", "тривожний", "втомлений".
                    Назвавши — ти вже трохи контролюєш.
                """.trimIndent(),
                whenToUse = "emotional-overwhelm,self-awareness,anytime"
            ),
            PracticeEntity(
                title = "Лист до себе",
                category = 4,
                durationSeconds = 300,
                difficulty = 1,
                instructions = """
                    Напиши коротке повідомлення собі: що ти відчуваєш, чому важко, що тобі потрібно.
                    Не для публікації — тільки для тебе. Це вивантажує голову.
                """.trimIndent(),
                whenToUse = "stress,sadness,confusion"
            ),

            // ─── SPIRITUAL/MEANING (category 5) ──────────────
            PracticeEntity(
                title = "3 речі за які вдячний",
                category = 5,
                durationSeconds = 120,
                difficulty = 0,
                instructions = """
                    Подумай і запиши (в щоденнику або в голові) 3 речі за які ти вдячний сьогодні.
                    Можуть бути дрібними: гарна кава, сонце за вікном, повідомлення від друга.
                    Вдячність змінює фокус з того чого немає на те що є.
                """.trimIndent(),
                whenToUse = "gratitude,mood-boost,daily-practice"
            ),
            PracticeEntity(
                title = "Що для тебе важливо зараз?",
                category = 5,
                durationSeconds = 180,
                difficulty = 0,
                instructions = """
                    Зупинись і запитай: що справді важливо в моєму житті прямо зараз?
                    Не "має бути", а "є". Запиши 3 речі. Це твій компас.
                """.trimIndent(),
                whenToUse = "lost-direction,meaning,stress"
            ),
            PracticeEntity(
                title = "Зв'язок з природою",
                category = 5,
                durationSeconds = 300,
                difficulty = 0,
                instructions = """
                    Вийди назовні (або до вікна). Подивись на небо/дерева. Послухай звуки.
                    Ти — частина чогось більшого. Це зменшує тривогу і повертає перспективу.
                """.trimIndent(),
                whenToUse = "overwhelm,anxiety,stuck"
            )
        )

        practiceDao.insertAll(practices)
    }
}
