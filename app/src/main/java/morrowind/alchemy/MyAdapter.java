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

/**
 * Created by cj on 2015-03-16.
 */
public class MyAdapter extends ArrayAdapter<Ingredient> implements Filterable
{
	private Context context;
	private List<Ingredient> allIngredients;
	private List<Ingredient> filteredIngredients;
	private Filter ingredientFilter;
	private ToggleButton toggleButton;
	private String search = "";

	public MyAdapter(Context context, List<Ingredient> ingredients, ToggleButton toggleButton)
	{
		super(context, R.layout.ingredient_entry, ingredients);
		this.context = context;
		this.allIngredients = new ArrayList<>();
		this.allIngredients.addAll(ingredients);
		this.filteredIngredients = ingredients;
		ingredientFilter = new MyAdapter.IngredientFilter(this);
		this.toggleButton = toggleButton;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		search = search.toLowerCase();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.ingredient_entry, parent, false);
		ImageView ingredientIcon = (ImageView) rowView.findViewById(R.id.ingredientIcon);
		TextView ingredientName = (TextView) rowView.findViewById(R.id.ingredientName);
		LinearLayout innerTable = (LinearLayout) rowView.findViewById(R.id.innerTable);
		if (position >= filteredIngredients.size())
		{
			ingredientName.setText(String.valueOf(position));
			return rowView;
		}
		String s = filteredIngredients.get(position).getIngredientName();
		if (toggleButton!=null && !toggleButton.isChecked() && search != null && !search.equals("")) // by ingredient
		{
			Spannable spanText = Spannable.Factory.getInstance().newSpannable(s);
			if (s.toLowerCase().contains(search))
				spanText.setSpan(new BackgroundColorSpan(0x80FFFF00), s.toLowerCase().indexOf(search), s.toLowerCase().indexOf(search) + search.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			ingredientName.setText(spanText);
		} else ingredientName.setText(s);

		AssetManager am = getContext().getResources().getAssets();
		try
		{
			InputStream is = am.open(filteredIngredients.get(position).getIngredientIcon());
			ingredientIcon.setImageDrawable(Drawable.createFromStream(is, filteredIngredients.get(position).getIngredientIcon()));

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		for (Effect effect : filteredIngredients.get(position).getEffects())
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
			s = effect.getEffectName();
			if (toggleButton!= null &&  toggleButton.isChecked() && search != null && !search.equals("")) // by effect
			{
				Spannable spanText = Spannable.Factory.getInstance().newSpannable(s);
				if (s.toLowerCase().contains(search))
					spanText.setSpan(new BackgroundColorSpan(0x80FFFF00), s.toLowerCase().indexOf(search), s.toLowerCase().indexOf(search) + search.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				row_text.setText(spanText);
			} else row_text.setText(s);
			row_text.setTextColor(Color.WHITE);
			row.addView(row_icon);
			row.addView(row_text);
			innerTable.addView(row);
		}

		return rowView;
	}

	@Override
	public Filter getFilter()
	{
		return ingredientFilter;
	}

	private class IngredientFilter extends Filter
	{
		private ArrayAdapter<Ingredient> arrayAdapter;

		public IngredientFilter(ArrayAdapter<Ingredient> arrayAdapter)
		{
			super();
			this.arrayAdapter = arrayAdapter;
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			search = constraint.toString();
			FilterResults result = new FilterResults();
			ArrayList<Ingredient> filteredList = new ArrayList<Ingredient>();
			if (constraint.length() == 0)
			{
				filteredList.addAll(allIngredients);
			} else
			{
				if (toggleButton!= null && toggleButton.isChecked()) //by effect
				{
					for (Ingredient ingredient : allIngredients)
					{
						for (Effect effect : ingredient.getEffects())
						{
							if (effect.getEffectName().toLowerCase().contains(search) && !filteredList.contains(ingredient))
								filteredList.add(ingredient);
						}
					}
				} else //by ingredient
				{
					for (Ingredient ingredient : allIngredients)
					{
						if (ingredient.getIngredientName().toLowerCase().contains(search))
							filteredList.add(ingredient);
					}
				}

			}
			result.values = filteredList;
			result.count = filteredList.size();
			Log.d("FILTER", constraint + "Found " + result.count + " from " + allIngredients.size() + " ingredients.");
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results)
		{
			Log.d("FILTER", "filteredIngredients counts " + filteredIngredients.size() + " ingredients.");
			Log.d("FILTER", "allIngredients counts " + allIngredients.size() + " ingredients.");
			filteredIngredients = (ArrayList<Ingredient>) results.values;

			arrayAdapter.clear();
			for (Ingredient ingredient : (ArrayList<Ingredient>) results.values)
				arrayAdapter.add(ingredient);
			notifyDataSetChanged();
			Log.d("FILTER", "listView contains " + arrayAdapter.getCount() + " elements.");
		}

	}

}