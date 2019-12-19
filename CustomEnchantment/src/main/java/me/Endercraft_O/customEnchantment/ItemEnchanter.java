package me.Endercraft_O.customEnchantment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemEnchanter {

	private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();
	private final static String customEnchantChar = ""+ChatColor.DARK_RED+ChatColor.DARK_AQUA+ChatColor.DARK_RED+ChatColor.DARK_PURPLE+ChatColor.GRAY;
	private final static String enchantLvlOne = ""+ChatColor.DARK_AQUA+ChatColor.DARK_BLUE+ChatColor.RESET;
	
    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }
	
    
    public static boolean hasCustomEnchantments(ItemStack item)
    {
    	return !getEnchantments(item).isEmpty();
    }
	public static void forceEnchant(ItemStack item, CustomEnchantmentValue cev)
	{
		ItemMeta m = item.getItemMeta();
		List<String> lore = m.getLore();
		if(lore == null) lore = new ArrayList<String>();
		int start = 0;
		for(int i = 0; i < lore.size(); i++)
		{
			if(lore.get(i).indexOf(customEnchantChar) != 0)
			{
				start = i;
				break;
			}
		}
		String val = toRomanNumerals(cev.getLvl());
		if(val.equals("I"))
			val = new String(enchantLvlOne);
		String enchantment = customEnchantChar +  cev.getEnchantment().getName() + " " + val;
		
		lore.add(start, enchantment);
		m.setLore(lore);
		item.setItemMeta(m);
	}
	public static void safeEnchant(ItemStack item, CustomEnchantmentValue cev)
	{
		if((cev.getEnchantment().getApplicable() == null || cev.getEnchantment().getApplicable().isEmpty() || cev.getEnchantment().getApplicable().contains(item.getType().name())) && !hasCustomEnchantments(item, cev.getEnchantment().getCustomConflicts()) && !hasEnchantments(item, cev.getEnchantment().getConflicts()))
		{
			List<CustomEnchantmentValue> removeds = removeEnchantment(item, cev.getEnchantment());
			CustomEnchantmentValue removed = (removeds == null || removeds.isEmpty()) ? null : removeds.get(0);
			if(removed == null)
			{
				forceEnchant(item, cev);
			}
			else
			{
				int lvl = removed.getLvl();
				if(lvl == cev.getLvl())
				{
					lvl = lvl == cev.getEnchantment().getMaxLvl() ? lvl : lvl+1;
				}
				else
					lvl = Integer.max(lvl, cev.getLvl());
				
				cev.setLvl((short)lvl);
				forceEnchant(item, cev);
			}
			
		}
	}
	public static void forceEnchant(ItemStack item, List<CustomEnchantmentValue> cevs)
	{
		for(CustomEnchantmentValue cev : cevs)
			forceEnchant(item, cev);
	}
	public static void safeEnchant(ItemStack item, List<CustomEnchantmentValue> cevs)
	{
		for(CustomEnchantmentValue cev : cevs)
			safeEnchant(item, cev);
	}
	public static boolean hasCustomEnchantments(ItemStack item, List<CustomEnchantment> list)
	{
		if(list == null || list.isEmpty())
			return false;
		List<CustomEnchantmentValue> main = getEnchantments(item);
		for(CustomEnchantmentValue cev : main)
		{
			if(list.contains(cev.getEnchantment()))
				return true;
		}
		return false;
	}
	public static boolean hasCustomEnchantments(ItemStack item, CustomEnchantment... list)
	{
		if(list == null || list.length == 0)
			return false;
		List<CustomEnchantment> ench = Arrays.asList(list);
		List<CustomEnchantmentValue> main = getEnchantments(item);
		for(CustomEnchantmentValue cev : main)
		{
			if(ench.contains(cev.getEnchantment()))
				return true;
		}
		return false;
	}
	public static boolean hasEnchantments(ItemStack item, List<Enchantment> list)
	{
		if(item.hasItemMeta())
		for(Enchantment e : list)
			if(item.getItemMeta().hasEnchant(e))
				return true;
		return false;
	}
	public static boolean hasEnchantments(ItemStack item, Enchantment... list)
	{
		if(item.hasItemMeta())
		for(Enchantment e : list)
			if(item.getItemMeta().hasEnchant(e))
				return true;
		return false;
	}
	public static List<CustomEnchantmentValue> getEnchantments(ItemStack item)
	{
		List<CustomEnchantmentValue> cevs = new ArrayList<CustomEnchantmentValue>();
		if(item.getItemMeta() == null)
			return cevs;
		List<String> lore = item.getItemMeta().getLore();
		int start = 0;
		if(lore != null)
			for(int i = 0; i < lore.size(); i++)
			{
				String s = lore.get(i);
				start = s.indexOf(customEnchantChar);
				if(start == 0)
				{
					cevs.add(new CustomEnchantmentValue(CustomEnchantment.getEnchantment(s.substring(customEnchantChar.length(), s.lastIndexOf(" "))), (short)toInteger(s.substring(s.lastIndexOf(" ")+1))) );
				}
			}
		return cevs;
	}
	public static CustomEnchantmentValue getEnchantment(ItemStack item, String name)
	{
		List<CustomEnchantmentValue> cevs = getEnchantments(item);
		for(CustomEnchantmentValue cev : cevs)
		{
			if(cev.getEnchantment().getName().equals(name))
				return cev;
		}
		return null;
	}
	public static CustomEnchantmentValue getEnchantment(ItemStack item, CustomEnchantment ce)
	{
		List<CustomEnchantmentValue> cevs = getEnchantments(item);
		for(CustomEnchantmentValue cev : cevs)
		{
			if(cev.getEnchantment().getName().equals(ce.getName()))
				return cev;
		}
		return null;
	}
	public static boolean removeEnchantment(ItemStack item, CustomEnchantmentValue cev)
	{
		String enchantment = customEnchantChar + cev.getEnchantment().getName() + " " + toRomanNumerals(cev.getLvl());
		List<String> lore = item.getItemMeta().getLore();
		return lore.remove(enchantment);
	}
	public static List<CustomEnchantmentValue> removeEnchantment(ItemStack item, CustomEnchantment ce)
	{
		List<CustomEnchantmentValue> ret = new ArrayList<CustomEnchantmentValue>();
		if(!hasCustomEnchantments(item, ce))
			return ret;
		List<String> lore = item.getItemMeta().getLore();
		for(int i = 0; i < lore.size(); i++)
		{
			String s = lore.get(i);
			int start = s.indexOf(customEnchantChar);
			if(start == 0)
			{
				if(ce.getName().equals(s.substring(customEnchantChar.length(), s.lastIndexOf(" "))))
				{
					ret.add(new CustomEnchantmentValue(CustomEnchantment.getEnchantment(s.substring(customEnchantChar.length(), s.lastIndexOf(" "))), (short)toInteger(s.substring(s.lastIndexOf(" ")+1))) );
					lore.remove(i);
					i--;
				}
			}
		}
		ItemMeta m = item.getItemMeta();
		m.setLore(lore);
		item.setItemMeta(m);
		return ret;
	}
	
	private final static String toRomanNumerals(int number) {
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRomanNumerals(number-l);
    }
	private final static int toInteger(String s)
	{
		if(s.equals(enchantLvlOne))
			return 1;
		int ret = 0;
		for(Integer i : map.keySet())
		{
			String seg = map.get(i);
			if(seg.length() > 1)
			{
				int a = s.indexOf(seg);
				if(a != -1)
				{
					ret += i;
					s = s.substring(0, a) + s.substring(a+2);
				}
			}
		}
		for(Integer i : map.keySet())
		{
			String seg = map.get(i);
			if(seg.length() == 1)
			{
				while(s.indexOf(seg) != -1)
				{
					int a = s.indexOf(seg);
					ret += i;
					s = s.substring(0, a) + s.substring(a+1);
				}
			}
		}
		return ret;
	}
	public static List<CustomEnchantmentValue> getAnvilableEnchantments(ItemStack item) {
		List<CustomEnchantmentValue> ret = getEnchantments(item);
		for(CustomEnchantmentValue cev : ret)
		{
			if(!cev.getEnchantment().isAnvilable())
			{
				ret.remove(cev);
			}
		}
		return ret;
	}
}
