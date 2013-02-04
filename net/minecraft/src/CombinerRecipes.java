package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class CombinerRecipes
{
    private static final CombinerRecipes smeltingBase = new CombinerRecipes();

    /** The list of smelting results. */
    private Map smeltingList = new HashMap();
    private Map experienceList = new HashMap();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final CombinerRecipes smelting()
    {
        return smeltingBase;
    }

    private CombinerRecipes()
    {
        this.addSmelting(Block.oreIron.blockID, new ItemStack(Item.ingotIron), 0.7F);
    }

    /**
     * Adds a smelting recipe.
     */
    public void addSmelting(int par1, ItemStack par2ItemStack, float par3)
    {
        this.smeltingList.put(Integer.valueOf(par1), par2ItemStack);
        this.experienceList.put(Integer.valueOf(par2ItemStack.itemID), Float.valueOf(par3));
    }

    /**
     * Returns the smelting result of an item.
     */
    public ItemStack getSmeltingResult(int par1)
    {
        return (ItemStack)this.smeltingList.get(Integer.valueOf(par1));
    }

    public Map getSmeltingList()
    {
        return this.smeltingList;
    }

    public float getExperience(int par1)
    {
        return this.experienceList.containsKey(Integer.valueOf(par1)) ? ((Float)this.experienceList.get(Integer.valueOf(par1))).floatValue() : 0.0F;
    }
}
