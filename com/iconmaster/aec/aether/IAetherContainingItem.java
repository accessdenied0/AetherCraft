package com.iconmaster.aec.aether;

import net.minecraft.item.ItemStack;

public interface IAetherContainingItem {
	public float addAether(ItemStack stack,float av);

	public float extractAether(ItemStack stack,float av);

	public void setAether(ItemStack stack,float av);

	public float getAether(ItemStack stack);
}