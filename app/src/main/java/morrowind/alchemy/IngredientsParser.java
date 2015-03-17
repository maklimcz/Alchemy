package morrowind.alchemy;

import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 2015-03-15.
 */
public class IngredientsParser
{
	// We don't use namespaces
	private static final String ns = null;
	private XmlPullParser xpp;

	public IngredientsParser(XmlPullParser xpp)
	{
		super();
		this.xpp =  xpp;
	}

	public List parse() throws XmlPullParserException, IOException
	{
		try
		{
			xpp.next();
			xpp.next();
			return readIngredients(xpp);
		} finally
		{

		}
	}

	private List readIngredients(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		List ingredients = new ArrayList();
		parser.require(XmlPullParser.START_TAG, ns, "ingredients");
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("ingredient"))
			{
				ingredients.add(readIngredient(parser));
			} else
			{
				skip(parser);
			}
		}
		return ingredients;
	}



	// Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
	private Ingredient readIngredient(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, ns, "ingredient");
		String name = null;
		Ingredient ingredient = new Ingredient();
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)	continue;
			String tag = parser.getName();
			if (tag.equals("name"))ingredient.setIngredientName(readName(parser));
			else if (tag.equals("icon"))ingredient.setIngredientIcon(readIcon(parser));
			else if (tag.equals("weight"))ingredient.setIngredientWeight(readWeight(parser));
			else if (tag.equals("value"))ingredient.setIngredientValue(readValue(parser));
			else if (tag.equals("effect"))ingredient.addEffect(readEffect(parser));
			else skip(parser);
		}
		return ingredient;
	}

	private String readName(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, ns, "name");
		String content = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "name");
		return content;
	}

	private String readIcon(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, ns, "icon");
		String content = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "icon");
		return content;
	}

	private float readWeight(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, ns, "weight");
		String content = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "weight");
		return Float.parseFloat(content);
	}

	private int readValue(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, ns, "value");
		String content = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "value");
		return Integer.parseInt(content);
	}
	private Effect readEffect(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		parser.require(XmlPullParser.START_TAG, ns, "effect");
		String content = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "effect");
		Effect effect = new Effect();
		effect.setEffectName(content);
		return effect;
	}

	// For the tags title and summary, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException
	{
		String result = "";
		if (parser.next() == XmlPullParser.TEXT)
		{
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		if (parser.getEventType() != XmlPullParser.START_TAG)
		{
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0)
		{
			switch (parser.next())
			{
				case XmlPullParser.END_TAG:
					depth--;
					break;
				case XmlPullParser.START_TAG:
					depth++;
					break;
			}
		}
	}

}
