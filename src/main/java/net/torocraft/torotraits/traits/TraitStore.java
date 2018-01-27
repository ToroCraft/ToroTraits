package net.torocraft.torotraits.traits;

import java.util.List;
import net.torocraft.torotraits.nbt.NbtField;

public class TraitStore {

  @NbtField(genericType = Trait.class)
  public List<Trait> traits;

}
