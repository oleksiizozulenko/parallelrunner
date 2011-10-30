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
public class Column
{	
	private ArrayList<String> _lsData;
	
	public Column()
	{		
		setData(new ArrayList<String>());
	}

	public Column(ArrayList<String> lsData)
	{
		this.setData(lsData);
	}
	
	/**
	 * <pre>
	 * <b>Creation date:</b>04.06.2009 by oleksii.zozulenko
	 * <b>Modification date:</b>04.06.2009 by oleksii.zozulenko
	 * </pre>
	 * @param  Column _lsData the _lsData to set
	 */
	public void setData(ArrayList<String> _lsData)
	{
		this._lsData = _lsData;
	}

	/**
	 * <pre>
	 * <b>Creation date:</b>04.06.2009 by oleksii.zozulenko
	 * <b>Modification date:</b>04.06.2009 by oleksii.zozulenko
	 * </pre>
	 * @return the  Column _lsData
	 */
	public ArrayList<String> getData()
	{
		return _lsData;
	}
	
	public void insertData(String sValue)
	{
		this._lsData.add(sValue);
	}
}
