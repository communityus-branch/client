package org.runestar.client.updater.mapper.std.classes

import org.runestar.client.common.startsWith
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type.INT_TYPE
import org.objectweb.asm.Type.VOID_TYPE
import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.OrderMapper
import org.runestar.client.updater.mapper.UniqueMapper
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.annotations.MethodParameters
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.extensions.type
import org.runestar.client.updater.mapper.std.ConstructorPutField
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Instruction2
import org.runestar.client.updater.mapper.tree.Method2

@DependsOn(DualNode::class)
class SequenceDefinition : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.superType == type<DualNode>() }
            .and { it.instanceFields.count { it.type == IntArray::class.type } == 5 }

    @DependsOn(Buffer::class)
    class decode : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.instructions.any { it.opcode == GOTO } }
                .and { it.instructions.none { it.opcode == BIPUSH && it.intOperand == 13 } }
                .and { it.arguments.startsWith(type<Buffer>()) }
    }

    class decode0 : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.instructions.any { it.opcode == GOTO } }
                .and { it.instructions.any { it.opcode == BIPUSH && it.intOperand == 13 } }
    }

    class shield : OrderMapper.InConstructor.Field(SequenceDefinition::class, 2) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class weapon : OrderMapper.InConstructor.Field(SequenceDefinition::class, 3) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    @MethodParameters()
    class postDecode : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.size in 0..1 }
                .and { it.instructions.none { it.isMethod } }
    }

    @DependsOn(decode0::class)
    class frameLengths : OrderMapper.InMethod.Field(decode0::class, 0) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == IntArray::class.type }
    }

    @DependsOn(decode0::class)
    class frameIds : OrderMapper.InMethod.Field(decode0::class, 1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == IntArray::class.type }
    }

    @MethodParameters("model", "frame")
    @DependsOn(SpotAnimationDefinition.getModel::class)
    class animateSpotAnimation : UniqueMapper.InMethod.Method(SpotAnimationDefinition.getModel::class) {
        override val predicate = predicateOf<Instruction2> { it.isMethod && it.methodOwner == type<SequenceDefinition>() }
    }

    @MethodParameters("model", "frame", "orientation")
    @DependsOn(ObjectDefinition.getModelDynamic::class)
    class animateObject : UniqueMapper.InMethod.Method(ObjectDefinition.getModelDynamic::class) {
        override val predicate = predicateOf<Instruction2> { it.isMethod && it.methodOwner == type<SequenceDefinition>() }
    }

    @MethodParameters("model", "frame", "sequence", "sequenceFrame")
    @DependsOn(Model::class)
    class animateSequence2 : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == type<Model>() }
                .and { it.arguments.size in 4..5 }
                .and { it.arguments.startsWith(type<Model>(), INT_TYPE, type<SequenceDefinition>(), INT_TYPE) }
    }

    @MethodParameters("model", "frame")
    @DependsOn(animateSequence2::class)
    class animateSequence : UniqueMapper.InMethod.Method(animateSequence2::class) {
        override val predicate = predicateOf<Instruction2> { it.isMethod && it.methodOwner == type<SequenceDefinition>() }
    }

    @MethodParameters("model", "frame")
    @DependsOn(Component.getModel::class)
    class animateComponent : UniqueMapper.InMethod.Method(Component.getModel::class) {
        override val predicate = predicateOf<Instruction2> { it.isMethod && it.methodOwner == type<SequenceDefinition>() }
    }

    @DependsOn(animateComponent::class)
    class frameIds2 : OrderMapper.InMethod.Field(animateComponent::class, 1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == GETFIELD && it.fieldType == IntArray::class.type }
    }

    class frameCount : ConstructorPutField(SequenceDefinition::class, 0, INT_TYPE)
}