package lu.kolja.commands

import lu.kolja.util.extensions.setOp
import lu.kolja.util.extensions.toMini
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player

class DeOperator: Command("deoperator") {

    init {

        setDefaultExecutor { sender, context ->
            if (sender is Player) {
                sender.setOp(false)
                sender.allPermissions.forEach { sender.sendMessage("Permission: ${it.permissionName}") }
                sender.sendMessage("Yay! this <rainbow>worked!!".toMini())
            }
        }

        /*val argType = ArgumentType.String("Player")

        argType.setCallback { s, ctx ->
            s.sendMessage("The Player <red>${ctx.input}</red> is invalid".toMini())
        }

        addSyntax( { s, ctx ->
            val player = Player.


        }, argType)*/
    }
}