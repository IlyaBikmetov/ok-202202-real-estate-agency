package ru.ibikmetov.kotlin.realestateagency.mappers.exceptions

class UnknownRequestClass(clazz: Class<*>): RuntimeException("Class $clazz cannot be mapped to ReAgContext")