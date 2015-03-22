package morrowind.alchemy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;

import java.util.ArrayList;

import morrowind.alchemy.model.Backpack;
import morrowind.alchemy.model.Ingredient;


public class ShowBackpack extends ActionBarActivity
{
	private DynamicListView listView;
	private MyAdapter ingredientsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_backpack);

		listView = (DynamicListView) findViewById(R.id.backpackListView);

		registerForContextMenu(listView);

		ingredientsAdapter = new MyAdapter(this, Backpack.getBackpack(), null);
		listView.setAdapter(ingredientsAdapter);
        listView.enableSwipeToDismiss(
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            removeItem(ingredientFromPosition(position));
                        }
                    }
                }
        );	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_show_backpack, menu);
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
		if (id == R.id.brewPotionsMenuItem)
		{
			brewPotions(null);
			return true;
		}

		else if (id == R.id.clearBackpackMenuItem)
		{
			Backpack.clear();
			ingredientsAdapter.notifyDataSetChanged();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu_backpack, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId())
		{
			case R.id.removeFromBackpack:
				int pos = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
				Ingredient ingredient = ingredientFromPosition(pos);

                //Czy to sprawdzenie jest konieczne?
				if(Backpack.contains(ingredient))
				{
                    removeItem(ingredient);
				}

				return true;

			default:
				return super.onContextItemSelected(item);
		}
	}

	public void brewPotions(View view)
	{
		Intent intent = new Intent(this, PotionBrewery.class);
		startActivity(intent);
	}

    private Ingredient ingredientFromPosition(int pos) {
        return ((Ingredient)listView.getAdapter().getItem(pos));
    }

    private void removeItem(Ingredient ingredient) {
        Toast.makeText(this, ingredient.getIngredientName() + " usunieto z plecaka.", Toast.LENGTH_SHORT).show();
        Backpack.remove(ingredient);
        ingredientsAdapter.notifyDataSetChanged();
    }

}
