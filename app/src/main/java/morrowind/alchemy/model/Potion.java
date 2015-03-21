package morrowind.alchemy.model;

import java.util.ArrayList;

/**
 * Created by cj on 2015-03-18.
 */
public class Potion
{
	private ArrayList<Effect> effects;
	private ArrayList<ArrayList<Ingredient>> ingredientLists;

	public Potion(ArrayList<Effect> effects)
	{
		this.effects = effects;
		ingredientLists = new ArrayList<ArrayList<Ingredient>>();
	}

	public void addIngredients(ArrayList<Ingredient> ingredients)
	{
		this.ingredientLists.add(ingredients);
	}

	public void addEffect(Effect effect)
	{
		effects.add(effect);
	}

	public ArrayList<Effect> getEffects()
	{
		return effects;
	}

	public void setEffects(ArrayList<Effect> effects)
	{
		this.effects = effects;
	}

	public ArrayList<ArrayList<Ingredient>> getIngredientLists()
	{
		return ingredientLists;
	}

	public void setIngredientLists(ArrayList<ArrayList<Ingredient>> ingredientLists)
	{
		this.ingredientLists = ingredientLists;
	}

	public boolean containsThatIngredients(ArrayList<Ingredient> ingredients)
	{
		for(ArrayList<Ingredient> ingredientList : ingredientLists)
		{
			if(ingredientList.containsAll(ingredients)) return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Potion)
		{
			return (((Potion) o).getEffects().containsAll(this.getEffects()));
		}
		return false;
	}
}
