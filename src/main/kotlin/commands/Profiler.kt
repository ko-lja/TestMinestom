package lu.kolja.commands

import net.minestom.server.command.builder.Command

class Profiler: Command("profiler") {

    init {

        setDefaultExecutor { s, ctx ->
            s.sendMessage("<gray>" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "</gray>")
        }
    }
}