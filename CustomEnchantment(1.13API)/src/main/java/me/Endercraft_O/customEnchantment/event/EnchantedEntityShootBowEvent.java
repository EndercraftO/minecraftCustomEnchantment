package me.Endercraft_O.customEnchantment.event;

import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityShootBowEvent;

import me.Endercraft_O.customEnchantment.CustomEnchantmentValue;

public class EnchantedEntityShootBowEvent extends Event {

	private EntityShootBowEvent event;
	private List<CustomEnchantmentValue> enchantment;
	
	public EnchantedEntityShootBowEvent(EntityShootBowEvent event, List<CustomEnchantmentValue> enchantment) {
		this.event = event;
		this.enchantment = enchantment;
	}

	public List<CustomEnchantmentValue> getEnchantment()
	{
		return enchantment;
	}
	public EntityShootBowEvent getEvent()
	{
		return event;
	}
	
	
	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
