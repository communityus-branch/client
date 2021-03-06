package org.runestar.client.updater.mapper.std.classes

import org.objectweb.asm.Opcodes.PUTFIELD
import org.objectweb.asm.Type.INT_TYPE
import org.objectweb.asm.Type.LONG_TYPE
import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.OrderMapper
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.extensions.type
import org.runestar.client.updater.mapper.std.MethodPutField
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Instruction2

@DependsOn(Entity::class)
class BoundaryObject : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.superType == Any::class.type }
            .and { it.interfaces.isEmpty() }
            .and { it.instanceFields.count { it.type == INT_TYPE } == 6 }
            .and { it.instanceFields.count { it.type == type<Entity>() } == 2 }
            .and { it.instanceFields.size == 9 }
            .and { it.instanceMethods.isEmpty() }

    @DependsOn(Scene.newBoundaryObject::class)
    class tag : MethodPutField(Scene.newBoundaryObject::class, 0, LONG_TYPE)

    @DependsOn(Scene.newBoundaryObject::class)
    class flags : MethodPutField(Scene.newBoundaryObject::class, 0, INT_TYPE)

    @DependsOn(Scene.newBoundaryObject::class)
    class x : MethodPutField(Scene.newBoundaryObject::class, 1, INT_TYPE)

    @DependsOn(Scene.newBoundaryObject::class)
    class y : MethodPutField(Scene.newBoundaryObject::class, 2, INT_TYPE)

    @DependsOn(Scene.newBoundaryObject::class)
    class tileHeight : MethodPutField(Scene.newBoundaryObject::class, 3, INT_TYPE)

    @DependsOn(Scene.newBoundaryObject::class, Entity::class)
    class entity1 : OrderMapper.InMethod.Field(Scene.newBoundaryObject::class, 0) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == type<Entity>() }
    }

    @DependsOn(Scene.newBoundaryObject::class, Entity::class)
    class entity2 : OrderMapper.InMethod.Field(Scene.newBoundaryObject::class, 1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == type<Entity>() }
    }

    @DependsOn(Scene.newBoundaryObject::class)
    class orientationA : MethodPutField(Scene.newBoundaryObject::class, 4, INT_TYPE)

    @DependsOn(Scene.newBoundaryObject::class)
    class orientationB : MethodPutField(Scene.newBoundaryObject::class, 5, INT_TYPE)
}