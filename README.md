Необходимо создать Android приложение, в котором используется карта и список хронологии, которые отображают эмуляцию боя.

Карту использовать MapBox, на ней отображается боец или техника соответствующего цвета команды, при клике на которого открывается диалоговое окно 
с обновляемой информацией бойца/техники. В хронологии отображается количество подключенных бойцов и изменение их состояния здоровья. Реализовать возможность просмотра последнего боя (сохраненного при последнем запуске приложения).
В ландшафтной ориентации хронология находится справа от карты, в портретной - под ней. 
При поворотах экрана обеспечить сохранность данных списка.

Данные поступают из real time db (firebase), для доступа к ней предоставлен файл google-services.json (пакет приложения должен быть ua.forposttest), 
для запуска обновления данных в базе необходимо выполнить запрос https://us-central1-test-452f8.cloudfunctions.net/helloWorld. Структура получаемых данных предоставлена в файле fighters.json. Срок выполнения — неделя.

Необходимо сделать форк данного репозитория, все изменения вносить в своем форке.
При необходимости допускается редактирование гитигнора, изменение структуры проекта(разбивки по пэкэджам), 
добавление своих ресурсов и т. д.
Готовое решение должно находится в открытом репозитории.
=======
# ForpostTestTask
