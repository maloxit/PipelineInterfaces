# Интерфейсы по Java(InProgress)
## Общая постановка задачи
Наша задача - сделать универсальный "конвейер" для файлов. Если проще - надо сделать 4 отдельных проекта:
1. _Reader_ - класс, который считывает информацию из файла блоками.
2. _Executor_ - класс, который полученный блок преобразует (по факту алгоритм из 1ой Лабы)
3. _Writer_ - класс, который умеет блоками писать в файл
4. _Manager_ - класс "чтобы править ими всеми"
Основная хитрость в этой лабораторной в том, что первые 3 проекта Иванкову хочется уметь заменять. Например, _Reader_ заменить на _NetworkReader_ (который будет с сайтов скачивать файл) или ваш _Executor_ на _Executor_ любого другого студента. Чтобы этого достичь, необходимо выполнить следующее:
5. _Reader_, _Writer_ и _Executor_ должны быть запакованы в *.jar файлы соответствующими именами.(так мы их превратим в библиотеки)
6. Они должны быть помещены в директорию artefacts в корне проекта _Manager_-а и подключены как библиотеки.
7. Мы должны установить между всеми _Reader_-ами(а так же _Writer_-ами и _Executor_-ами) некое обобщение, которое не зависит от частной реализации, но обязано быть у всех. (Да-да я говорю о том, что нам нужны интерфейсы.) С помощью этого обобщения у всех элементов pipeline и будет общение между собой.
## Остальные важные требования
1. Открывать файловые потоки должен Manager
2. Каждый из 4-х проектов должен инициализироваться от файла конфигурации
3. Все константы должны быть объявлены как глобальные static final переменные
4. В файле конфигурации Manager должны лежать полные(в смысле пакетов) имена до классов Reader-а Executor-а и Writer-а, а также пути до их файлов конфигурации
5. Все пути к файлам в файле конфигурации должны быть относительными(например "configs\code2.txt", а не "C:\Users\TBoRMaTb\JavaLab\configs\code2.txt")
## Описание элементов файла PipelineInterfaces.jar
Чтобы ~~игра в кальмара~~ конструктор пайплайна произошел успешно, был создан этот проект. В нём как раз и находятся "обобщенные" элементы, которые необходимо использовать, чтобы ваш модуль был заменяемым и все прошло хорошо. Для того чтобы воспользоваться ими, вам необходимо лишь подключить PipelineInterfaces.jar в каждый созданный вами проект. Давайте же наконец пройдемся по тому что там есть.
### RC - коды возврата
Так как мы не кидаем исключения, нам нужна унифицированная система для сообщений об ошибке. Именно за это и отвечает этот класс: каждая функция должна его возвращать. Внутри можно обнаружить 3 поля
1. `public​ ​final​ ​RCType​ type;` - тип ошибки. Список всех видов ошибок
    - `CODE_SUCCESS`
    - `CODE_CUSTOM_ERROR`
    - ​`CODE_INVALID_ARGUMENT`​
    - ​`CODE_FAILED_PIPELINE_CONSTRUCTION`
    - `CODE_INVALID_INPUT_FILE​`
    - ​`CODE_INVALID_OUTPUT_FILE`​
    - ​`CODE_CONFIG_FILE_ERROR`​
    - ​`CODE_CONFIG_GRAMMAR_ERROR`​
    - ​`CODE_CONFIG_SEMANTIC_ERROR`​
    - ​`CODE_FAILED_TO_READ`​
    - ​`CODE_FAILED_TO_WRITE`

2. `public​ ​final​ ​RCWho​ who;` - владелец сообщения. Бывает всего 5 типов.
    - `UNKNOWN​(​"​UNKNOWN​"​)`
    - `READER​(​"​READER​"​)`
    - `EXECUTOR​(​"​EXECUTOR​"​)`
    - `WRITER​(​"​WRITER​"​)`
    - `MANAGER​(​"​MANAGER​"​)`

