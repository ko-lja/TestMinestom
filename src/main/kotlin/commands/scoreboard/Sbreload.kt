package lu.kolja.commands.scoreboard

import lu.kolja.TestMinestom
import lu.kolja.util.extensions.toMini
import net.minestom.server.command.builder.Command

class Sbreload: Command("sbreload") {

    init {
        setDefaultExecutor{ s, ctx ->
            /*if (!(s.hasPermission("sb.reload"))) {
                s.sendMessage("<red>No permission!".toMini())
                return@setDefaultExecutor
            }*/
            s.sendMessage("<green>Reloading scoreboard!".toMini())
            TestMinestom.scoreboardCommand().reload()
        }
    }
}