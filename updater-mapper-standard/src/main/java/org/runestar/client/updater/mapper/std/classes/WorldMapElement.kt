package org.runestar.client.updater.mapper.std.classes

import org.runestar.client.common.startsWith
import org.objectweb.asm.Opcodes.BIPUSH
import org.objectweb.asm.Opcodes.PUTFIELD
import org.objectweb.asm.Type.*
import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.OrderMapper
import org.runestar.client.updater.mapper.UniqueMapper
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.annotations.MethodParameters
import org.runestar.client.updater.mapper.extensions.Predicate
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.extensions.type
import org.runestar.client.updater.mapper.nextWithin
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Field2
import org.runestar.client.updater.mapper.tree.Instruction2
import org.runestar.client.updater.mapper.tree.Method2

@DependsOn(DualNode::class)
class WorldMapElement : IdentityMapper.Class() {

    override val predicate = predicateOf<Class2> { it.superType == type<DualNode>() }
            .and { it.instanceFields.count { it.type == String::class.type } == 2 }

    class strings : IdentityMapper.InstanceField() {
        override val predicate = predicateOf<Field2> { it.type == Array<String>::class.type }
    }

    @MethodParameters("buffer")
    @DependsOn(Buffer::class)
    class decode : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(type<Buffer>()) }
                .and { it.instructions.none { it.opcode == BIPUSH && it.intOperand == 16 } }
    }

    @MethodParameters("buffer", "opcode")
    @DependsOn(Buffer::class)
    class decode0 : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(type<Buffer>()) }
                .and { it.instructions.any { it.opcode == BIPUSH && it.intOperand == 16 } }
    }

    @MethodParameters("id")
    @DependsOn(Sprite::class)
    class getSprite0 : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == type<Sprite>() }
                .and { it.arguments.startsWith(INT_TYPE) }
    }

    @MethodParameters("b")
    @DependsOn(Sprite::class)
    class getSprite : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == type<Sprite>() }
                .and { it.arguments.startsWith(BOOLEAN_TYPE) }
    }

    @DependsOn(decode0::class)
    class string1 : UniqueMapper.InMethod.Field(decode0::class) {
        override val predicate = predicateOf<Instruction2> { it.opcode == BIPUSH && it.intOperand == 17 }
                .nextWithin(7) { it.opcode == PUTFIELD && it.fieldType == String::class.type }
    }

//    @DependsOn(decode0::class)
//    class name : UniqueMapper.InMethod.Field(decode0::class) {
//        override val predicate = predicateOf<Instruction2> { it.opcode == ICONST_3 }
//                .nextWithin(7) { it.opcode == PUTFIELD && it.fieldType == String::class.type }
//    }

    class sprite1 : OrderMapper.InConstructor.Field(WorldMapElement::class, 0) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class sprite2 : OrderMapper.InConstructor.Field(WorldMapElement::class, 1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class textSize : OrderMapper.InConstructor.Field(WorldMapElement::class, 2) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class category : OrderMapper.InConstructor.Field(WorldMapElement::class, 7) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }
}