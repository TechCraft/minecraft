package net.minecraft.src;

public class RecipesExchangeStone {

	public void addRecipes(CraftingManager par1CraftingManager) 
	{
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.flint), new Object[] {Item.exchangeStone, Block.cobblestone, Block.cobblestone, Block.cobblestone, Block.cobblestone});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.cobblestone, 4), new Object[] {Item.exchangeStone, Item.flint});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.gravel), new Object[] {Item.exchangeStone, Block.dirt, Block.dirt, Block.dirt, Block.dirt});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.dirt, 4), new Object[] {Item.exchangeStone, Block.dirt});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.wood), new Object[] {Item.exchangeStone, Block.planks, Block.planks, Block.planks, Block.planks});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.clay), new Object[] {Item.exchangeStone, Block.gravel, Block.gravel, Block.gravel, Block.gravel});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.gravel, 4), new Object[] {Item.exchangeStone, Item.clay});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.clay), new Object[] {Item.exchangeStone, Item.flint, Item.flint, Item.flint, Item.flint});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.obsidian), new Object[] {Item.exchangeStone, Block.wood, Block.wood});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.wood, 2), new Object[] {Item.exchangeStone, Block.obsidian});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.ingotIron), new Object[] {Item.exchangeStone, Block.obsidian, Block.obsidian, Block.obsidian, Block.obsidian});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.obsidian, 4), new Object[] {Item.exchangeStone, Item.ingotIron});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.ingotGold), new Object[] {Item.exchangeStone, Item.ingotIron, Item.ingotIron, Item.ingotIron, Item.ingotIron, Item.ingotIron});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.ingotIron, 5), new Object[] {Item.exchangeStone, Item.ingotGold});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.diamond), new Object[] {Item.exchangeStone, Item.ingotGold, Item.ingotGold, Item.ingotGold, Item.ingotGold});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.ingotGold, 4), new Object[] {Item.exchangeStone, Item.diamond});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.enderPearl), new Object[] {Item.exchangeStone, Item.ingotIron, Item.ingotIron, Item.ingotIron, Item.ingotIron});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.ingotIron, 4), new Object[] {Item.exchangeStone, Item.enderPearl});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.dirt), new Object[] {Item.exchangeStone, Block.sand});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.cobblestone), new Object[] {Item.exchangeStone, Block.dirt});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.grass), new Object[] {Item.exchangeStone, Block.cobblestone});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Block.sand), new Object[] {Item.exchangeStone, Block.grass});
		
	}
}