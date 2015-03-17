package morrowind.alchemy;

/**
 * Created by cj on 2015-03-16.
 */
public class Effect
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

}
