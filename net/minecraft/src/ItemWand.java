package net.minecraft.src;

public class ItemWand extends Item
{
  public ItemWand(int par1)
  {
    super(par1);
    this.maxStackSize = 1;
    this.setCreativeTab(CreativeTabs.tabMisc);
  }

  public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
  {
    if(par7 == 0)
    {
      --par5;
    }
    if(par7 == 1)
    {
      ++par5;
    }
    if(par7 == 2)
    {
      --par6;
    }
    if(par7 == 3)
    {
      ++par6;
    }
    if(par7 == 4)
    {
      --par4;
    }
    if(par7 == 5)
    {
      ++par4;
    }
    
    if(!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
    {
      return false;
    }
    else
    {
      int var11 = par3World.getBlockId(par3, par4, par5);
      
      if (var11 == Block.bookself)
      {
        var11.setBlockWithNotify(par4, par5, par6, Item.)
      }
    }
  }
}
