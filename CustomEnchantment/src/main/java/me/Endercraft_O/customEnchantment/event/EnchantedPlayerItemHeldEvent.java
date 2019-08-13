package me.Endercraft_O.customEnchantment.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class EnchantedPlayerItemHeldEvent extends Event {
	
	private ItemStack main;
	private ItemStack off;
	
	public EnchantedPlayerItemHeldEvent(ItemStack main, ItemStack off)
	{
		this.main = main;
		this.off = off;
	}
	
	public ItemStack getMainHand()
	{
		return main;
	}
	public ItemStack getOffHand()
	{
		return off;
	}
	
	
	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
