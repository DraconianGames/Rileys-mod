package net.riley.riley_mod.worldgen.portal;

import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.riley.riley_mod.block.RileyModBlocks;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public class RileyModPortalShape {
    private static final int MIN_WIDTH = 2;
    public static final int MAX_WIDTH = 21;
    private static final int MIN_HEIGHT = 3;
    public static final int MAX_HEIGHT = 21;
    private static final BlockBehaviour.StatePredicate FRAME = (state, level, pos) ->
            state.is(RileyModBlocks.ACTIVACTED_FUNTIUM.get());

private final LevelAccessor level;
private final Direction.Axis axis;
private final Direction rightDir;
private int numPortalBlocks;
@Nullable
private BlockPos bottomLeft;
private int height;
private final int width;

    public static Optional<RileyModPortalShape> findEmptyPortalShape(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
        return findPortalShape(level, pos, (shape) -> shape.isValid() && shape.numPortalBlocks == 0, axis);
    }

    public static Optional<RileyModPortalShape> findPortalShape(LevelAccessor level, BlockPos pos, Predicate<RileyModPortalShape> predicate, Direction.Axis axis) {
        Optional<RileyModPortalShape> optional = Optional.of(new RileyModPortalShape(level, pos, axis)).filter(predicate);
        if (optional.isPresent()) {
            return optional;
        } else {
            Direction.Axis otherAxis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            return Optional.of(new RileyModPortalShape(level, pos, otherAxis)).filter(predicate);
        }
    }

    public RileyModPortalShape(LevelAccessor level, BlockPos bottomLeft, Direction.Axis axis) {
        this.level = level;
        this.axis = axis;
        this.rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        this.bottomLeft = this.calculateBottomLeft(bottomLeft);
        if (this.bottomLeft == null) {
            this.bottomLeft = bottomLeft;
            this.width = 1;
            this.height = 1;
        } else {
            this.width = this.calculateWidth();
            if (this.width > 0) {
                this.height = this.calculateHeight();
            }
        }
    }

    @Nullable
    private BlockPos calculateBottomLeft(BlockPos pos) {
        for(int i = Math.max(this.level.getMinBuildHeight(), pos.getY() - 21); pos.getY() > i && isEmpty(this.level.getBlockState(pos.below())); pos = pos.below()) {
        }

        Direction direction = this.rightDir.getOpposite();
        int distance = this.getDistanceUntilEdgeAboveFrame(pos, direction) - 1;
        return distance < 0 ? null : pos.relative(direction, distance);
    }

    private int calculateWidth() {
        int width = this.getDistanceUntilEdgeAboveFrame(this.bottomLeft, this.rightDir);
        return width >= MIN_WIDTH && width <= MAX_WIDTH ? width : 0;
    }
    private int getDistanceUntilEdgeAboveFrame(BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for(int i = 0; i <= MAX_WIDTH; ++i) {
            mutablePos.set(pos).move(direction, i);
            BlockState state = this.level.getBlockState(mutablePos);
            if (!isEmpty(state)) {
                if (FRAME.test(state, this.level, mutablePos)) {
                    return i;
                }
                break;
            }

            BlockState stateBelow = this.level.getBlockState(mutablePos.move(Direction.DOWN));
            if (!FRAME.test(stateBelow, this.level, mutablePos)) {
                break;
            }
        }

        return 0;
    }

    private int calculateHeight() {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int h = this.getDistanceUntilTop(mutablePos);
        return h >= MIN_HEIGHT && h <= MAX_HEIGHT && this.hasTopFrame(mutablePos, h) ? h : 0;
    }

    private boolean hasTopFrame(BlockPos.MutableBlockPos pos, int height) {
        for(int i = 0; i < this.width; ++i) {
            BlockPos.MutableBlockPos checkPos = pos.set(this.bottomLeft).move(Direction.UP, height).move(this.rightDir, i);
            if (!FRAME.test(this.level.getBlockState(checkPos), this.level, checkPos)) {
                return false;
            }
        }
        return true;
    }

    private int getDistanceUntilTop(BlockPos.MutableBlockPos pos) {
        for(int i = 0; i < MAX_HEIGHT; ++i) {
            pos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
            if (!FRAME.test(this.level.getBlockState(pos), this.level, pos)) {
                return i;
            }

            pos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
            if (!FRAME.test(this.level.getBlockState(pos), this.level, pos)) {
                return i;
            }

            for(int j = 0; j < this.width; ++j) {
                pos.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
                BlockState state = this.level.getBlockState(pos);
                if (!isEmpty(state)) {
                    return i;
                }

                if (state.is(RileyModBlocks.ABYSS_PORTAL.get())) {
                    ++this.numPortalBlocks;
                }
            }
        }

        return MAX_HEIGHT;
    }

    private boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(RileyModBlocks.ABYSS_PORTAL.get());
    }

    public boolean isValid() {
        return this.bottomLeft != null && this.width >= MIN_WIDTH && this.width <= MAX_WIDTH && this.height >= MIN_HEIGHT && this.height <= MAX_HEIGHT;
    }

    public void createPortalBlocks() {
        BlockState state = RileyModBlocks.ABYSS_PORTAL.get().defaultBlockState().setValue(NetherPortalBlock.AXIS, this.axis);
        BlockPos.betweenClosed(this.bottomLeft, this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach((pos) -> {
            this.level.setBlock(pos, state, 18);
        });
    }

    public boolean isComplete() {
        return this.isValid() && this.numPortalBlocks == this.width * this.height;
    }

    public static Vec3 getRelativePosition(BlockUtil.FoundRectangle rectangle, Direction.Axis axis, Vec3 pos, EntityDimensions dimensions) {
        double d0 = (double)rectangle.axis1Size - (double)dimensions.width;
        double d1 = (double)rectangle.axis2Size - (double)dimensions.height;
        BlockPos corner = rectangle.minCorner;
        double x;
        if (d0 > 0.0D) {
            float f = (float)corner.get(axis) + dimensions.width / 2.0F;
            x = Mth.clamp(Mth.inverseLerp(pos.get(axis) - (double)f, 0.0D, d0), 0.0D, 1.0D);
        } else {
            x = 0.5D;
        }

        double y;
        if (d1 > 0.0D) {
            Direction.Axis yAxis = Direction.Axis.Y;
            y = Mth.clamp(Mth.inverseLerp(pos.get(yAxis) - (double)corner.get(yAxis), 0.0D, d1), 0.0D, 1.0D);
        } else {
            y = 0.0D;
        }

        Direction.Axis zAxis = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        double z = pos.get(zAxis) - ((double)corner.get(zAxis) + 0.5D);
        return new Vec3(x, y, z);
    }

    public static PortalInfo createPortalInfo(ServerLevel level, BlockUtil.FoundRectangle portalPos, Direction.Axis axis, Vec3 relativePos, Entity entity, Vec3 velocity, float yRot, float xRot) {
        BlockPos minCorner = portalPos.minCorner;
        BlockState state = level.getBlockState(minCorner);
        Direction.Axis portalAxis = state.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
        double axis1Size = (double)portalPos.axis1Size;
        double axis2Size = (double)portalPos.axis2Size;
        EntityDimensions dimensions = entity.getDimensions(entity.getPose());
        int rotation = axis == portalAxis ? 0 : 90;
        Vec3 rotatedVelocity = axis == portalAxis ? velocity : new Vec3(velocity.z, velocity.y, -velocity.x);
        double xOffset = (double)dimensions.width / 2.0D + (axis1Size - (double)dimensions.width) * relativePos.x();
        double yOffset = (axis2Size - (double)dimensions.height) * relativePos.y();
        double zOffset = 0.5D + relativePos.z();
        boolean isXAxis = portalAxis == Direction.Axis.X;
        Vec3 pos = new Vec3((double)minCorner.getX() + (isXAxis ? xOffset : zOffset), (double)minCorner.getY() + yOffset, (double)minCorner.getZ() + (isXAxis ? zOffset : xOffset));
        Vec3 freePos = findCollisionFreePosition(pos, level, entity, dimensions);
        return new PortalInfo(freePos, rotatedVelocity, yRot + (float)rotation, xRot);
    }

    private static Vec3 findCollisionFreePosition(Vec3 pos, ServerLevel level, Entity entity, EntityDimensions dimensions) {
        if (!(dimensions.width > 4.0F) && !(dimensions.height > 4.0F)) {
            double halfHeight = (double)dimensions.height / 2.0D;
            Vec3 center = pos.add(0.0D, halfHeight, 0.0D);
            VoxelShape shape = Shapes.create(AABB.ofSize(center, (double)dimensions.width, 0.0D, (double)dimensions.width).expandTowards(0.0D, 1.0D, 0.0D).inflate(1.0E-6D));
            Optional<Vec3> freePos = level.findFreePosition(entity, shape, center, (double)dimensions.width, (double)dimensions.height, (double)dimensions.width);
            return freePos.map(vec3 -> vec3.subtract(0.0D, halfHeight, 0.0D)).orElse(pos);
        } else {
            return pos;
        }
    }
}
