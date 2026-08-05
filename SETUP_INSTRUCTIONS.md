# AntiChaos — Інструкція з Налаштування Проєкту

## Що вже готово ✅
- Повна структура Android-проєкту в `App/` папці
- Gradle wrapper налаштовано (gradlew.bat + gradle-wrapper.jar)
- local.properties створено з шляхом до SDK
- Всі Kotlin файли, конфігурації, ресурси на місці

## Що потрібно зробити (1 раз при першому запуску)

### Крок 1: Відкрити проєкт в Android Studio
1. Запустити **Android Studio**
2. **File → Open** (або "Open an Existing Project" на стартовому екрані)
3. Обрати папку: `C:\Users\Public.DESKTOP-726T6F4\Desktop\AntiChaos\App`
4. Натиснути **OK**

### Крок 2: Налаштувати Android SDK (якщо ще не встановлено)
Android Studio автоматично запропонує налаштувати SDK при першому відкритті. Якщо ні:

1. В Android Studio: **File → Project Structure → SDK Location**
   - Перевірити що "Android SDK Location" вказує на: `C:\Users\Public.DESKTOP-726T6F4\AppData\Local\Android\Sdk`
   
2. Відкрити **SDK Manager**: **Tools → SDK Manager**

3. Вкладка **SDK Platforms**:
   - Поставити галочку на **Android 15.0 (Android 15) / API 35**
   - Також потрібен: **Show Package Details** → вибрати "Sources for Android 15" (опціонально)
   - Натиснути **Apply** → **OK**

4. Вкладка **SDK Tools**:
   - Перевірити що встановлено:
     - ✅ Android SDK Build-Tools 35.0.0
     - ✅ Android SDK Command-line Tools (latest)
     - ✅ Android Emulator (якщо плануєте тестувати на емуляторі)
   - Натиснути **Apply** → **OK**

### Крок 3: Прийняти ліцензії
1. В Android Studio: **Tools → SDK Manager → SDK Tools**
2. Прокрутити вниз до **"Android SDK Command-line Tools"** — має бути встановлено
3. Відкрити термінал в Android Studio (**View → Tool Windows → Terminal**)
4. Виконати команду:
   ```bash
   sdkmanager --licenses
   ```
5. Для кожної ліцензії натискати `y` (yes)

### Крок 4: Sync Gradle
1. Після встановлення SDK, Android Studio автоматично запропонує **Sync Project with Gradle Files**
2. Натиснути **Sync Now** (або іконка слона з стрілкою на панелі інструментів)
3. Перший sync займає 5-10 хвилин (завантажує залежності)

### Крок 5: Перевірка
Після успішного sync:
- Зліва в **Project** view має бути повна структура папок
- Без червоних помилок у файлах
- Можна натиснути **Run → Run 'app'** для запуску на емуляторі або реальному пристрої

---

## Якщо виникли проблеми

### "SDK not found" помилка
Перевірити `local.properties` файл в корені `App/`:
```
sdk.dir=C:\Users\Public.DESKTOP-726T6F4\AppData\Local\Android\Sdk
```
Якщо шлях невірний — виправити або видалити файл, Android Studio створить новий.

### Gradle sync fail / "Failed to resolve"
1. Перевірити інтернет-з'єднання
2. **File → Invalidate Caches → Invalidate and Restart**
3. Спробувати sync знову

### "JAVA_HOME not set" в sdkmanager
Використовувати Java від Android Studio:
```bash
set JAVA_HOME=C:\Program Files\Android\Android Studio\jbr
sdkmanager --licenses
```

---

## Після налаштування

Проєкт готовий до розробки. Наступні кроки (Phase 1):
1. Реалізувати ViewModels для кожного екрану
2. Створити реальні Compose UI компоненти (зараз там TODO коментарі)
3. Підключити репозиторії до UI через Flow
4. Додати launcher icons та drawable ресурси

Детальний план: `WORK_PLAN.md`
Структура проєкту: `App/README.md`
