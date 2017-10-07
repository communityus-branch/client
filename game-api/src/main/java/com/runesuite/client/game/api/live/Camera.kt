package com.runesuite.client.game.api.live

import com.runesuite.client.game.api.Angle
import com.runesuite.client.game.raw.Client.accessor

interface Camera {

    val x: Int

    val y: Int

    val z: Int

    val pitch: com.runesuite.client.game.api.Angle

    val yaw: com.runesuite.client.game.api.Angle

    object Live : Camera {

        override val x get() = accessor.cameraX

        override val y get() = accessor.cameraY

        override val z get() = accessor.cameraZ

        override val pitch get() = com.runesuite.client.game.api.Angle(accessor.cameraPitch)

        override val yaw get() = com.runesuite.client.game.api.Angle(accessor.cameraYaw)

        override fun toString(): String {
            return "Camera.Live(x=$x, y=$y, z=$z, pitch=$pitch, yaw=$yaw)"
        }
    }

    fun copyOf(): Copy = Copy(x, y, z, pitch, yaw)

    data class Copy(
            override val x: Int,
            override val y: Int,
            override val z: Int,
            override val pitch: com.runesuite.client.game.api.Angle,
            override val yaw: com.runesuite.client.game.api.Angle
    ) : Camera
}