package me.Endercraft_O.customEnchantment;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.Endercraft_O.customEnchantment.event.*;


public class Main extends JavaPlugin{

	protected static Main plugin;
	private static List<CustomEnchantment> enchants = new ArrayList<CustomEnchantment>();
	private static BukkitTask secondLoop;
	
	public void onEnable()
	{
		plugin = this;
		Bukkit.getPluginManager().registerEvents(new EnchantmentListener(), this);
		secondLoop = new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			public void run()
			{
				for(Player p : Bukkit.getServer().getOnlinePlayers())
				{
					Bukkit.getPluginManager().callEvent(new EnchantedSecondTickEvent(p));
				}
			}
		}.runTaskTimer(this, 20, 20);
		EnchantmentCommands ec = new EnchantmentCommands();
		this.getCommand("CustomEnchantment").setExecutor(ec);
		this.getCommand("CustomEnchantment").setTabCompleter(ec);
	}
	
	public void onDisable()
	{
		secondLoop.cancel();
	}
	
	protected static List<CustomEnchantment> getEnchantments()
	{
		return enchants;
	}
}