3. `public​ ​final​ ​String​ info;` - строка с осмысленным сообщением. 

Помимо этого, в этом классе в виде констант добавлены типичные RC коды, которыми стоит пользоваться, если они вам подходят. 

 - `public​ ​static​ ​final​ ​RC​ ​RC_SUCCESS​ ​=​ ​new​ ​RC​(​RCWho​.​UNKNOWN​, ​RCType​.​CODE_SUCCESS​, ​"​No errors have occurred.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_MANAGER_INVALID_ARGUMENT​ ​=​ ​new​ ​RC​(​RCWho​.​MANAGER​, ​RCType​.​CODE_INVALID_ARGUMENT​, ​"​Invalid argument for manager.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_MANAGER_INVALID_READER_CLASS​ ​=​ ​new​ ​RC​(​RCWho​.​MANAGER​, ​RCType​.​CODE_FAILED_PIPELINE_CONSTRUCTION​, ​"​Couldn't find valid IReader class.​"​); `
 - ​`public​ ​static​ ​final​ ​RC​ ​RC_MANAGER_INVALID_WRITER_CLASS​ ​=​ ​new​ ​RC​(​RCWho​.​MANAGER​, ​RCType​.​CODE_FAILED_PIPELINE_CONSTRUCTION​, ​"​Couldn't find valid IWriter class.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_MANAGER_INVALID_EXECUTOR_CLASS​ ​=​ ​new​ ​RC​(​RCWho​.​MANAGER​, ​RCType​.​CODE_FAILED_PIPELINE_CONSTRUCTION​, ​"​Couldn't find valid IExecutor class.​"​); `
 - ​`public​ ​static​ ​final​ ​RC​ ​RC_MANAGER_INVALID_INPUT_FILE​ ​=​ ​new​ ​RC​(​RCWho​.​MANAGER​, ​RCType​.​CODE_INVALID_INPUT_FILE​, ​"​Couldn't open input file.​"​); `
 - ​`public​ ​static​ ​final​ ​RC​ ​RC_MANAGER_INVALID_OUTPUT_FILE​ ​=​ ​new​ ​RC​(​RCWho​.​MANAGER​, ​RCType​.​CODE_INVALID_OUTPUT_FILE​, ​"​Couldn't open output file.​"​); `
 - ​`public​ ​static​ ​final​ ​RC​ ​RC_MANAGER_CONFIG_FILE_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​MANAGER​, ​RCType​.​CODE_CONFIG_FILE_ERROR​, ​"​Couldn't open manager config file.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_MANAGER_CONFIG_GRAMMAR_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​MANAGER​, ​RCType​.​CODE_CONFIG_GRAMMAR_ERROR​, ​"​Some grammar error in manager config file.​"​);` 
 - `public​ ​static​ ​final​ ​RC​ ​RC_MANAGER_CONFIG_SEMANTIC_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​MANAGER​, ​RCType​.​CODE_CONFIG_SEMANTIC_ERROR​, ​"​Some semantic error in manager config file.​"​);` 
 - `public​ ​static​ ​final​ ​RC​ ​RC_READER_CONFIG_FILE_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​READER​, ​RCType​.​CODE_CONFIG_FILE_ERROR​, ​"​Couldn't open reader config file.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_READER_CONFIG_GRAMMAR_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​READER​, ​RCType​.​CODE_CONFIG_GRAMMAR_ERROR​, ​"​Some grammar error in reader config file.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_READER_CONFIG_SEMANTIC_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​READER​, ​RCType​.​CODE_CONFIG_SEMANTIC_ERROR​, ​"​Some semantic error in reader config file.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_READER_FAILED_TO_READ​ ​=​ ​new​ ​RC​(​RCWho​.​READER​, ​RCType​.​CODE_FAILED_TO_READ​, ​"​Some input error has occurred while reading from input file.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_WRITER_CONFIG_FILE_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​WRITER​, ​RCType​.​CODE_CONFIG_FILE_ERROR​, ​"​Couldn't open writer config file.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_WRITER_CONFIG_GRAMMAR_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​WRITER​, ​RCType​.​CODE_CONFIG_GRAMMAR_ERROR​, ​"​Some grammar error in writer config file.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_WRITER_CONFIG_SEMANTIC_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​WRITER​, ​RCType​.​CODE_CONFIG_SEMANTIC_ERROR​, ​"​Some semantic error in writer config file.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_WRITER_FAILED_TO_WRITE​ ​=​ ​new​ ​RC​(​RCWho​.​WRITER​, ​RCType​.​CODE_FAILED_TO_WRITE​, ​"​Some output error has occurred while writing to output file.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_EXECUTOR_CONFIG_FILE_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​EXECUTOR​, ​RCType​.​CODE_CONFIG_FILE_ERROR​, ​"​Couldn't open executor config file.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_EXECUTOR_CONFIG_GRAMMAR_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​EXECUTOR​, ​RCType​.​CODE_CONFIG_GRAMMAR_ERROR​, ​"​Some grammar error in executor config file.​"​); `
 - `public​ ​static​ ​final​ ​RC​ ​RC_EXECUTOR_CONFIG_SEMANTIC_ERROR​ ​=​ ​new​ ​RC​(​RCWho​.​EXECUTOR​, ​RCType​.​CODE_CONFIG_SEMANTIC_ERROR​, ​"​Some semantic error in executor config file.​"​)`

