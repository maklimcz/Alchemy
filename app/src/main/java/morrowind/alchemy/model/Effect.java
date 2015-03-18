package morrowind.alchemy.model;

import java.io.Serializable;

/**
 * Created by cj on 2015-03-16.
 */
public class Effect implements Serializable
{
	public Effect(){}

	public String getEffectName()
	{
		return effectName;
	}

	public void setEffectName(String effectName)
	{
		this.effectName = effectName;
	}

	public String getEffectIcon()
	{
		return effectIcon;
	}

	public void setEffectIcon(String effectIcon)
	{
		this.effectIcon = effectIcon;
	}

	private String effectName;
	private String effectIcon;

	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Effect) return effectName.equals(((Effect) o).getEffectName());
		else return false;
	}
}
