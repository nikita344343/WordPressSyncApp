# Простой способ собрать APK (без Android Studio)

Сборка идёт на GitHub в облаке. На твоём ПК не нужны Android Studio, SDK и JDK.

---

## Шаг 1: Аккаунт и репозиторий на GitHub

1. Зайди на **https://github.com** и войди (или зарегистрируйся).
2. Нажми **«+»** → **New repository**.
3. Название: например **WordPressSyncApp**.
4. Поставь **Private**, если не хочешь, чтобы проект был публичным.
5. **Create repository** (галочки «Add README» не нужны).

---

## Шаг 2: Загрузить проект

У тебя проект лежит в папке **WordPressSyncApp** (например, на диске G: в **G:\AndroidNB\WordPressSyncApp**).

**Вариант A — через сайт GitHub (самый простой):**

1. Открой свой новый репозиторий на GitHub.
2. Нажми **«uploading an existing file»** (или «Add file» → «Upload files»).
3. Перетащи в окно **всё содержимое** папки WordPressSyncApp (внутри должны быть папки `app`, `gradle`, `.github` и файлы `build.gradle.kts`, `settings.gradle.kts` и т.д.).  
   Важно: загружать нужно **содержимое** папки, а не саму папку WordPressSyncApp.
4. Внизу нажми **Commit changes**.

**Вариант B — через приложение GitHub Desktop (если установлено):**

1. File → Add local repository → укажи папку WordPressSyncApp (если там уже есть git) или Create new repository в этой папке.
2. Publish repository на GitHub.
3. Сделай Commit и Push.

---

## Шаг 3: Запустить сборку

1. Открой свой репозиторий на GitHub.
2. Вверху перейди на вкладку **Actions**.
3. Слева выбери workflow **«Build APK»**.
4. Справа нажми **«Run workflow»** → **«Run workflow»**.
5. Подожди 3–7 минут. Когда шаг **«Build Debug APK»** станет зелёным — сборка прошла.

(Если ты загружал файлы через «upload» и ветка создалась как **main** или **master**, то workflow может запуститься сам при первом коммите — тогда просто зайди в **Actions** и открой последний запуск.)

---

## Шаг 4: Скачать APK

1. В **Actions** открой последний зелёный запуск **Build APK**.
2. Внизу страницы в блоке **Artifacts** будет **app-debug**.
3. Нажми на **app-debug** — скачается архив с файлом **app-debug.apk**.
4. Распакуй архив и установи **app-debug.apk** на телефон или перетащи в LDPlayer.

---

## Если что-то пошло не так

- **Workflow не появился в Actions.**  
  Убедись, что при загрузке файлов попала папка **.github** с файлом **.github/workflows/build-apk.yml** внутри проекта.

- **Сборка упала с ошибкой.**  
  В **Actions** открой упавший запуск, нажми на шаг с красным крестиком и посмотри лог. Скопируй текст ошибки — по нему можно будет понять причину.

- **Хочу пересобрать.**  
  В **Actions** → **Build APK** → **Run workflow** → **Run workflow**.

Дальше можно править код у себя, снова заливать файлы в репозиторий и запускать **Run workflow** — каждый раз будет собираться новый APK.
