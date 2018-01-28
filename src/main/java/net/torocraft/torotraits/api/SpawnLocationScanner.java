package net.torocraft.torotraits.api;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpawnLocationScanner {

  private final World world;
  private final int worldHeight;
  private final Random rand;

  private AxisAlignedBB box;
  private BlockPos pos;
  private BlockPos up;
  private BlockPos down;

  public SpawnLocationScanner(World world, Entity entity, BlockPos startPos) {
    this.world = world;
    this.pos = startPos;
    worldHeight = world.getActualHeight();
    box = entity.getEntityBoundingBox();
    rand = new Random();
  }

  @Nullable
  public BlockPos areaScan(int areaRadius, int verticalRadius, int attempts) {
    BlockPos startPos = pos;

    for (int attempt = 0; attempt < attempts; attempt++) {
      pos = findBlockAround(startPos, areaRadius);
      BlockPos spawnablePos = verticalScan(verticalRadius);
      if (spawnablePos != null) {
        return spawnablePos;
      }
    }

    return null;
  }

  private BlockPos findBlockAround(BlockPos startPos, int areaRadius) {
    int distance = rand.nextInt(areaRadius);
    int degrees = rand.nextInt(360);
    int x = distance * (int) Math.round(Math.cos(Math.toRadians(degrees)));
    int z = distance * (int) Math.round(Math.sin(Math.toRadians(degrees)));
    return new BlockPos(startPos.getX() + x, startPos.getY(), startPos.getZ() + z);
  }

  @Nullable
  public BlockPos verticalScan(int verticalRadius) {
    if (check()) {
      return pos;
    }
    up = pos;
    down = pos;
    for (int i = 0; i < verticalRadius; i++) {
      if (scanUp()) {
        return pos;
      }
      if (scanDown()) {
        return pos;
      }
    }
    return null;
  }

  private boolean scanUp() {
    if (up.getY() > worldHeight) {
      return false;
    }
    pos = up = up.up();
    return check();
  }

  private boolean scanDown() {
    if (up.getY() < 0) {
      return false;
    }
    pos = down = down.down();
    return check();
  }

  private boolean check() {
    box = box.offset(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    return isOnOpaqueBlock() && isNotColliding();
  }

  private boolean isOnOpaqueBlock() {
    IBlockState state = world.getBlockState(pos.down());
    return state.isOpaqueCube();
  }

  private boolean isNotColliding() {
    return !world.containsAnyLiquid(box) && world.getCollisionBoxes(null, box).isEmpty();
  }

}
