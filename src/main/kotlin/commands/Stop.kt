package lu.kolja.commands

import lu.kolja.TestMinestom
import lu.kolja.util.extensions.toMini
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import kotlin.system.exitProcess

class Stop: Command("stop") {
    init {
        setDefaultExecutor { s, ctx ->
            if (s.hasPermission("minetest.stop")) {
                s.sendMessage("<red>Stopping server..".toMini())
                TestMinestom.instanceContainer().saveInstance()
                TestMinestom.instanceContainer().saveChunksToStorage()
                TestMinestom.connectionManager().onlinePlayers.forEach {
                    it.kick("<yellow>Stopping <blue>server, <black>Sorry".toMini())
                }.run {
                    MinecraftServer.stopCleanly()
                    exitProcess(0)
                }
            } else {
                s.sendMessage("<dark_red>No permission!!".toMini())
            }
        }
    }
}