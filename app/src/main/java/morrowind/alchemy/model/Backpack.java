package morrowind.alchemy.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Backpack implements Serializable
{
	private static ArrayList<Ingredient> backpack = new ArrayList<>();

	public static ArrayList<Ingredient> getBackpack()
	{
		return backpack;
	}

	public static void setBackpack(ArrayList<Ingredient> b)
	{
		backpack = b;
	}

	public static void add(Ingredient ingredient)
	{
		if(!backpack.contains(ingredient))backpack.add(ingredient);
	}

	public static void remove(Ingredient ingredient)
	{
		backpack.remove(ingredient);
	}

	public static void clear()
	{
		backpack.clear();
	}

	public static boolean contains(Ingredient ingredient)
	{
		return backpack.contains(ingredient);
	}

	public static int size()
	{
		return backpack.size();
	}
}
