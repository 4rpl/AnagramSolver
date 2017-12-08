package ru.ilyayudov.mas.DataModels

enum class ToponymType(val friendlyName: String) {
    NotSet("Не выбрано"),
    Street("Улица"),
    Lane("Переулок");

    override fun toString() = friendlyName
}