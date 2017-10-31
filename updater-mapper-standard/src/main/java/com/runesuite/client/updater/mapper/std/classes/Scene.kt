package com.runesuite.client.updater.mapper.std.classes

import com.hunterwb.kxtra.lang.list.startsWith
import com.runesuite.mapper.IdentityMapper
import com.runesuite.mapper.OrderMapper
import com.runesuite.mapper.annotations.DependsOn
import com.runesuite.mapper.annotations.MethodParameters
import com.runesuite.mapper.extensions.*
import com.runesuite.mapper.tree.Class2
import com.runesuite.mapper.tree.Field2
import com.runesuite.mapper.tree.Instruction2
import com.runesuite.mapper.tree.Method2
import org.objectweb.asm.Opcodes.PUTFIELD
import org.objectweb.asm.Type.*

@DependsOn(GameObject::class)
class Scene : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.superType == Any::class.type }
            .and { it.interfaces.isEmpty() }
            .and { it.instanceFields.any { it.type == type<GameObject>().withDimensions(1) } }

    @DependsOn(GameObject::class)
    class gameObjects : IdentityMapper.InstanceField() {
        override val predicate = predicateOf<Field2> { it.type == type<GameObject>().withDimensions(1) }
    }

    class tiles : IdentityMapper.InstanceField() {
        override val predicate = predicateOf<Field2> { it.type.arrayDimensions == 3 && it.type.baseType in it.jar }
    }

    @DependsOn(Entity::class)
    class newGameObject : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == BOOLEAN_TYPE }
                .and { it.arguments.startsWith(INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE,
                        INT_TYPE, INT_TYPE, INT_TYPE, type<Entity>(), INT_TYPE, BOOLEAN_TYPE, INT_TYPE, INT_TYPE) }
                .and { it.arguments.size in 13..14 }
    }

    @DependsOn(Entity::class)
    class newFloorDecoration : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE,
                        type<Entity>(), INT_TYPE, INT_TYPE) }
                .and { it.arguments.size in 7..8 }
    }

    @DependsOn(Entity::class)
    class newWallDecoration : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE,
                        type<Entity>(), type<Entity>(), INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE) }
                .and { it.arguments.size in 12..13 }
    }

    @MethodParameters("plane", "x", "y", "int0", "bottom", "int1", "middle", "top")
    @DependsOn(Entity::class)
    class newGroundItemPile : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE,
                        type<Entity>(), INT_TYPE, type<Entity>(), type<Entity>()) }
                .and { it.arguments.size in 8..9 }
    }

    @MethodParameters("plane", "x", "y")
    @DependsOn(Tile.groundItemPile::class)
    class removeGroundItemPile : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(INT_TYPE, INT_TYPE, INT_TYPE) }
                .and { it.arguments.size in 3..4 }
                .and { it.instructions.any { it.opcode == PUTFIELD && it.fieldId == field<Tile.groundItemPile>().id } }
    }

    @MethodParameters("plane", "x", "y")
    @DependsOn(Tile.floorDecoration::class)
    class removeFloorDecoration : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(INT_TYPE, INT_TYPE, INT_TYPE) }
                .and { it.arguments.size in 3..4 }
                .and { it.instructions.any { it.opcode == PUTFIELD && it.fieldId == field<Tile.floorDecoration>().id } }
    }

    @MethodParameters("plane", "x", "y")
    @DependsOn(Tile.wallDecoration::class)
    class removeWallDecoration : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(INT_TYPE, INT_TYPE, INT_TYPE) }
                .and { it.arguments.size in 3..4 }
                .and { it.instructions.any { it.opcode == PUTFIELD && it.fieldId == field<Tile.wallDecoration>().id } }
    }

    @MethodParameters("plane", "x", "y")
    @DependsOn(Tile.boundaryObject::class)
    class removeBoundaryObject : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(INT_TYPE, INT_TYPE, INT_TYPE) }
                .and { it.arguments.size in 3..4 }
                .and { it.instructions.any { it.opcode == PUTFIELD && it.fieldId == field<Tile.boundaryObject>().id } }
    }

    @DependsOn(Entity::class)
    class newBoundaryObject : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.startsWith(INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE,
                        type<Entity>(), type<Entity>(), INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE) }
                .and { it.arguments.size in 10..11 }
    }

    @MethodParameters("plane", "x", "y")
    @DependsOn(BoundaryObject::class)
    class getBoundaryObject : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == type<BoundaryObject>() }
    }

    @MethodParameters("plane", "x", "y")
    @DependsOn(WallDecoration::class)
    class getWallDecoration : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == type<WallDecoration>() }
    }

    @MethodParameters("plane", "x", "y")
    @DependsOn(FloorDecoration::class)
    class getFloorDecoration : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == type<FloorDecoration>() }
    }

    class planes : OrderMapper.InConstructor.Field(Scene::class, -3) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class xSize : OrderMapper.InConstructor.Field(Scene::class, -2) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class ySize : OrderMapper.InConstructor.Field(Scene::class, -1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class tileHeights : OrderMapper.InConstructor.Field(Scene::class, -1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE.withDimensions(3) }
    }

    class method1 : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == VOID_TYPE }
                .and { it.arguments.size in 6..7 }
                .and { it.arguments.startsWith(INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE) }
    }

    @MethodParameters("plane", "x", "y", "id")
    class getObjectFlags : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == INT_TYPE }
                .and { it.arguments.size == 4 }
                .and { it.arguments.startsWith(INT_TYPE, INT_TYPE, INT_TYPE, INT_TYPE) }
    }
}