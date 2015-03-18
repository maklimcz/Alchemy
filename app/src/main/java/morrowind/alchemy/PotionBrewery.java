package morrowind.alchemy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import morrowind.alchemy.model.Backpack;
import morrowind.alchemy.model.Effect;
import morrowind.alchemy.model.Ingredient;
import morrowind.alchemy.model.Potion;


public class PotionBrewery extends ActionBarActivity
{
	
	private ArrayList<Ingredient> backpack;
	private ListView listView;
	private ArrayList<Potion> potions;
	private PotionsAdapter potionsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_potion_brewery);

		backpack = (ArrayList<Ingredient>) ( (Backpack) getIntent().getSerializableExtra("backpack") ).getBackpack();
		listView = (ListView) findViewById(R.id.breweryListView);

		registerForContextMenu(listView);

		potions = brewPotions(backpack);
		Toast.makeText(this, "Brewed " + potions.size() + " potions.", Toast.LENGTH_SHORT).show();
		potionsAdapter = new PotionsAdapter(this, potions);
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
		
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private ArrayList<Potion> brewPotions(ArrayList<Ingredient> ingredients)
	{
		ArrayList<Potion> potions = new ArrayList<Potion>();
		ArrayList<Potion> result = new ArrayList<Potion>();
		for(Ingredient ingredient : ingredients)
		{
			for(Effect effect : ingredient.getEffects())
			{
				if(potionsWithThatEffect(potions, effect).isEmpty())
				{
					Potion potion = new Potion();
					potion.addEffect(effect);
					potion.addIngredient(ingredient);
					potions.add(potion);
				}
				else
				{
					for(Potion potionWithThatEffect : potionsWithThatEffect(potions, effect))
						potionWithThatEffect.addIngredient(ingredient);
				}
			}
		}
		for(Potion potion : potions)
		{
			if(potion.getIngredients().size() >= 2) result.add(potion);
		}

		return result;
	}

	private ArrayList<Potion> potionsWithThatEffect(ArrayList<Potion> potions, Effect effect)
	{
		ArrayList<Potion> potionsWithThatEffect = new ArrayList<Potion>();
		for(Potion potion : potions)
		{
			for(Effect effect1 : potion.getEffects())
				if(effect1.equals(effect)) potionsWithThatEffect.add(potion);
		}
		return potionsWithThatEffect;
	}
}
