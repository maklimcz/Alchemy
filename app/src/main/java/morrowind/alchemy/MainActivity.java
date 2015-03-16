package morrowind.alchemy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity
{

	private ListView listView;
	private List<Ingredient> ingredients;
	private MyAdapter ingredientsAdapter ;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);

		MyXMLParser myXMLParser = new MyXMLParser(getResources().getXml(R.xml.ingredients));
		try
		{
			ingredients = myXMLParser.parse();
		} catch (XmlPullParserException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		/*
		AlchemyDatabaseHandler alchemyDatabaseHandler = new AlchemyDatabaseHandler(this);
		//alchemyDatabaseHandler.addIngredient(new Ingredient("Kot",R.drawable.nyan , new String[] {"Miauu"}));
		Log.d("Reading: ", "Reading all ingredients...");
		ingredients = alchemyDatabaseHandler.getIngredients();
		for (Ingredient ingredient : ingredients)
		{
			String log = "Name: " + ingredient.getIngredientName() + " icon: " + ingredient.getIngredientIcon() + " effects: ";
			for(Effect e : ingredient.getEffects()) log += e.getEffectName() + ", ";
			Log.d("Ingredient: ", log);
		}
		*/


		ingredientsAdapter = new MyAdapter(this, ingredients);
		listView.setAdapter(ingredientsAdapter);
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

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
