package morrowind.alchemy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import morrowind.alchemy.model.Backpack;
import morrowind.alchemy.model.Effect;
import morrowind.alchemy.model.Ingredient;
import morrowind.alchemy.parsers.EffectsParser;
import morrowind.alchemy.parsers.IngredientsParser;


public class MainActivity extends ActionBarActivity
{

	private EditText editText;
	private ListView listView;
	private ToggleButton toggleButton;
	private ArrayList<Ingredient> ingredients;
	private ArrayList<Ingredient> backpack;
	private ArrayList<Effect> effects;
	private MyAdapter ingredientsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);
		registerForContextMenu(listView);
		editText = (EditText) findViewById(R.id.editText);
		toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

		IngredientsParser ingredientsParser = new IngredientsParser(getResources().getXml(R.xml.ingredients));
		EffectsParser effectsParser = new EffectsParser(getResources().getXml(R.xml.effects));
		try
		{
			ingredients = ingredientsParser.parse();
			effects = effectsParser.parse();
			backpack = new ArrayList<Ingredient>();
		} catch (XmlPullParserException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		for (Ingredient ingredient : ingredients)
		{
			for (Effect effect : ingredient.getEffects())
			{
				for (Effect effect1 : effects)
				{
					if (effect.getEffectName().equals(effect1.getEffectName()))
						effect.setEffectIcon(effect1.getEffectIcon());
				}
			}
		}

		ingredientsAdapter = new MyAdapter(this, ingredients, toggleButton);
		listView.setAdapter(ingredientsAdapter);
		editText.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				ingredientsAdapter.getFilter().filter(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		return true;
	}

	public void toggleChanged(View view)
	{
		ingredientsAdapter.getFilter().filter(editText.getText());
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId())
		{
			case R.id.addToBackpack:
				int pos = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
				Ingredient ingredient = ((Ingredient)listView.getAdapter().getItem(pos));
				if(backpack.contains(ingredient))
				{
					Toast.makeText(this, ingredient.getIngredientName() + " juz jest w plecaku.", Toast.LENGTH_SHORT).show();
				}
					else
				{
					backpack.add(ingredient);
					Toast.makeText(this, ingredient.getIngredientName() + " dodano do plecaka.", Toast.LENGTH_SHORT).show();
				}
				return true;
			case R.id.showBackpack:
				showBackpack(null);
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	public void showBackpack(View view)
	{
		Intent intent = new Intent(this, ShowBackpack.class);
		intent.putExtra("backpack", new Backpack(backpack));
		startActivity(intent);
	}
}
