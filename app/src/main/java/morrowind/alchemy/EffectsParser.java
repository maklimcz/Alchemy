package morrowind.alchemy;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 2015-03-15.
 */
public class EffectsParser
{
	// We don't use namespaces
	private static final String ns = null;
	private XmlPullParser xpp;

	public EffectsParser(XmlPullParser xpp)
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
			return readEffects(xpp);
		} finally
		{

		}
	}

	private List readEffects(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		List effects = new ArrayList();
		parser.require(XmlPullParser.START_TAG, ns, "effects");
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("effect"))
			{
				effects.add(readEffect(parser));
			} else
			{
				skip(parser);
			}
		}
		return effects;
	}



	// Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
	private Effect readEffect(XmlPullParser parser) throws XmlPullParserException, IOException
	{
		parser.require(XmlPullParser.START_TAG, ns, "effect");
		String name = null;
		Effect effect = new Effect();
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)	continue;
			String tag = parser.getName();
			if (tag.equals("name"))effect.setEffectName(readName(parser));
			else if (tag.equals("icon"))effect.setEffectIcon(readIcon(parser));
			else skip(parser);
		}
		//Log.d("effect", "parser: " + effect.getEffectName() + " " + effect.getEffectIcon());
		return effect;
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
