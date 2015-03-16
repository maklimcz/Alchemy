package morrowind.alchemy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 2015-03-16.
 */
public class AlchemyDatabaseHandler extends SQLiteOpenHelper
{

	public AlchemyDatabaseHandler(Context context)
	{
		super(context, "alchemyDatabase", null, 1);
	}

	public AlchemyDatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("drop table if exists ingredients");
		db.execSQL("create table ingredients (id integer primary key, name text, icon integer, e1 text, e2 text, e3 text, e4 text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists ingredients");
		onCreate(db);
	}

	public void addIngredient(Ingredient ingredient) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		try
		{
			values.put("name", ingredient.getIngredientName());
			values.put("icon", ingredient.getIngredientIcon());
			values.put("e1", ingredient.getEffects().get(0).getEffectName());
			values.put("e2", ingredient.getEffects().get(1).getEffectName());
			values.put("e3", ingredient.getEffects().get(2).getEffectName());
			values.put("e4", ingredient.getEffects().get(3).getEffectName());
		}
		catch(Exception ex)
		{
			Log.e("dbException", ex.getMessage());
		}
		db.insert("ingredients", null, values);
		db.close();
	}

	public List<Ingredient> getIngredients() {
		SQLiteDatabase db = this.getWritableDatabase();
		List<Ingredient> ingredients = new ArrayList<>();

		String getPost = "select * from ingredients order by name;";
		Cursor cursor = getReadableDatabase().rawQuery(getPost, null);

		if (cursor.moveToFirst()) {
			do {
				String [] effect_names = { cursor.getString(3) , cursor.getString(4), cursor.getString(5), cursor.getString(6)};
				Ingredient ingredient = new Ingredient(cursor.getString(1), cursor.getInt(2), effect_names );
				ingredients.add(ingredient);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return ingredients;
	}

}
