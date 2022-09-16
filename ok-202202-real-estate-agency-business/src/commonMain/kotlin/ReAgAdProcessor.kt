package ru.ibikmetov.kotlin.realestateagency.business

import com.crowdproj.kotlin.cor.*
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import ru.ibikmetov.kotlin.realestateagency.business.general.initRepo
import ru.ibikmetov.kotlin.realestateagency.business.general.operation
import ru.ibikmetov.kotlin.realestateagency.business.validation.validateTitleNotEmpty
import ru.ibikmetov.kotlin.realestateagency.business.stubs.*
import ru.ibikmetov.kotlin.realestateagency.business.validation.*
import ru.ibikmetov.kotlin.realestateagency.business.general.initStatus
import ru.ibikmetov.kotlin.realestateagency.business.general.prepareResult
import ru.ibikmetov.kotlin.realestateagency.business.permission.accessValidation
import ru.ibikmetov.kotlin.realestateagency.business.permission.chainPermissions
import ru.ibikmetov.kotlin.realestateagency.business.repo.*
import ru.ibikmetov.kotlin.realestateagency.common.ReAgContext
import ru.ibikmetov.kotlin.realestateagency.common.models.*

class ReAgAdProcessor(private val settings: ReAgSettings = ReAgSettings()) {
    suspend fun exec(ctx: ReAgContext) = BusinessChain.exec(ctx.apply { settings = this@ReAgAdProcessor.settings })

    companion object {
        private val BusinessChain = rootChain<ReAgContext> {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")

            operation("Создание объявления", ReAgCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                chain {
                    title = "Валидация запроса"
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                    worker("Очистка заголовка") { adValidating.title = adValidating.title.trim() }
                    worker("Очистка описания") { adValidating.description = adValidating.description.trim() }
                    // validate all fields for html TAGs to avoid
                    validateTitleNotEmpty("Проверка на непустой заголовок")
                    validateTitleHasContent("Проверка на наличие содержания в заголовке")
                    validateDescriptionNotEmpty("Проверка на непустое описание")
                    validateDescriptionHasContent("Проверка на наличие содержания в описании")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chainPermissions("Вычисление разрешений для пользователя")
                worker {
                    title = "Инициализация adRepoRead"
                    on { state == ReAgState.RUNNING }
                    handle {
                        adRepoRead = adValidated
                        adRepoRead.ownerId = principal.id
                    }
                }
                accessValidation("Вычисление прав доступа")
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание объявления в БД")
                }
                prepareResult("Подготовка ответа")
                worker {
                    title = "Подготовка ответа"
                    on { state == ReAgState.RUNNING }
                    handle {
                        state = ReAgState.FINISHING
                        adResponse = adRepoDone
                    }
                }
            }
            operation("Получить объявление", ReAgCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                chain {
                    title = "Валидация запроса"
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                    worker("Очистка id") { adValidating.id = ReAgAdId(adValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chainPermissions("Вычисление разрешений для пользователя")
                worker {
                    title = "Инициализация adRepoRead"
                    on { state == ReAgState.RUNNING }
                    handle {
                        adRepoRead = adValidated
                        adRepoRead.ownerId = principal.id
                    }
                }
                accessValidation("Вычисление прав доступа")
                chain {
                    title = "Чтение объекта"
                    repoPrepareRead("Подготовка объекта к чтению из БД")
                    repoRead("Чтение объявления в БД")
                }
                prepareResult("Подготовка ответа")
                worker {
                    title = "Подготовка ответа"
                    on { state == ReAgState.RUNNING }
                    handle {
                        state = ReAgState.FINISHING
                        adResponse = adRepoDone
                    }
                }
            }
            operation("Изменить объявление", ReAgCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                chain {
                    title = "Валидация запроса"
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                    worker("Очистка id") { adValidating.id = ReAgAdId(adValidating.id.asString().trim()) }
                    worker("Очистка заголовка") { adValidating.title = adValidating.title.trim() }
                    worker("Очистка описания") { adValidating.description = adValidating.description.trim() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateTitleNotEmpty("Проверка на непустой заголовок")
                    validateTitleHasContent("Проверка на наличие содержания в заголовке")
                    validateDescriptionNotEmpty("Проверка на непустое описание")
                    validateDescriptionHasContent("Проверка на наличие содержания в описании")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chainPermissions("Вычисление разрешений для пользователя")
                worker {
                    title = "Инициализация adRepoRead"
                    on { state == ReAgState.RUNNING }
                    handle {
                        adRepoRead = adValidated
                        adRepoRead.ownerId = principal.id
                    }
                }
                accessValidation("Вычисление прав доступа")
                chain {
                    title = "Обновление объекта"
                    repoPrepareUpdate("Подготовка объекта к обновлению в БД")
                    repoUpdate("Обновление объявления в БД")
                }
                prepareResult("Подготовка ответа")
                worker {
                    title = "Подготовка ответа"
                    on { state == ReAgState.RUNNING }
                    handle {
                        state = ReAgState.FINISHING
                        adResponse = adRepoDone
                    }
                }
            }
            operation("Удалить объявление", ReAgCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                chain {
                    title = "Валидация запроса"
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                    worker("Очистка id") { adValidating.id = ReAgAdId(adValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chainPermissions("Вычисление разрешений для пользователя")
                worker {
                    title = "Инициализация adRepoRead"
                    on { state == ReAgState.RUNNING }
                    handle {
                        adRepoRead = adValidated
                        adRepoRead.ownerId = principal.id
                    }
                }
                accessValidation("Вычисление прав доступа")
                chain {
                    title = "Удаление объекта"
                    repoPrepareDelete("Подготовка объекта к удалению из БД")
                    repoDelete("Удаление объявления в БД")
                }
                prepareResult("Подготовка ответа")
                worker {
                    title = "Подготовка ответа"
                    on { state == ReAgState.RUNNING }
                    handle {
                        state = ReAgState.FINISHING
                        adResponse = adRepoDone
                    }
                }
            }
            operation("Поиск объявлений", ReAgCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                chain {
                    title = "Валидация запроса"
                    worker("Копируем поля в adFilterValidating") { adFilterValidating = adFilterRequest.copy() }
                    worker("Очистка adFilterValidating") { adFilterValidating = ReAgAdFilter(adFilterValidating.searchString.trim()) }
                    finishAdFilterValidation("Успешное завершение процедуры валидации")
                }
                worker {
                    title = "Инициализация adRepoRead"
                    on { state == ReAgState.RUNNING }
                    handle {
                        adRepoRead = adValidated
                        adRepoRead.ownerId = principal.id
                    }
                }
                chain {
                    title = "Удаление объекта"
                    repoSearch("Поиск объявлений в БД")
                }
                prepareResult("Подготовка ответа")
                worker {
                    title = "Подготовка ответа"
                    on { state == ReAgState.RUNNING }
                    handle {
                        state = ReAgState.FINISHING
                    }
                }
            }
        }.build()
    }
}