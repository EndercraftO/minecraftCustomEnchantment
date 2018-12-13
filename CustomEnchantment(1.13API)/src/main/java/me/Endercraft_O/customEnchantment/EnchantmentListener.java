package me.Endercraft_O.customEnchantment;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

import me.Endercraft_O.customEnchantment.event.*;

public class EnchantmentListener implements Listener{

	@EventHandler
	public void BlockBreak(BlockBreakEvent e)
	{
		if(e == null)
			return;
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		List<CustomEnchantmentValue> enchants = ItemEnchanter.getEnchantments(item);
		if(!enchants.isEmpty())
		{
			Bukkit.getServer().getPluginManager().callEvent(new EnchantedBlockBreakEvent(e,enchants));
		}
	}
	@EventHandler
	public void EntityShootBow(EntityShootBowEvent e)
	{
		if(e == null)
			return;
		ItemStack item = e.getBow();
		List<CustomEnchantmentValue> enchants = ItemEnchanter.getEnchantments(item);
		if(!enchants.isEmpty())
		{
			Bukkit.getServer().getPluginManager().callEvent(new EnchantedEntityShootBowEvent(e,enchants));
		}
	}
	@EventHandler
	public void EntityDeathBow(EntityDeathEvent e)
	{
		if(e == null)
			return;
		ItemStack item;		
		if(e.getEntity().getKiller().getType().equals(EntityType.PLAYER))
		{
			item = ((Player)e.getEntity().getKiller()).getInventory().getItemInMainHand();
			List<CustomEnchantmentValue> enchants = ItemEnchanter.getEnchantments(item);
			if(!enchants.isEmpty())
			{
				Bukkit.getServer().getPluginManager().callEvent(new EnchantedEntityDeathEvent(e,enchants));
			}
		}
	}
	@EventHandler
	public void PlayerDropItem(PlayerDropItemEvent e)
	{
		if(e == null)
			return;
		ItemStack item = e.getItemDrop().getItemStack();
		List<CustomEnchantmentValue> enchants = ItemEnchanter.getEnchantments(item);
		if(!enchants.isEmpty())
		{
			Bukkit.getServer().getPluginManager().callEvent(new EnchantedPlayerDropItemEvent(e,enchants));
		}
	}
	@EventHandler
	public void PlayerInteractEntity(PlayerInteractEntityEvent e)
	{
		if(e == null)
			return;
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		List<CustomEnchantmentValue> enchants = ItemEnchanter.getEnchantments(item);
		if(!enchants.isEmpty())
		{
			Bukkit.getServer().getPluginManager().callEvent(new EnchantedPlayerInteractEntityEvent(e,enchants));
		}
	}
	@EventHandler
	public void PlayerInteract(PlayerInteractEvent e)
	{
		if(e == null)
			return;
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		List<CustomEnchantmentValue> enchants = ItemEnchanter.getEnchantments(item);
		if(!enchants.isEmpty())
		{
			Bukkit.getServer().getPluginManager().callEvent(new EnchantedPlayerInteractEvent(e,enchants));
		}
	}
	@EventHandler
	public void PlayerItemBreak(PlayerItemBreakEvent e)
	{
		if(e == null)
			return;
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		List<CustomEnchantmentValue> enchants = ItemEnchanter.getEnchantments(item);
		if(!enchants.isEmpty())
		{
			Bukkit.getServer().getPluginManager().callEvent(new EnchantedPlayerItemBreakEvent(e,enchants));
		}
	}
	@EventHandler
	public void PlayerItemConsume(PlayerItemConsumeEvent e)
	{
		if(e == null)
			return;
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		List<CustomEnchantmentValue> enchants = ItemEnchanter.getEnchantments(item);
		if(!enchants.isEmpty())
		{
			Bukkit.getServer().getPluginManager().callEvent(new EnchantedPlayerItemConsumeEvent(e,enchants));
		}
	}
	@EventHandler
	public void PlayerItemHeld(PlayerItemHeldEvent e)
	{
		if(e == null)
			return;
		ItemStack main = e.getPlayer().getInventory().getItemInMainHand();
		ItemStack off = e.getPlayer().getInventory().getItemInOffHand();
		List<CustomEnchantmentValue> enchantsMain = ItemEnchanter.getEnchantments(main);
		List<CustomEnchantmentValue> enchantsOff = ItemEnchanter.getEnchantments(off);
		if(!enchantsMain.isEmpty() || !enchantsOff.isEmpty())
		{
			Bukkit.getServer().getPluginManager().callEvent(new EnchantedPlayerItemHeldEvent(main,off));
		}
	}
	@EventHandler
	public void PlayerItemMend(PlayerItemMendEvent e)
	{
		if(e == null)
			return;
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		List<CustomEnchantmentValue> enchants = ItemEnchanter.getEnchantments(item);
		if(!enchants.isEmpty())
		{
			Bukkit.getServer().getPluginManager().callEvent(new EnchantedPlayerItemMendEvent(e,enchants));
		}
	}
	@EventHandler
	public void PlayerShearEntity(PlayerShearEntityEvent e)
	{
		if(e == null)
			return;
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		List<CustomEnchantmentValue> enchants = ItemEnchanter.getEnchantments(item);
		if(!enchants.isEmpty())
		{
			Bukkit.getServer().getPluginManager().callEvent(new EnchantedPlayerShearEntityEvent(e,enchants));
		}
	}
	
	
	@EventHandler
	public void AnvilAdd(PrepareAnvilEvent e)
	{
		if(e == null)
			return;
		ItemStack first = e.getInventory().getItem(0).clone();
		ItemStack second = e.getInventory().getItem(1).clone();
		if(first.getType().equals(second.getType()) || second.getType().equals(Material.ENCHANTED_BOOK) && ItemEnchanter.hasCustomEnchantments(second))
		{
			List<CustomEnchantmentValue> secondEnc = ItemEnchanter.getAnvilableEnchantments(second);
			
			ItemEnchanter.safeEnchant(first, secondEnc);
			e.setResult(first);
		}
	}
	
	@EventHandler
	public void click(InventoryClickEvent e)
	{
		if(e.getInventory() instanceof AnvilInventory)
		{
			AnvilInventory inventory = (AnvilInventory) e.getInventory();
			
			if(e.getSlot() == 2)
			{
				Player p = (Player) e.getWhoClicked();
				if(e.isShiftClick())
				{
					p.getInventory().addItem(inventory.getItem(2));
					inventory.setItem(0, new ItemStack(Material.AIR));
					inventory.setItem(1, new ItemStack(Material.AIR));
					inventory.setItem(2, new ItemStack(Material.AIR));
				}
				else
				{
					if(p.getItemOnCursor() != null && p.getItemOnCursor().getType().equals(Material.AIR))
					{
						p.setItemOnCursor(inventory.getItem(2));
						inventory.setItem(0, new ItemStack(Material.AIR));
						inventory.setItem(1, new ItemStack(Material.AIR));
						inventory.setItem(2, new ItemStack(Material.AIR));
					}
				}
			}

		}
	}
}
