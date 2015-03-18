package morrowind.alchemy.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cj on 2015-03-18.
 */
public class Backpack implements Serializable
{
	private ArrayList<Ingredient> backpack;

	public Backpack(ArrayList<Ingredient> backpack)
	{
		this.backpack = backpack;
	}

	public ArrayList<Ingredient> getBackpack()
	{
		return backpack;
	}
}
