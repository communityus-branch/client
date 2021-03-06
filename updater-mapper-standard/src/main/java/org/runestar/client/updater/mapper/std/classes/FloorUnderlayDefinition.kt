package org.runestar.client.updater.mapper.std.classes

import org.runestar.client.common.startsWith
import org.runestar.client.updater.mapper.*
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.annotations.MethodParameters
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Instruction2
import org.runestar.client.updater.mapper.tree.Method2
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type.*

@DependsOn(DualNode::class)
class FloorUnderlayDefinition : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.superType == type<DualNode>() }
            .and { it.instanceFields.size == 5 }
            .and { it.instanceFields.all { it.type == INT_TYPE } }

    @DependsOn(Buffer::class)
    class decode : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(type<Buffer>()) }
                .and { it.instructions.none { it.opcode == PUTFIELD } }
    }

    @DependsOn(Buffer::class)
    class decode0 : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(type<Buffer>()) }
                .and { it.instructions.any { it.opcode == PUTFIELD } }
    }

    class rgb : UniqueMapper.InConstructor.Field(FloorUnderlayDefinition::class) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD }
    }

    @MethodParameters()
    class postDecode : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.size in 0..1 }
                .and { it.instructions.any { it.isMethod } }
    }

    @MethodParameters("rgb")
    @DependsOn(postDecode::class)
    class setHsl : UniqueMapper.InMethod.Method(postDecode::class) {
        override val predicate = predicateOf<Instruction2> { it.isMethod }
    }

    @DependsOn(setHsl::class)
    class saturation : OrderMapper.InMethod.Field(setHsl::class, 0) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD }
    }

    @DependsOn(setHsl::class)
    class lightness : OrderMapper.InMethod.Field(setHsl::class, 1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD }
    }

    @DependsOn(setHsl::class)
    class hue : UniqueMapper.InMethod.Field(setHsl::class) {
        override val predicate = predicateOf<Instruction2> { it.opcode == RETURN }
                .prev { it.opcode == PUTFIELD }
    }

    @DependsOn(setHsl::class)
    class hueMultiplier : UniqueMapper.InMethod.Field(setHsl::class) {
        override val predicate = predicateOf<Instruction2> { it.opcode == RETURN }
                .prevWithin(10) { it.opcode == GETFIELD }
    }
}