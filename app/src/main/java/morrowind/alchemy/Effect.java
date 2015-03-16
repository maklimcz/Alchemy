package morrowind.alchemy;

import android.graphics.drawable.Drawable;

/**
 * Created by cj on 2015-03-16.
 */
public class Effect
{
	public String effectName;
	public int effectIcon;

	public Effect(String effectName, int effectIcon)
	{
		this.effectIcon = effectIcon;
		this.effectName = effectName;
	}

	public Effect(String effectName)
	{
		this.effectIcon = 0;
		this.effectName = effectName;
	}

	public String getEffectName(){return effectName;}
	public int getEffectIcon(){return effectIcon;}
}
