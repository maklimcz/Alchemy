package morrowind.alchemy.model;

import java.util.ArrayList;

/**
 * Created by cj on 2015-03-18.
 */
public class Potion
{
	private ArrayList<Effect> effects;
	private ArrayList<Ingredient> ingredients;

	public ArrayList<Effect> getEffects()
	{
		return effects;
	}

	public void setEffects(ArrayList<Effect> effects)
	{
		this.effects = effects;
	}

	public ArrayList<Ingredient> getIngredients()
	{
		return ingredients;
	}

	public void setIngredients(ArrayList<Ingredient> ingredients)
	{
		this.ingredients = ingredients;
	}

	public Potion()
	{
		this.effects = new ArrayList<Effect>();
		this.ingredients = new ArrayList<Ingredient>();
	}

	public Potion(ArrayList<Effect> effects, ArrayList<Ingredient> ingredients)
	{
		this.effects = effects;
		this.ingredients = ingredients;
	}

	public void addEffect(Effect effect)
	{
		if(effects == null) effects = new ArrayList<Effect>();
		if(!effects.contains(effect)) effects.add(effect);
	}

	public void addIngredient(Ingredient ingredient)
	{
		if(ingredients == null) ingredients = new ArrayList<Ingredient>();
		if(!ingredients.contains(ingredient)) ingredients.add(ingredient);
	}

}
