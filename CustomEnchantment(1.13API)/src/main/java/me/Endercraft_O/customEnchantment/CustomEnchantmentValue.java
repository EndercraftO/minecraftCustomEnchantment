package me.Endercraft_O.customEnchantment;

public class CustomEnchantmentValue{

	private CustomEnchantment enchantment;
	private short lvl;
	
	protected CustomEnchantmentValue(CustomEnchantment enchantment, short lvl)
	{
		this.enchantment = enchantment;
		if(lvl < enchantment.getMaxLvl())
		{
			this.lvl = lvl;
		}
		else
		{
			this.lvl = enchantment.getMaxLvl();
		}
	}
	
	public void setLvl(short lvl)
	{
		this.lvl = lvl;
	}
	public short getLvl()
	{
		return lvl;
	}
	public CustomEnchantment getEnchantment()
	{
		return enchantment;
	}
	
	public String toString()
	{
		return "[Name:" + enchantment.getName() + " Lvl:" +  lvl + "]";
	}
}
