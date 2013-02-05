package net.minecraft.src;

public class RecipesExchangeStone {

	public void addRecipes(CraftingManager par1CraftingManager) 
	{
		par1CraftingManager.addRecipe(new ItemStack(Block.chest), new Object[] {"###", "# #", "###", '#', Block.planks});
        
	}
}
