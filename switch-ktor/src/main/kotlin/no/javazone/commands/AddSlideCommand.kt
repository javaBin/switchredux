package no.javazone.commands

import io.ktor.http.*
import kotlinx.serialization.json.Json
import no.javazone.Command
import no.javazone.CommandResult
import no.javazone.Database
import no.javazone.EmptyResult
import org.jsonbuddy.JsonObject

class AddSlideCommand(
    val slidedeck:String?=null,
    val slidenumber:Int?=null,
    val content:JsonObject?=null,
    val duration:Int?=null,
):Command {

    override fun execute(): CommandResult {
        if (slidedeck == null) return Command.badRequest("Missing slidedeck")
        if (slidenumber == null) return Command.badRequest("Missing slidenumber")
        if (content == null) return Command.badRequest("Missing content")
        if (duration == null) return Command.badRequest("Mising duration")
        Database.connection().prepareStatement("insert into slide(slidedeck,slidenumber,content,duration) values (?,?,?,?)").use {
            it.setString(1,slidedeck)
            it.setInt(2,slidenumber)
            it.setString(3,content.toJson())
            it.executeUpdate()
        }
        return EmptyResult()
    }
}