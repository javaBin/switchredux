package no.javazone.switchredux.time

import no.javazone.switchredux.*

class ShowTimeCommandResult(
    val time:String,
    val timeSource:String,
    ):CommandResult() {
}

class ShowTimeCommand:Command {
    override fun execute(): CommandResult {
        return TimeService.currentTimeInfo()
    }
}