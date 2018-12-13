package me.Endercraft_O.customEnchantment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class CustomEnchantment {

	private String name;
	private short max;
	private Listener listener = null;
	private boolean anvilable;
	private List<Material> applicable = null;
	private List<CustomEnchantment> conflicts = null;
	
	public CustomEnchantment(String name, int max, Material... app)
	{
		this.name = name;
		this.max = (short) Math.abs(max);
		if(app != null && app.length > 0)
			applicable = Arrays.asList(app);
	}
	
	public CustomEnchantment(String name, int max, Listener listener, Material... app)
	{
		this(name, max);
		this.listener = listener;
		if(app != null && app.length > 0)
			applicable = Arrays.asList(app);
	}
	
	public CustomEnchantment(String name, int max, boolean anvil, Material... app)
	{
		this.name = name;
		this.max = (short) Math.abs(max);
		anvilable = anvil;
		if(app != null && app.length > 0)
			applicable = Arrays.asList(app);
	}
	
	public CustomEnchantment(String name, int max, Listener listener, boolean anvil, Material... app)
	{
		this(name, max);
		this.listener = listener;
		anvilable = anvil;
		if(app != null && app.length > 0)
			applicable = Arrays.asList(app);
	}
	
	public void addConflicts(CustomEnchantment... ces)
	{
		for(CustomEnchantment ce : ces)
		{
			conflicts.add(ce);
		}
	}
	
	public String getName() {return name;}
	public Short getMaxLvl() {return max;}
	public Listener getListener() {return listener;}
	public boolean isAnvilable() {return anvilable;}
	public List<Material> getApplicable() {return applicable;}
	public List<CustomEnchantment> getConflicts() {return conflicts;}
	
	public static boolean addEnchamtment(CustomEnchantment enchant)
	{
		if(!getEnchantments().contains(enchant.getName()))
		{
			Main.getEnchantments().add(enchant);
			if(enchant.listener != null)
				Bukkit.getPluginManager().registerEvents(enchant.getListener(), Main.plugin);
			return true;
		}
		return false;
	}
	public static boolean removeEnchantment(CustomEnchantment enchant)
	{
		if(Main.getEnchantments().contains(enchant))
		{
			Main.getEnchantments().remove(enchant);
			HandlerList.unregisterAll(enchant.getListener());
			return true;
		}
		return false;
	}
	public static boolean removeEnchamtment(String name)
	{
		CustomEnchantment ce = getEnchantment(name);
		if(ce != null)
		{
			removeEnchantment(ce);
			return true;
		}
		return false;
	}
	public static CustomEnchantment getEnchantment(String name)
	{
		for(CustomEnchantment ce : Main.getEnchantments())
		{
			if(ce.getName().equals(name))
				return ce;
		}
		return null;
	}
	public static List<String> getEnchantments()
	{
		List<String> ret = new ArrayList<String>();
		for(CustomEnchantment ce : Main.getEnchantments())
		{
			ret.add(ce.getName());
		}
		return ret;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof CustomEnchantment)
		{
			CustomEnchantment ce = (CustomEnchantment) obj;
			if(ce.getName().equals(this.getName()) && ce.getMaxLvl().equals(this.getMaxLvl()))
				return true;
		}
		return false;
	}
}
