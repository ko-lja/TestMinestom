package lu.kolja.enums

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound


enum class Sounds(val sound: Sound) {

    PING(Sound.sound(Key.key("entity.experience_orb.pickup"), Sound.Source.MASTER, 1f, 1f));

    fun sound(): Sound {
        return sound
    }
}