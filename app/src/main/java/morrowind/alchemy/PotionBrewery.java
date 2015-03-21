package morrowind.alchemy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import morrowind.alchemy.model.Backpack;
import morrowind.alchemy.model.Effect;
import morrowind.alchemy.model.Ingredient;
import morrowind.alchemy.model.Potion;


public class PotionBrewery extends ActionBarActivity
{
	private ExpandableListView listView;
	private ArrayList<Potion> potions;
	private PotionsAdapterExpandable potionsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_potion_brewery);


		listView = (ExpandableListView) findViewById(R.id.breweryListView);

		potions = brewPotions(Backpack.getBackpack());
		Toast.makeText(this, "Brewed " + potions.size() + " potions.", Toast.LENGTH_SHORT).show();
		potionsAdapter = new PotionsAdapterExpandable(this, potions);
		listView.setAdapter(potionsAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_potion_brewery, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		return super.onOptionsItemSelected(item);
	}

	private ArrayList<Potion> brewPotions(ArrayList<Ingredient> ingredients)
	{
		ArrayList<Potion> potions = new ArrayList<Potion>();

		for(Ingredient ingredient1 : ingredients)
		{
			for(Ingredient ingredient2 : ingredients)
			{
				if(!ingredient1.equals(ingredient2) && !Ingredient.commonEffects(ingredient1, ingredient2).isEmpty())
				{
					Potion potionWithThatEffects = potionWithThatEffects(potions, Ingredient.commonEffects(ingredient1, ingredient2));
					ArrayList<Ingredient> tempIngreds = new ArrayList<>();
					tempIngreds.add(ingredient1);
					tempIngreds.add(ingredient2);
					if(potionWithThatEffects == null)
					{
						potionWithThatEffects = new Potion(Ingredient.commonEffects(ingredient1, ingredient2));
						potionWithThatEffects.addIngredients(tempIngreds);
						potions.add(potionWithThatEffects);
					}
					else if(!potionWithThatEffects.containsThatIngredients(tempIngreds))
					{
						potionWithThatEffects.addIngredients(tempIngreds);
					}

				}
			}
		}
		return potions;
	}

	private Potion potionWithThatEffects(ArrayList<Potion> potions, ArrayList<Effect> effects)
	{
		for(Potion potion : potions)
		{
			if(potion.getEffects().containsAll(effects) && potion.getEffects().size() == effects.size()) return potion;
		}
		return null;
	}

}
