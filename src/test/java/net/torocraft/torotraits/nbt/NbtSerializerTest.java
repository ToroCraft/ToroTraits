package net.torocraft.torotraits.nbt;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.nbt.NBTTagCompound;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NbtSerializerTest {

  @Test
  public void testBoolTrue() {
    t1.bool1 = true;
    readWrite();
    Assert.assertEquals(t1.bool1, t2.bool1);
  }

  @Test
  public void testBoolFalse() {
    t1.bool1 = false;
    readWrite();
    Assert.assertEquals(t1.bool1, t2.bool1);
  }

  @Test
  public void testBooleanTrue() {
    t1.bool2 = true;
    readWrite();
    Assert.assertEquals(t1.bool2, t2.bool2);
  }

  @Test
  public void testBooleanFalse() {
    t1.bool2 = false;
    readWrite();
    Assert.assertEquals(t1.bool2, t2.bool2);
  }

  @Test
  public void testBooleanNull() {
    t1.bool2 = null;
    readWrite();
    Assert.assertEquals(t1.bool2, t2.bool2);
  }

  @Test
  public void testSetNull() {
    t1.set1 = null;
    readWrite();
    Assert.assertEquals(t1.set1, t2.set1);
  }

  @Test
  public void testSet() {
    t1.set1 = new HashSet<>();
    t1.set1.add(1);
    t1.set1.add(10);
    t1.set1.add(13);
    readWrite();
    Assert.assertEquals(3, t2.set1.size());
    Assert.assertTrue(t2.set1.contains(1));
    Assert.assertTrue(t2.set1.contains(10));
    Assert.assertTrue(t2.set1.contains(13));
  }

  TestClass t1;
  TestClass t2;

  @Before
  public void before() {
    t1 = new TestClass();
  }

  private void readWrite() {
    NBTTagCompound c = new NBTTagCompound();
    NbtSerializer.write(c, t1);
    t2 = NbtSerializer.read(c, TestClass.class);
  }

  public static class TestClass {

    @NbtField
    boolean bool1;

    @NbtField
    Boolean bool2;

    @NbtField(genericType = Integer.class)
    Set<Integer> set1;
  }

}