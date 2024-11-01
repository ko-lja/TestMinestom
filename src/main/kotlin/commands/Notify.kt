package lu.kolja.commands

import lu.kolja.enums.Sounds
import lu.kolja.util.extensions.send
import lu.kolja.util.extensions.toMini
import net.minestom.server.advancements.FrameType
import net.minestom.server.advancements.notifications.Notification
import net.minestom.server.advancements.notifications.NotificationCenter
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.item.Material

class Notify: Command("notify") {

    init {
        setDefaultExecutor{ s, ctx ->
            if (s !is Player) return@setDefaultExecutor
            s.playSound(Sounds.PING.sound())
            println(Material.STONE.namespace())
            println(Material.STONE.namespace().namespace())
            NotificationCenter.send(Notification("<green>Yoo".toMini(), FrameType.GOAL, Material.STONE), s)
        }
        val strArgument = ArgumentType.StringArray("Message")
        strArgument.setCallback { s, ex ->
            s.send("<red>The message ${ex.input}")
        }
        val strArgument2 = ArgumentType.StringArray("Material")
        strArgument2.setCallback { s, ex ->
            s.send("<red>The material ${ex.input} is invalid")
        }
        addSyntax({ s, ctx ->
            if (s !is Player) return@addSyntax
            s.playSound(Sounds.PING.sound())
            NotificationCenter.send(Notification(ctx.get(strArgument).toMini(), FrameType.GOAL, Material.fromNamespaceId("minecraft:${ctx.get(strArgument2)}") ?: Material.STONE), s)
            addSyntax({ s, ctx ->
                if (s !is Player) return@addSyntax
                s.playSound(Sounds.PING.sound())
                NotificationCenter.send(Notification(ctx.get(strArgument).toMini(), FrameType.GOAL, Material.STONE), s)
            }, strArgument)
        }, strArgument2)
    }
}