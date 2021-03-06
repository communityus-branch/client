package org.runestar.client.game.api

import org.runestar.client.game.raw.access.XIgnored

class Ignored(override val accessor: XIgnored) : User(accessor) {

    override fun toString(): String {
        return "Ignored(username=$username, previousUsername=$previousUsername)"
    }
}