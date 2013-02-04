package net.minecraft.src;

public class TileEntityCombiner extends TileEntity implements IInventory
{
    /**
     * The ItemStacks that hold the items currently being used in the combiner
     */
    private ItemStack[] combinerItemStacks = new ItemStack[3];

    /** The number of ticks that the combiner will keep burning */
    public int combinerBurnTime = 0;

    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the combiner burning for
     */
    public int currentItemBurnTime = 0;

    /** The number of ticks that the current item has been cooking for */
    public int combinerCookTime = 0;

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.combinerItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.combinerItemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.combinerItemStacks[par1] != null)
        {
            ItemStack var3;

            if (this.combinerItemStacks[par1].stackSize <= par2)
            {
                var3 = this.combinerItemStacks[par1];
                this.combinerItemStacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.combinerItemStacks[par1].splitStack(par2);

                if (this.combinerItemStacks[par1].stackSize == 0)
                {
                    this.combinerItemStacks[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.combinerItemStacks[par1] != null)
        {
            ItemStack var2 = this.combinerItemStacks[par1];
            this.combinerItemStacks[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.combinerItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "container.combiner";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.combinerItemStacks = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.combinerItemStacks.length)
            {
                this.combinerItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.combinerBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.combinerCookTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.combinerItemStacks[1]);
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.combinerBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.combinerCookTime);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.combinerItemStacks.length; ++var3)
        {
            if (this.combinerItemStacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.combinerItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int par1)
    {
        return this.combinerCookTime * par1 / 200;
    }

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }

        return this.combinerBurnTime * par1 / this.currentItemBurnTime;
    }

    /**
     * Returns true if the combiner is currently burning
     */
    public boolean isBurning()
    {
        return this.combinerBurnTime > 0;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        boolean var1 = this.combinerBurnTime > 0;
        boolean var2 = false;

        if (this.combinerBurnTime > 0)
        {
            --this.combinerBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.combinerBurnTime == 0 && this.canSmelt())
            {
                this.currentItemBurnTime = this.combinerBurnTime = getItemBurnTime(this.combinerItemStacks[1]);

                if (this.combinerBurnTime > 0)
                {
                    var2 = true;

                    if (this.combinerItemStacks[1] != null)
                    {
                        --this.combinerItemStacks[1].stackSize;

                        if (this.combinerItemStacks[1].stackSize == 0)
                        {
                            Item var3 = this.combinerItemStacks[1].getItem().getContainerItem();
                            this.combinerItemStacks[1] = var3 != null ? new ItemStack(var3) : null;
                        }
                    }
                }
            }

            if (this.isBurning() && this.canSmelt())
            {
                ++this.combinerCookTime;

                if (this.combinerCookTime == 200)
                {
                    this.combinerCookTime = 0;
                    this.smeltItem();
                    var2 = true;
                }
            }
            else
            {
                this.combinerCookTime = 0;
            }

            if (var1 != this.combinerBurnTime > 0)
            {
                var2 = true;
                BlockCombiner.updateCombinerBlockState(this.combinerBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (var2)
        {
            this.onInventoryChanged();
        }
    }

    /**
     * Returns true if the combiner can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        if (this.combinerItemStacks[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack var1 = CombinerRecipes.smelting().getSmeltingResult(this.combinerItemStacks[0].getItem().itemID);
            return var1 == null ? false : (this.combinerItemStacks[2] == null ? true : (!this.combinerItemStacks[2].isItemEqual(var1) ? false : (this.combinerItemStacks[2].stackSize < this.getInventoryStackLimit() && this.combinerItemStacks[2].stackSize < this.combinerItemStacks[2].getMaxStackSize() ? true : this.combinerItemStacks[2].stackSize < var1.getMaxStackSize())));
        }
    }

    /**
     * Turn one item from the combiner source stack into the appropriate smelted item in the combiner result stack
     */
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack var1 = CombinerRecipes.smelting().getSmeltingResult(this.combinerItemStacks[0].getItem().itemID);

            if (this.combinerItemStacks[2] == null)
            {
                this.combinerItemStacks[2] = var1.copy();
            }
            else if (this.combinerItemStacks[2].itemID == var1.itemID)
            {
                ++this.combinerItemStacks[2].stackSize;
            }

            --this.combinerItemStacks[0].stackSize;

            if (this.combinerItemStacks[0].stackSize <= 0)
            {
                this.combinerItemStacks[0] = null;
            }
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the combiner burning, or 0 if the item isn't
     * fuel
     */
    public static int getItemBurnTime(ItemStack par0ItemStack)
    {
        if (par0ItemStack == null)
        {
            return 0;
        }
        else
        {
            int var1 = par0ItemStack.getItem().itemID;
            Item var2 = par0ItemStack.getItem();

            if (var1 < 256 && Block.blocksList[var1] != null)
            {
                Block var3 = Block.blocksList[var1];

                if (var3 == Block.woodSingleSlab)
                {
                    return 150;
                }

                if (var3.blockMaterial == Material.wood)
                {
                    return 300;
                }
            }

            return var2 instanceof ItemTool && ((ItemTool)var2).getToolMaterialName().equals("WOOD") ? 200 : (var2 instanceof ItemSword && ((ItemSword)var2).getToolMaterialName().equals("WOOD") ? 200 : (var2 instanceof ItemHoe && ((ItemHoe)var2).func_77842_f().equals("WOOD") ? 200 : (var1 == Item.stick.itemID ? 100 : (var1 == Item.coal.itemID ? 1600 : (var1 == Item.bucketLava.itemID ? 20000 : (var1 == Block.sapling.blockID ? 100 : (var1 == Item.blazeRod.itemID ? 2400 : 0)))))));
        }
    }

    /**
     * Return true if item is a fuel source (getItemBurnTime() > 0).
     */
    public static boolean isItemFuel(ItemStack par0ItemStack)
    {
        return getItemBurnTime(par0ItemStack) > 0;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}
}
