package morrowind.alchemy;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import morrowind.alchemy.model.Effect;
import morrowind.alchemy.model.Ingredient;
import morrowind.alchemy.model.Potion;

/**
 * Created by cj on 2015-03-18.
 */
public class PotionsAdapter extends ArrayAdapter<Potion> implements Filterable
{
	private Context context;
	private List<Potion> potions;

	public PotionsAdapter(Context context, List<Potion> potions)
	{
		super(context, R.layout.potion_entry, potions);
		this.context = context;
		this.potions = potions;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.potion_entry, parent, false);
		ImageView potionIcon = (ImageView) rowView.findViewById(R.id.effectIcon);
		LinearLayout innerTable = (LinearLayout) rowView.findViewById(R.id.innerTableBrewery);
		if (position >= potions.size())
		{
			return rowView;
		}
		AssetManager am = getContext().getResources().getAssets();
		try
		{
			InputStream is = am.open("potion.png");
			potionIcon.setImageDrawable(Drawable.createFromStream(is, "potion.png"));

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		for (Effect effect : potions.get(position).getEffects())
		{
			LinearLayout row = new LinearLayout(context);
			row.setOrientation(LinearLayout.HORIZONTAL);
			ImageView row_icon = new ImageView(context);
			try
			{
				InputStream is = am.open(effect.getEffectIcon());
				row_icon.setImageDrawable(Drawable.createFromStream(is, effect.getEffectIcon()));
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (NullPointerException ex)
			{
				Log.d("Exception", effect.getEffectName() + " : " + ex.getMessage());
			}

			TextView row_text = new TextView(context);
			row_text.setText(effect.getEffectName());
			row_text.setTextColor(Color.WHITE);
			row.addView(row_icon);
			row.addView(row_text);
			innerTable.addView(row);
		}

		for (Ingredient ingredient: potions.get(position).getIngredients())
		{
			LinearLayout row = new LinearLayout(context);
			row.setOrientation(LinearLayout.HORIZONTAL);
			ImageView row_icon = new ImageView(context);
			try
			{
				InputStream is = am.open(ingredient.getIngredientIcon());
				row_icon.setImageDrawable(Drawable.createFromStream(is, ingredient.getIngredientIcon()));
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (NullPointerException ex)
			{
				Log.d("Exception", ingredient.getIngredientName() + " : " + ex.getMessage());
			}

			TextView row_text = new TextView(context);
			row_text.setText(ingredient.getIngredientName());
			row_text.setTextColor(Color.WHITE);
			row.addView(row_icon);
			row.addView(row_text);
			innerTable.addView(row);
		}

		return rowView;
	}

}