package org.runestar.client.updater.mapper.std.classes

import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type.INT_TYPE
import org.objectweb.asm.Type.VOID_TYPE
import org.runestar.client.updater.mapper.*
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.annotations.MethodParameters
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.extensions.type
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Instruction2
import org.runestar.client.updater.mapper.tree.Method2

@DependsOn(DualNode::class)
class SpotAnimationDefinition : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.superType == type<DualNode>() }
            .and { it.interfaces.isEmpty() }
            .and { it.instanceFields.count { it.type == ShortArray::class.type } == 4 }
            .and { it.instanceFields.count { it.type == INT_TYPE } >= 8 }
            .and { it.instanceFields.all { it.type == INT_TYPE || it.type == ShortArray::class.type } }

    @MethodParameters("buffer", "n")
    class decode0 : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.instructions.any { it.opcode == BIPUSH && it.intOperand == 40 } }
    }

    @MethodParameters("buffer")
    @DependsOn(decode0::class)
    class decode : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it != method<decode0>() }
    }

    @DependsOn(Client.getSpotAnimationDefinition::class)
    class id : OrderMapper.InMethod.Field(Client.getSpotAnimationDefinition::class, 0) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE && it.fieldOwner == type<SpotAnimationDefinition>() }
    }

    @DependsOn(Model::class)
    class getModel : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == type<Model>() }
    }

    // in degrees, 0, 180, 270
    @DependsOn(SpotAnimationDefinition.getModel::class)
    class orientation : OrderMapper.InMethod.Field(SpotAnimationDefinition.getModel::class, -1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == GETFIELD && it.fieldType == INT_TYPE }
    }

    @DependsOn(decode0::class)
    class resizeh : UniqueMapper.InMethod.Field(decode0::class) {
        override val predicate = predicateOf<Instruction2> { it.opcode == ICONST_4 }
                .nextWithin(10) { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    @DependsOn(decode0::class)
    class resizev : UniqueMapper.InMethod.Field(decode0::class) {
        override val predicate = predicateOf<Instruction2> { it.opcode == ICONST_5 }
                .nextWithin(10) { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class sequence : OrderMapper.InConstructor.Field(SpotAnimationDefinition::class, 0) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    @DependsOn(getModel::class)
    class recol_s : OrderMapper.InMethod.Field(getModel::class, 0) {
        override val predicate = predicateOf<Instruction2> { it.opcode == SALOAD }
                .prevIn(2) { it.opcode == GETFIELD && it.fieldType == ShortArray::class.type }
    }

    @DependsOn(getModel::class)
    class recol_d : OrderMapper.InMethod.Field(getModel::class, 1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == SALOAD }
                .prevIn(2) { it.opcode == GETFIELD && it.fieldType == ShortArray::class.type }
    }

    @DependsOn(getModel::class)
    class retex_s : OrderMapper.InMethod.Field(getModel::class, 2) {
        override val predicate = predicateOf<Instruction2> { it.opcode == SALOAD }
                .prevIn(2) { it.opcode == GETFIELD && it.fieldType == ShortArray::class.type }
    }

    @DependsOn(getModel::class)
    class retex_d : OrderMapper.InMethod.Field(getModel::class, 3) {
        override val predicate = predicateOf<Instruction2> { it.opcode == SALOAD }
                .prevIn(2) { it.opcode == GETFIELD && it.fieldType == ShortArray::class.type }
    }

    @DependsOn(decode0::class)
    class model : OrderMapper.InMethod.Field(decode0::class, 0) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }
}