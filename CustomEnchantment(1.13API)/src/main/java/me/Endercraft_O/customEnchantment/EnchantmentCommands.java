package me.Endercraft_O.customEnchantment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;

public class EnchantmentCommands implements CommandExecutor, TabCompleter{

	private static List<String> cmds;
	
	static
	{
		String[] a = new String[3];
		a[0] = "safemerge";
		a[1] = "forcemerge";
		a[2] = "get";
		cmds = Arrays.asList(a);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg) {
		if(sender instanceof Player && arg.length > 0)
		{
			Player p = (Player)sender;
			if(arg[0].equals("safemerge"))
			{
				ItemEnchanter.safeEnchant(p.getInventory().getItemInMainHand(), ItemEnchanter.getEnchantments(p.getInventory().getItemInOffHand()));
			}
			if(arg[0].equals("forcemerge"))
			{
				ItemEnchanter.forceEnchant(p.getInventory().getItemInMainHand(), ItemEnchanter.getEnchantments(p.getInventory().getItemInOffHand()));
			}
			else if(arg[0].equals("get"))
			{
				if(getEnchantmentNames().contains(arg[1]))
				{
					CustomEnchantment ce = CustomEnchantment.getEnchantment(arg[1].replaceAll("_", " "));
					try
					{
						int lvl = Integer.parseInt(arg[2]);
						if(lvl <= ce.getMaxLvl() && lvl > 0)
						{
							ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
							ItemEnchanter.forceEnchant(book, new CustomEnchantmentValue(ce, (short)lvl));
							p.getInventory().addItem(book);
							return true;
						}
						else if(lvl > ce.getMaxLvl())
							p.sendMessage(ChatColor.RED + "The number you have entered (" + lvl + ") is too big, it must be at most " + ce.getMaxLvl());
						else 
							p.sendMessage(ChatColor.RED + "The number you have entered (" + lvl + ") is too small, it must be at least 1");
						return true;
					}
					catch(NumberFormatException e)
					{
						p.sendMessage(ChatColor.RED + "Please enter an integer value for enchantment level");
						return true;
					}
				}
				p.sendMessage(ChatColor.RED + "Usage: /CustomEnchantment get <customEnchantmentName> <lvl>");
			}
			else
			{
				p.sendMessage(ChatColor.RED + "Usage: /CustomEnchantment [safemerge/forcemerge/get]");
			}
		}
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] arg) {
		if(sender instanceof Player)
		{
			if(arg.length == 0)
				return cmds;
			if(arg[0].equals("get"))
			{
				return getEnchantmentNames();
			}
		}
		return cmds;
	}

	private List<String> getEnchantmentNames()
	{
		List<String> ret = new ArrayList<String>();
		for(CustomEnchantment ce : Main.getEnchantments())
		{
			ret.add(ce.getName().replaceAll(" ", "_"));
		}
		return ret;
	}
	
}
