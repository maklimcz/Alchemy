package morrowind.alchemy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filterable;
import android.widget.ImageView;

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
public class PotionsAdapterExpandable extends BaseExpandableListAdapter
{
	private Context context;
	private ArrayList<Potion> potions;

	public PotionsAdapterExpandable(Context context, ArrayList<Potion> potions)
	{
		super();
		this.context = context;
		this.potions=potions;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		Potion potion = potions.get(groupPosition);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.potion_entry_extended, parent, false);
		LinearLayout innerTable = (LinearLayout) convertView.findViewById(R.id.childRow);
		if (groupPosition >= potions.size())
		{
			return convertView;
		}
		for (Ingredient ingredient : potions.get(groupPosition).getIngredientLists().get(childPosition))
		{
			LinearLayout row = new LinearLayout(context);
			row.setOrientation(LinearLayout.HORIZONTAL);
			ImageView row_icon = new ImageView(context);
			try
			{
				AssetManager am = context.getResources().getAssets();
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
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return false;
	}

	@Override
	public int getGroupCount()
	{
		return potions.size();
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return potions.get(groupPosition).getIngredientLists().size();
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return potions.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return potions.get(groupPosition).getIngredientLists().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return 0;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.potion_entry, parent, false);
		ImageView potionIcon = (ImageView) convertView.findViewById(R.id.effectIcon);
		LinearLayout innerTable = (LinearLayout) convertView.findViewById(R.id.innerTableBrewery);
		if (groupPosition >= potions.size())
		{
			return convertView;
		}
		AssetManager am = context.getResources().getAssets();
		try
		{
			InputStream is = am.open("potion.png");
			potionIcon.setImageDrawable(Drawable.createFromStream(is, "potion.png"));

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		for (Effect effect : potions.get(groupPosition).getEffects())
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
		return convertView;
	}

}