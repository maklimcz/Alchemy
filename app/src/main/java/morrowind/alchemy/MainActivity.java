package morrowind.alchemy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity
{

	private EditText editText;
	private ListView listView;
	private ToggleButton toggleButton;
	private List<Ingredient> ingredients;
	private List<Effect> effects;
	private MyAdapter ingredientsAdapter ;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);
		editText = (EditText) findViewById(R.id.editText);
		toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

		IngredientsParser ingredientsParser = new IngredientsParser(getResources().getXml(R.xml.ingredients));
		EffectsParser effectsParser = new EffectsParser(getResources().getXml(R.xml.effects));
		try
		{
			ingredients = ingredientsParser.parse();
			effects = effectsParser.parse();
		} catch (XmlPullParserException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		for(Ingredient ingredient : ingredients)
		{
			for(Effect effect : ingredient.getEffects())
			{
				for(Effect effect1 : effects)
				{
					if(effect.getEffectName().equals(effect1.getEffectName()))effect.setEffectIcon(effect1.getEffectIcon());
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

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void toggleChanged(View view)
	{
		ingredientsAdapter.getFilter().filter(editText.getText());
	}
}
