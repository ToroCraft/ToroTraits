package net.torocraft.torotraits.nbt;

import net.minecraft.nbt.NBTTagCompound;

public interface NbtData {

	<T extends NbtData> T load(NBTTagCompound c);

	<T extends NbtData> NBTTagCompound save(T data);

}
