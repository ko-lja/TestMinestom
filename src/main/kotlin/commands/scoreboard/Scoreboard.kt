package lu.kolja.commands.scoreboard

import lu.kolja.util.extensions.toMini
import lu.kolja.util.manager.ConfigManager
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player
import net.minestom.server.permission.Permission
import net.minestom.server.scoreboard.Sidebar

class Scoreboard(private val configManager: ConfigManager): Command("scoreboard") {
    private var scoreboard: Sidebar
    private var title: String
    private var content: List<String>
    private val viewPerm = Permission("sb.view")
    init {
        title = configManager.getConfig().scoreboard.title
        content = configManager.getConfig().scoreboard.text
        scoreboard = Sidebar(title.toMini())
        content.forEachIndexed { index, it ->
            var line = content.size
            scoreboard.createLine(Sidebar.ScoreboardLine(
                "line_val_$index",
                it.toMini(),
                line
            ))
            line -= 1
        }
        setDefaultExecutor { s, ctx ->
            if (s !is Player) return@setDefaultExecutor
            if (s.hasPermission(viewPerm)) {
                s.removePermission(viewPerm)
                scoreboard.removeViewer(s)
            } else {
                s.addPermission(viewPerm)
                scoreboard.addViewer(s)
            }
        }
    }
    fun reload() {
        configManager.reloadConfig()
        val sbViewers: Set<Player> = scoreboard.viewers.toSet()
        scoreboard.viewers.forEach {
            scoreboard.removeViewer(it)
        }
        title = configManager.getConfig().scoreboard.title
        content = configManager.getConfig().scoreboard.text
        scoreboard.setTitle(title.toMini())
        scoreboard.lines.forEach {
            scoreboard.removeLine(it.id)
        }
        println(title)
        content.forEachIndexed { index, it ->
            var line = content.size
            println(it)
            scoreboard.createLine(Sidebar.ScoreboardLine(
                "line_val_$index",
                it.toMini(),
                line
            ))
            line -= 1
        }
        sbViewers.forEach { scoreboard.addViewer(it) }
    }
}