# typefilter 1.0.0

**Назначение:**<br>
Консольная утилита обрабатывает переданные текстовые файлы, содержащие в перемешку целые числа, строки и
вещественные числа. В процессе обработки исходных файлов:<br>

- по мере необходимости создаются выходные файлы содержащие соответсвующе
  отсортированные данные.
- в процессе обработки собираются статистические данные, которые выводятся в консоль

**Формат входных данных:**<br>
Текстовые файлы с данными для сортировки (не менее одного), в качестве разделителя между данными используется перевод
строки.

**Алгоритм работы** <br>
Строки из файлов читаются по очереди в соответствии с их перечислением в командной строке. В процессе работы происходит
фильтрация и сортировка данных. Считывание данных выполняется построчно. Пустые строки не учитываются.

**Результат работы:**<br>
При обоработке происходит распределение и сохранение значений в 3 выходных файла:<br>
***integers.txt*** - содержит целочисленные значения произвольной длины<br>
***floats.txt*** - содержит вещественные числа <br>
***strings.txt*** - содержит строковые значения<br>

По умолчанию выводится сокращенная статистика обработки - количество обработанных строк.<br>
При необходимости (ключ -f)  можно сформировать расширенную статистику.

## Базовые трбования

- [JDK версии 8](https://www.java.com/ru/download/manual.jsp)
- [система контроля версий Git](https://git-scm.com/downloads)
- [система сборки Gradle](https://gradle.org/install/)
- [автоматизация сборки make](https://gnuwin32.sourceforge.net/packages/make.htm)

## Библиотеки, фреймворки, плагины

(Загружаются автоматически при сборке проекта, указаны в зависимостях build.gradle, интегрированы в jar)

- [picocli 4.7.5](https://picocli.info/) - интерфейс командной строки
- [junit 5.10.1](https://junit.org/junit5/docs/snapshot/release-notes/index.html#release-notes-5.10.1) - фреймвор юнит
  тестирования кода
- [checkstyle](https://checkstyle.sourceforge.io/) - проверка стиля кода

# Компиляция и запуск на Linux через командную строку

Для компиляции и запуска приложения потребуется [утилита make](https://gnuwin32.sourceforge.net/packages/make.htm)<br>

## Шаг1

Если у вас не установлена java 8:

```bash
sudo apt-get install openjdk-8-jdk
```

для установки make на Ubuntu\linux введите команду:

```bash
sudo apt install make
```

## Шаг2

Перейдите в необходимый каталог и произведите клонирования репозитория с помощью команды:

```bash
git clone git@github.com:ZorgIT/test-cft-intership.git
```

## Шаг3

Выполните команды для установки зависимостей, компиляции, и сборки

```bash
cd test-cft-intership
make fast-start
```

## Шаг4

```bash
cd test-cft-intership
make fast-start
```

Все подготовлено, теперь вы можете использовать утилиту для работы <br>
Тестовые файлы расположены в src/resources/test/ (in1.txt, in2.txt)

Пример запуска программы программы:

```bash
java -jar build/libs/typefilter-1.0.0.jar src/test/resources/in1.txt src/test/resources/in2.txt
```

вызов српавки по работе с программой ключ -h

```
Usage: typefilter [-afhsV] [-o=<outputPath>] [-p=<fileNamePrefix>]
[<filePaths>...] [COMMAND]
Get data from text file and extrude to separate file by datatype.
[<filePaths>...]   File Paths
-a                     Appends data to file is existed
-f                     give short result report
-h, --help             Show this help message and exit.
-o=<outputPath>        Path to write output files
-p=<fileNamePrefix>    Add prefix to filename
-s                     give short result report
-V, --version          Print version information and exit.
```

```bash
run --args="src/test/resources/in1.txt src/test/resources/in2.txt -o src/test/resources/output -s -f"
```