Ну и для того чтобы удобнее проверять является ли RC кодом возврата ошибки - добавлен метод

`public​ ​boolean​ ​isSuccess​()`

**Замечание 1**: всегда проверяйте RC

**Замечание 2**: если вдруг вам захотелось вернуть свой код ошибки - воспользуйтесь типом ошибки `CODE_CUSTOM_ERROR`
### IConfigurable - тот кто читает конфиги
Как было описано в пункте "Остальные важные требования" каждый файл должен уметь считывать информацию из файла конфигурации. Для этого был создан данный интерфейс. В нем объявлен всего 1 метод

`RC​ ​setConfig​(​String​ ​cfgFileName​);`

Класс, который имплементирует данный интерфейс, должен по вызову этого метода считать файл конфигурации (путь которого передается как аргумент) и в случае какой-либо ошибки - вернуть соответствующий RC.

**Замечание 1**: данный интерфейс уже включён в интерфейсы IReader, IWriter и IExecutor, так что его стоит явно указывать только в Manager
### IProvider - тот кто выдает данные
Для того чтобы собирать наш "конвейер", нам необходимо как-то пометить тех, кто может передать информацию следующему в pipeline. Такие классы мы и помечаем как IProvider. В данном интерфейсе всего 1 метод.

`RC​ ​setConsumer​(​IConsumer​ ​consumer​);`

Класс, который имплементирует данный интерфейс, должен по вызову этого метода сохранить себе следующий элемент pipeline-а (который передается как аргумент) и в случае какой-либо ошибки - вернуть соответствующий RC. Помимо этого, когда класс будет готов передать часть информации дальше по Pipeline, он должен вызывать у сохраненного IConsumer метод consume.

**Замечание 1**: данный интерфейс уже включён в интерфейсы IReader и IExecutor, так что его не надо указывать явно
### IConsumer - тот кто потребляет данные
Для того чтобы собирать наш "конвейер", нам необходимо как-то пометить тех, кто может получить информацию в pipeline. Такие классы мы и помечаем как IConsumer. В данном интерфейсе всего 1 метод.

`RC​ consume(byte[] data);`

Класс, который имплементирует данный интерфейс, должен по вызову этого метода получить набор байт от предыдущего элемента pipeline-а (который передается как аргумент) и в случае какой-либо ошибки - вернуть соответствующий RC.

