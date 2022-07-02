package ru.ibikmetov.kotlin.realestateagency.common.exceptions

import ru.ibikmetov.kotlin.realestateagency.common.models.ReAgCommand

class UnknownReAgCommand(command: ReAgCommand) : Throwable("Wrong command $command at mapping toTransport stage")