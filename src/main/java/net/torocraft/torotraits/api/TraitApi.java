package net.torocraft.torotraits.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.torocraft.torotraits.ToroTraits;
import net.torocraft.torotraits.nbt.NbtSerializer;
import net.torocraft.torotraits.traits.Trait;
import net.torocraft.torotraits.traits.TraitStore;
import net.torocraft.torotraits.traits.Type;

public class TraitApi {

  public static void applyTrait(EntityCreature entity, Trait trait) {
    entity.addTag(ToroTraits.TAG_HAS_TRAIT);
    TraitStore store = read(entity);
    if (store.traits == null) {
      store.traits = new ArrayList<>();
    }
    store.traits.add(trait);
    write(entity, store);
  }

  public static List<Trait> getTraits(EntityCreature entity, Trait trait) {
    if (!entity.getTags().contains(ToroTraits.TAG_HAS_TRAIT)) {
      return Collections.emptyList();
    }
    TraitStore store = read(entity);
    if (store.traits == null) {
      return Collections.emptyList();
    }
    return store.traits;
  }

  public static boolean hasTrait(EntityCreature entity, Type traitType) {
    return false;
  }

  public static void write(EntityCreature entity, TraitStore store) {
    NBTTagCompound c = new NBTTagCompound();
    NbtSerializer.write(c, store);
    entity.getEntityData().setTag(ToroTraits.NBT_TRAIT_STORE, c);
  }

  public static TraitStore read(EntityCreature entity) {
    NBTTagCompound c = entity.getEntityData().getCompoundTag(ToroTraits.NBT_TRAIT_STORE);
    TraitStore store = NbtSerializer.read(c, TraitStore.class);
    return store;
  }

  public static AxisAlignedBB nearByBox(BlockPos position, int radius) {
    return new AxisAlignedBB(position).grow(radius, radius, radius);
  }

  private static EntityItem damageAndDrop(EntityCreature entity, ItemStack stack) {
    if (stack.isItemStackDamageable()) {
      stack.setItemDamage(stack.getMaxDamage() - entity.getRNG()
          .nextInt(1 + entity.getRNG().nextInt(Math.max(stack.getMaxDamage() - 3, 1))));
    }
    return drop(entity, stack);
  }

  public static EntityItem drop(EntityCreature entity, ItemStack stack) {
    return new EntityItem(entity.getEntityWorld(), entity.posX, entity.posY, entity.posZ, stack);
  }
}
