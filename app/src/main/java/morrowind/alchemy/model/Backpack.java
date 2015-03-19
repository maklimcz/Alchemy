package morrowind.alchemy.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cj on 2015-03-18.
 */
public class Backpack implements Serializable
{
	private static ArrayList<Ingredient> backpack;

	public static ArrayList<Ingredient> getBackpack()
	{
		if(backpack == null) backpack = new ArrayList<Ingredient>();
		return backpack;
	}

	public static void setBackpack(ArrayList<Ingredient> b)
	{
		backpack = b;
	}

	public static void add(Ingredient ingredient)
	{
		if(backpack == null) backpack = new ArrayList<Ingredient>();
		backpack.add(ingredient);
	}

	public static void remove(Ingredient ingredient)
	{
		if(backpack != null && backpack.contains(ingredient)) backpack.remove(ingredient);
	}

	public static void clear()
	{
		if(backpack != null) backpack.clear();
	}

	public static boolean contains(Ingredient ingredient)
	{
		if(backpack != null) return backpack.contains(ingredient);
		else return false;
	}

	public static int size()
	{
		if(backpack != null) return backpack.size();
		else return 0;
	}
}
