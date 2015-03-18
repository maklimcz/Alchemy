package morrowind.alchemy;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 2015-03-16.
 */
public class MyAdapter extends ArrayAdapter<Ingredient> implements Filterable
{
	private Context context;
	private List<Ingredient> allIngredients;
	private List<Ingredient> filteredIngredients;
	private Filter ingredientFilter = new MyAdapter.IngredientFilter();

	public MyAdapter(Context context, List<Ingredient> ingredients)
	{
		super(context, R.layout.ingredient_entry, ingredients);
		this.context = context;
		this.allIngredients = ingredients;
		this.filteredIngredients = new ArrayList<Ingredient>();
		this.filteredIngredients.addAll(allIngredients);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

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

		ingredientName.setText(filteredIngredients.get(position).getIngredientName());

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
			row_text.setText(effect.getEffectName());
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
		@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			FilterResults result = new FilterResults();
			ArrayList<Ingredient> filteredList = new ArrayList<Ingredient>();
			if (constraint == null || constraint.length() == 0)
			{
				filteredList.addAll(allIngredients);
			} else
			{
				for (Ingredient ingredient : allIngredients)
				{
					if (ingredient.getIngredientName().toLowerCase().startsWith(constraint.toString()))
						filteredList.add(ingredient);
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
			filteredIngredients.clear();
			filteredIngredients.addAll((ArrayList<Ingredient>) results.values);
			Log.d("FILTER", "filteredIngredients counts " + filteredIngredients.size() + " ingredients.");
			notifyDataSetChanged();
		}

	}

}