**Замечание 1**: данный интерфейс уже включён в интерфейсы IReader и IExecutor, так что его не надо указывать явно

**Замечание 2**: если data==null, то это сигнал на завершение работы конвейера. Обработайте такой вариант отдельно.

**Замечание 3**: в случае ошибки стоит закончить все свои дела и передать следующему null
### IReader - тот кто умеет читать
Первый осмысленный интерфейс. Данным интерфейсом обладает класс являющийся результирующим классом проекта Reader. Данный интерфейс наследуется от интерфейсов IProvider и IConfigurable (соответственно все что относится к ним, относится и к нему).
Помимо этого, данный интерфейс имеет 2 метода:

`RC​ ​setInputStream​(​InputStream​ ​input​);`
`RC​ ​run​();`

Класс, который имплементирует данный интерфейс, должен по вызову первого метода получить InputStream(который передается как аргумент) и сохранить его в локальную переменную, а по вызову второго - запустить обработку (т.к. он гарантированно является первым элементом из pipeline). В случае какой-либо ошибки - вернуть соответствующий RC.

**Замечание 1**: данный интерфейс нужно имплементировать вашим Reader-ом.

**Замечание 2**: вы должны читать текст кусками и кусками передавать следующему элементу

**Замечание 3**: когда вы считаете до конца файла скорее всего последний блок данных будет _меньше_ чем размер вашего буфера. Следите, пожалуйста, за тем чтобы IConsumer получил правильный буфер.

**Замечание 4**: под конец передайте null
### IExecutor - тот кто умеет работать
Промежуточное звено конвейера. Данным интерфейсом обладает результирующий класс проекта Executor. Данный интерфейс наследуется от IConsumer, IProvider, IConfigurable (соответственно все что относится к ним, относится и к нему). Дополнительных методов не имеет.

Класс который имплементирует данный интерфейс обязан по получении данных от предыдущего элемента конвейера (через consume) обработать и передать следующему.
**Замечание 1** Не забудьте про возможность null-а и правильную обработку ошибок.
### IWriter - тот кто умеет писАть
Последний осмысленный интерфейс. Данным интерфейсом обладает класс являющийся результирующим классом проекта Writer. Данный интерфейс наследуется от интерфейсов IConsumer и IConfigurable (соответственно все что относится к ним, относится и к нему).
Помимо этого, данный интерфейс имеет 1 метода:

`RC​ ​seOutputStream​(OutputStream​  output​);`

Класс, который имплементирует данный интерфейс, должен по вызову данного метода получить OutputStream(который передается как аргумент) и сохранить его в локальную переменную, а по вызову consume - записать в OutputStream (промежуточно заполняя и записывая в буфер). В случае какой-либо ошибки - вернуть соответствующий RC.

**Замечание 1**: данный интерфейс нужно имплементировать вашим Writer-ом.

**Замечание 2**: вы должны записывать текст кусками.
## FAQ
- А как мне по файлу конфигурации получить класс?

`try { 
 Class<?> tmp = Class.forName(pipelineParams.readerClassName); 
 if (IReader.class.isAssignableFrom(tmp)) 
 reader = (IReader) tmp.getDeclaredConstructor().newInstance(); 
 else 
 return RC_CANT_FIND_READER_CLASS; 
 } catch (Exception e) { 
 return RC_CANT_FIND_READER_CLASS; 
 }`
 - TBD
## Полезные ссылки
- [Статья о подключении jar ](https://javadevblog.com/kak-dobavit-biblioteku-jar-fajl-v-proekt-intellij-idea.html)
- [Статья о компиляции в jar ](https://ru.stackoverflow.com/questions/427538/%D0%9A%D0%B0%D0%BA-%D1%81%D0%BE%D0%B7%D0%B4%D0%B0%D1%82%D1%8C-jar-%D0%B0%D1%80%D1%85%D0%B8%D0%B2-%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D1%83%D1%8F-%D0%B2-intellij-idea)
- TBD
