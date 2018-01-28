package net.torocraft.torotraits.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class SpawnApi {

  public static void spawn(World world, String mob, BlockPos pos, int spawnRadius) {
    spawnEntityCreature(world, getEntityFromString(world, mob), pos, spawnRadius);
  }

  public static String getEntityString(Entity entityIn) {
    EntityEntry entry = EntityRegistry.getEntry(entityIn.getClass());
    if (entry == null || entry.getRegistryName() == null) {
      return "";
    }
    return entry.getRegistryName().toString();
  }

  public static EntityCreature getEntityFromString(World world, String entityID) {
    String[] parts = entityID.split(":");
    String domain, entityName;
    if (parts.length == 2) {
      domain = parts[0];
      entityName = parts[1];
    } else {
      domain = "minecraft";
      entityName = entityID;
    }
    Entity entity = EntityList
        .createEntityByIDFromName(new ResourceLocation(domain, entityName), world);
    if (!(entity instanceof EntityCreature)) {
      return null;
    }
    return (EntityCreature) entity;
  }

  public static void spawnEntityCreature(World world, EntityCreature entity, BlockPos pos,
      int spawnRadius) {
    double x = pos.getX() + 0.5D;
    double y = pos.getY();
    double z = pos.getZ() + 0.5D;
    entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F),
        0.0F);
    entity.rotationYawHead = entity.rotationYaw;
    entity.renderYawOffset = entity.rotationYaw;
    entity.enablePersistence();
    entity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    if (spawnRadius > 1) {
      findAndSetSuitableSpawnLocation(entity, pos, spawnRadius);
    }
    world.spawnEntity(entity);
    entity.playLivingSound();
  }

  /**
   * find the closest suitable spawn location to the given position within the provided radius and
   * set it to the entity.
   *
   * @return false if no location was found
   */
  public static boolean findAndSetSuitableSpawnLocation(EntityCreature entity, BlockPos around,
      int spawnRadius) {

    SpawnLocationScanner scanner = new SpawnLocationScanner(entity.world, entity, around);
    BlockPos spawnable = scanner.areaScan(spawnRadius, spawnRadius, 10);

    if (spawnable == null) {
      entity.setPosition(around.getX() + 0.5, around.getY(), around.getZ() + 0.5);
      return false;
    } else {
      entity.setPosition(spawnable.getX() + 0.5, spawnable.getY(), spawnable.getZ() + 0.5);
      return true;
    }
  }

}
