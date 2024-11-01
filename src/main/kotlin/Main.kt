package lu.kolja

import lu.kolja.commands.DeOperator
import lu.kolja.commands.Notify
import lu.kolja.commands.Operator
import lu.kolja.commands.Reload
import lu.kolja.commands.Stop
import lu.kolja.commands.scoreboard.Sbreload
import lu.kolja.commands.scoreboard.Scoreboard
import lu.kolja.util.extensions.drop
import lu.kolja.util.extensions.toMini
import lu.kolja.util.manager.ConfigManager
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player
import net.minestom.server.event.item.ItemDropEvent
import net.minestom.server.event.item.PickupItemEvent
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerBlockBreakEvent
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.extras.MojangAuth
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.InstanceManager
import net.minestom.server.instance.block.Block
import net.minestom.server.network.ConnectionManager

private lateinit var server: MinecraftServer
private lateinit var instanceManager: InstanceManager
private lateinit var instanceContainer: InstanceContainer
private lateinit var connectionManager: ConnectionManager
private lateinit var miniMessage: MiniMessage
private lateinit var configManager: ConfigManager
private lateinit var scoreboardCommand: Scoreboard

object TestMinestom {
    fun server(): MinecraftServer {
        return server
    }
    fun instanceManager(): InstanceManager {
        return instanceManager
    }
    fun instanceContainer(): InstanceContainer {
        return instanceContainer
    }
    fun connectionManager(): ConnectionManager {
        return connectionManager
    }
    fun miniMessage(): MiniMessage {
        return miniMessage
    }
    fun config(): ConfigManager {
        return configManager
    }
    fun scoreboardCommand(): Scoreboard {
        return scoreboardCommand
    }
}

fun main() {
    init()

    instanceContainer.setGenerator {unit ->
        unit.modifier().fillHeight(0, 44, Block.STONE_BRICKS)
        unit.modifier().fillHeight(44, 46, Block.DIRT)
    }

    val globalEventHandler = MinecraftServer.getGlobalEventHandler()

    globalEventHandler.addListener(PlayerChatEvent::class.java) { e ->
        e.isCancelled = true
        connectionManager.onlinePlayers.forEach {
            it.sendMessage(e.message.toMini())
        }
        e.player.sendMessage("Your message was <green>${e.message}".toMini())
        println("[${e.player.username}] : ${e.message}")
    }
    globalEventHandler.addListener(AsyncPlayerConfigurationEvent::class.java) { e ->
        val player = e.player
        e.spawningInstance = instanceContainer
        player.respawnPoint = Pos(0.0, 47.0, 0.0)
    }
    globalEventHandler.addListener(PlayerBlockBreakEvent::class.java) { e ->
        e.drop()
    }
    globalEventHandler.addListener(PickupItemEvent::class.java) { e ->
        if (e.entity is Player) {
            val p = e.entity as Player
            if (p.canPickupItem()) {
                p.inventory.addItemStack(e.itemEntity.itemStack)
            }
        }
    }
    globalEventHandler.addListener(ItemDropEvent::class.java) { e ->
        val p = e.player
        val item = e.itemStack
        p.gameMode = GameMode.CREATIVE
        p.drop(item)
    }
    MinecraftServer.getCommandManager().run {
        register(Operator())
        register(Stop())
        register(DeOperator())
        register(Reload())
        register(scoreboardCommand)
        register(Sbreload())
        register(Notify())
    }
    server.start("0.0.0.0", 25565)
}
private fun init() {
    server = MinecraftServer.init()
    MojangAuth.init()
    instanceManager = MinecraftServer.getInstanceManager()
    instanceContainer = instanceManager.createInstanceContainer()
    connectionManager = MinecraftServer.getConnectionManager()
    miniMessage = MiniMessage.miniMessage()
    configManager = ConfigManager("TestMinestom")
    scoreboardCommand = Scoreboard(configManager)
}