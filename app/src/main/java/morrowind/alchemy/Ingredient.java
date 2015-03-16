package morrowind.alchemy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 2015-03-16.
 */
public class Ingredient
{
	public String ingredientName;
	public List<Effect> effects;
	public int ingredientIcon;

	public Ingredient(String ingredientName, int ingredientIcon, List<Effect> effects)
	{
		this.ingredientName = ingredientName;
		this.ingredientIcon = ingredientIcon;
		this.effects = effects;
	}

	public Ingredient(String ingredientName, int ingredientIcon, String[] effect_names)
	{
		this.ingredientName = ingredientName;
		this.ingredientIcon = ingredientIcon;
		this.effects = new ArrayList<>();
		for(String effect_name : effect_names) this.effects.add(new Effect(effect_name, 0));
	}

	public String getIngredientName(){return ingredientName;}
	public List<Effect> getEffects(){return effects;}
	public int getIngredientIcon(){return ingredientIcon;}
}
