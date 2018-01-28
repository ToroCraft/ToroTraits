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
  private final double halfWidth;
  private final double height;

  private BlockPos pos;
  private BlockPos up;
  private BlockPos down;

  public SpawnLocationScanner(World world, Entity entity, BlockPos startPos) {
    this.world = world;
    this.pos = startPos;
    worldHeight = world.getActualHeight();
    halfWidth = entity.width / 2.0F;
    height = entity.height;
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
    return isOnOpaqueBlock() && isNotColliding();
  }

  private boolean isOnOpaqueBlock() {
    IBlockState state = world.getBlockState(pos.down());
    return state.isOpaqueCube();
  }

  private boolean isNotColliding() {
    AxisAlignedBB box = getBoundingBox();
    return !world.containsAnyLiquid(box) && world.getCollisionBoxes(null, box).isEmpty();
  }

  public AxisAlignedBB getBoundingBox() {
    double x = pos.getX() + 0.5;
    double y = pos.getY();
    double z = pos.getZ() + 0.5;
    return new AxisAlignedBB(x - halfWidth, y, z - halfWidth, x + halfWidth, y + height,
        z + halfWidth);
  }

}
