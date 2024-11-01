package lu.kolja.util.extensions

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player

val mm = MiniMessage.miniMessage()
/**
 * Parses a string and formats it to a [Component] with [MiniMessage]
 * @return [Component] The formatted string
 */
fun String.toMini(): Component {
    return mm.deserialize(this)
}

/**
 * Parses a string and formats it to a [Component] with [MiniMessage]
 * @param message The message to format and send
 */
fun Player.send(message: String) {
    sendMessage(mm.deserialize(message))
}
fun CommandSender.send(message: String) {
    sendMessage(mm.deserialize(message))
}
fun Array<String>.toMini(): Component {
    return mm.deserialize(joinToString(separator = " "))
}