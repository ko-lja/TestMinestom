package lu.kolja.commands

import lu.kolja.TestMinestom
import net.minestom.server.command.builder.Command

class Reload:Command("reload") {

    init {
        setDefaultExecutor{ s, ctx ->
            TestMinestom.config().reloadConfig()
        }
    }
}