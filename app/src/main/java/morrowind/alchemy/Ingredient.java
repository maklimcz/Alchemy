package morrowind.alchemy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 2015-03-16.
 */
public class Ingredient
{
	private int ingredientID;
	private String ingredientName;
	private List<Effect> effects;

	private String ingredientIcon;
	private float ingredientWeight;
	private int ingredientValue;

	public Ingredient()	{}
	public Ingredient(int ingredientID, String ingredientName, String ingredientIcon, String[] effect_names, float ingredientWeight, int ingredientValue)
	{
		this.ingredientID = ingredientID;
		this.ingredientName = ingredientName;
		this.ingredientIcon = ingredientIcon;
		this.effects = new ArrayList<>();
		for(String effect_name : effect_names) this.effects.add(new Effect(effect_name, 0));
		this.ingredientWeight = ingredientWeight;
		this.ingredientValue = ingredientValue;
	}

	public String getIngredientName()
	{
		return ingredientName;
	}

	public List<Effect> getEffects()
	{
		return effects;
	}

	public String getIngredientIcon()
	{
		return ingredientIcon;
	}

	public int getIngredientValue()
	{
		return ingredientValue;
	}

	public void setIngredientValue(int ingredientValue)
	{
		this.ingredientValue = ingredientValue;
	}

	public float getIngredientWeight()
	{
		return ingredientWeight;
	}

	public void setIngredientWeight(float ingredientWeight)
	{
		this.ingredientWeight = ingredientWeight;
	}

	public void setIngredientIcon(String ingredientIcon)
	{
		this.ingredientIcon = ingredientIcon;
	}

	public int getIngredientID()
	{
		return ingredientID;
	}

	public void setIngredientID(int ingredientID)
	{
		this.ingredientID = ingredientID;
	}

	public void setEffects(List<Effect> effects)
	{
		this.effects = effects;
	}

	public void setIngredientName(String ingredientName)
	{
		this.ingredientName = ingredientName;
	}

	public void addEffect(Effect effect)
	{
		if(this.effects == null) this.effects = new ArrayList<Effect>();
		this.effects.add(effect);
	}
}
