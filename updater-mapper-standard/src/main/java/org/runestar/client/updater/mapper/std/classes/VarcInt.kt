package org.runestar.client.updater.mapper.std.classes

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type.*
import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.OrderMapper
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.extensions.Predicate
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Field2
import org.runestar.client.updater.mapper.tree.Instruction2

@DependsOn(Varcs::class)
class VarcInt : OrderMapper.InConstructor.Class(Varcs::class, 0, 2) {

    override val predicate = predicateOf<Instruction2> { it.opcode == Opcodes.GETFIELD && it.fieldType == BOOLEAN_TYPE }

    override fun resolve(instruction: Instruction2): Class2 {
        return instruction.jar[instruction.fieldOwner]
    }

    class persist : IdentityMapper.InstanceField() {
        override val predicate = predicateOf<Field2> { it.type == BOOLEAN_TYPE }
    }
}