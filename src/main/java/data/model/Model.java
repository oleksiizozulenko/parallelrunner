/**
 * 
 */
package data.model;

/**
 * @author oleksii.zozulenko
 */
public abstract class Model
{
	public boolean isSet(Class<? extends Model> target)
	{
		if (target != null && !target.equals(this))
		{
			return true;
		}
		return false;
	}
}
