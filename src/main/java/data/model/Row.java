/*
 * oleksii.zozulenko
 * 04.06.2009
 */
package data.model;

import java.util.ArrayList;

/**
 * <pre>
 * <b>Creation date:</b>04.06.2009 by oleksii.zozulenko
 * <b>Modification date:</b>04.06.2009 by oleksii.zozulenko
 * </pre>
 * @author oleksii.zozulenko
 *
 */
public class Row
{	
	private ArrayList<String> _lsData;
	
	public Row()
	{		
		this.setData(new ArrayList<String>());
	}
	
	public Row(ArrayList<String> lsData)
	{
		this.setData(lsData);
	}
	
	public void setData(ArrayList<String> lsValue)
	{
		this._lsData = lsValue;
	}
	
	public ArrayList<String> getData()
	{
		return this._lsData;
	}
	
	public void insertData(String sValue)
	{
		this._lsData.add(sValue);
	}
}
