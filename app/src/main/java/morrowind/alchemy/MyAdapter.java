package morrowind.alchemy;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by cj on 2015-03-16.
 */
public class MyAdapter extends ArrayAdapter<Ingredient>
{
	private final Context context;
	private final List<Ingredient> ingredients;

	public MyAdapter(Context context, List<Ingredient> ingredients)
	{
		super(context, R.layout.ingredient_entry, ingredients);
		this.context = context;
		this.ingredients = ingredients;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.ingredient_entry, parent, false);
		ImageView ingredientIcon = (ImageView) rowView.findViewById(R.id.ingredientIcon);
		TextView ingredientName = (TextView) rowView.findViewById(R.id.ingredientName);

		ImageView [] effect_icons = new ImageView[4];
		TextView [] effect_names = new TextView[4];

		effect_icons[0] = (ImageView) rowView.findViewById(R.id.effect1_icon);
		 effect_names[0] = (TextView) rowView.findViewById(R.id.effect1_name);

		effect_icons[1] = (ImageView) rowView.findViewById(R.id.effect2_icon);
		 effect_names[1] = (TextView) rowView.findViewById(R.id.effect2_name);

		effect_icons[2] = (ImageView) rowView.findViewById(R.id.effect3_icon);
		 effect_names[2] = (TextView) rowView.findViewById(R.id.effect3_name);

		effect_icons[3] = (ImageView) rowView.findViewById(R.id.effect4_icon);
		 effect_names[3] = (TextView) rowView.findViewById(R.id.effect4_name);

		ingredientName.setText(ingredients.get(position).getIngredientName());

		AssetManager am = getContext().getResources().getAssets();
		try {
			InputStream is = am.open(ingredients.get(position).getIngredientIcon());
			ingredientIcon.setImageDrawable(Drawable.createFromStream(is, ingredients.get(position).getIngredientIcon()));

		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0 ; i < 4 ; ++i)
		{
			try
			{
				effect_names[i].setText(ingredients.get(position).getEffects().get(i).getEffectName());
				InputStream is = am.open(ingredients.get(position).getEffects().get(i).getEffectIcon());
				effect_icons[i].setImageDrawable(Drawable.createFromStream(is, ingredients.get(position).getEffects().get(i).getEffectIcon()));
			}
			catch(Exception ex)
			{
				Log.d("exception", ex.getMessage());
				effect_names[i].setText("");
				effect_icons[i].setImageDrawable(null);
			}

		}
		return rowView;
	}
}