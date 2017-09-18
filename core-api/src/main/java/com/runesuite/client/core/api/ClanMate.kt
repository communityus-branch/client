package com.runesuite.client.core.api

import com.runesuite.client.core.api.live.Worlds
import com.runesuite.client.core.raw.Wrapper
import com.runesuite.client.core.raw.access.XClanMate

class ClanMate(override val accessor: XClanMate) : Wrapper() {

    val name: String get() = accessor.name

    val worldId get() = accessor.world

    val world get() = Worlds[accessor.world]

    val rank get() = checkNotNull(Rank.LOOKUP[accessor.rank]) { accessor.rank }

    override fun toString(): String {
        return "ClanMate(name=$name, worldId=$worldId, rank=$rank)"
    }

    enum class Rank(val id: Byte) {

        NONE(-1),
        FRIEND(0),
        RECRUIT(1),
        CORPORAL(2),
        SERGEANT(3),
        LIEUTENANT(4),
        CAPTAIN(5),
        GENERAL(6),
        OWNER(7);

        companion object {
            val LOOKUP = values().associateBy { it.id }
        }
    }
}