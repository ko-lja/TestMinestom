package lu.kolja.util.extensions

import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.ItemEntity
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerBlockBreakEvent
import net.minestom.server.instance.block.Block
import net.minestom.server.item.ItemStack
import java.time.Duration
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

fun Block.toItem(amount: Int): ItemStack {
    return ItemStack.of(registry().material()!!, amount)
}
fun PlayerBlockBreakEvent.drop() {
    val player = player
    val item = block.toItem(1)
    val blockPos = blockPosition
    val randomFactor = 0.2

    // Create item entity with pickup delay
    val itemEntity = ItemEntity(item).apply {
        setPickupDelay(Duration.ofMillis(500))
    }

    // Calculate spawn position based on block face
    val spawnPos = Vec(
        blockPos.x() + 0.5 + randomOffset(randomFactor),
        blockPos.y() + 0.5,
        blockPos.z() + 0.5 + randomOffset(randomFactor)
    )

    // Spawn the item at calculated position
    itemEntity.setInstance(player.instance, spawnPos)

    // Add a small random velocity for natural movement
    val randomVelocity = Vec(
        randomOffset(0.05),
        0.0,  // Slight upward bias
        randomOffset(0.05)
    )
    itemEntity.velocity = randomVelocity
}

private fun randomOffset(factor: Double): Double {
    return Random.nextDouble(-factor, factor)
}
fun Player.drop(item: ItemStack) {
    // Create item entity with shorter pickup delay for player drops
    val throwStrength = 0.3
    val randomFactor = 0.1
    val itemEntity = ItemEntity(item).apply {
        setPickupDelay(Duration.ofSeconds(2L))
    }

    // Calculate spawn position slightly in front and above player's eyes
    val eyePosition = position.add(0.0, eyeHeight.toDouble(), 0.0)
    val direction = position.direction()

    // Calculate spawn offset based on player's look direction
    val spawnOffset = Vec(
        -sin(Math.toRadians(position.yaw.toDouble())) * 0.5,
        0.3, // Slightly above eye level
        cos(Math.toRadians(position.yaw.toDouble())) * 0.5
    )

    // Add randomization to spawn position
    val spawnPos = eyePosition.add(
        spawnOffset.x() + randomOffset(randomFactor),
        spawnOffset.y() + randomOffset(randomFactor),
        spawnOffset.z() + randomOffset(randomFactor)
    )

    // Spawn the item
    itemEntity.setInstance(instance, spawnPos)

    // Calculate velocity based on player's look direction and throw strength
    val baseVelocity = direction.mul(throwStrength)

    // Add slight upward arc and randomization
    val velocity = Vec(
        baseVelocity.x() + randomOffset(0.05),
        baseVelocity.y() + randomOffset(0.05), // Add upward arc
        baseVelocity.z() + randomOffset(0.05)
    )

    itemEntity.velocity = velocity
}