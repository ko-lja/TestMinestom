package lu.kolja.util.extensions

import net.minestom.server.entity.Player
import net.minestom.server.permission.Permission

val op = Permission("*")
/**
 * Sets the player's op status
 * @param value The new op status
 */
fun Player.setOp(value: Boolean) = when(value) {
    true -> let { if (!hasPermission(op)) addPermission(op) }
    false -> let { if (hasPermission(op)) removePermission(op) }
}