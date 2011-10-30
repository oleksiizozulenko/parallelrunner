/**
 * <pre>
 * <b>Creation date:</b>31.03.2009 by oleksii.zozulenko
 * <b>Modification date:</b>31.03.2009 by oleksii.zozulenko
 * </pre>
 * 
 */
package project.ui;

import com.thoughtworks.selenium.Selenium;

/**
 * <pre>
 * <b>Creation date:</b>31.03.2009 by oleksii.zozulenko
 * <b>Modification date:</b>31.03.2009 by oleksii.zozulenko
 * </pre>
 * @author oleksii.zozulenko
 *
 */
public class SeleniumTableUtils
{
	private static Selenium _oSelenium;
	
	@SuppressWarnings("static-access")
	public SeleniumTableUtils(Selenium oSelenium)
	{
		this._oSelenium = oSelenium;
	}
	
	/** 
	 * <pre>
	 * Function get date form cell
	 * <b>Creation date:</b>06.04.2009 by oleksii.zozulenko
	 * <b>Modification date:</b>06.04.2009 by oleksii.zozulenko
	 * </pre>
	 * @param sTableLocator
	 * @param iRow -- Table row (start from 0) 
	 * @param iCol  -- Column number (start from 0)
	 * @param Selenium oSelenium
	 * @return String
	 */
	public String getData(String sTableLocator, int iRow, int iCol)
	{
		//tablelocator.row.col
		return _oSelenium.getTable(sTableLocator + "." + iRow + "." + iCol);
	}

	/**
	 * 
	 * <pre>
	 * Function get cell number by text in this cell.
	 * <b>Creation date:</b>06.04.2009 by oleksii.zozulenko
	 * <b>Modification date:</b>06.04.2009 by oleksii.zozulenko
	 * </pre>
	 * @param sTableLocator - table element on page
	 * @param sCellValue - value what you want found
	 * @param Selenium oSelenium
	 * @return project.structs.Table (return 0,0 if data not found in table)
	 */
	public data.model.Table getCell(String sTableLocator, String sCellValue)
	{
		data.model.Table oTable = new data.model.Table();
		int iCol = 1;
		int iRow = 1;
		
		String sPreparedLct = sTableLocator.replaceFirst("//", "").replaceFirst("descendant::", "");
		
		oTable.setColNum(iCol);
		oTable.setRowNum(iRow);
		
		String sTableRowLct = "//descendant::" + sPreparedLct + "//tr[" + (iRow) + "]";
		
		while (_oSelenium.isElementPresent(sTableRowLct))
		{
			String sTableColLct = sTableRowLct + "/td[" + (iCol) + "]";
			
			while (_oSelenium.isElementPresent(sTableColLct))
			{			
				String sValue = _oSelenium.getText(sTableColLct);
				//System.out.println("Value: `" + sValue + "`");
				
				if (sValue.equalsIgnoreCase(sCellValue))
				{
					oTable.setColNum(iCol);
					oTable.setRowNum(iRow);
					return oTable;
				}
				iCol++;
				sTableColLct = sTableRowLct + "/td[" + (iCol) + "]";
			}
			iCol = 1;
			iRow++;
			sTableRowLct = "//descendant::" + sPreparedLct + "//tr[" + (iRow) + "]";
			
		}
		
		return oTable;
	}
	
	
	public data.model.Column getColumnData(String sTableLct, int iColNum)
	{
		data.model.Column oCol = new data.model.Column();
		int k = 1;
		
		String sLocator = sTableLct + "//tr[" + k + "]/td[" + iColNum + "]";
		
		if (!_oSelenium.isElementPresent(sLocator))
		{
			return null;
		}

		while (_oSelenium.isElementPresent(sLocator))
		{
			
			String sValue = _oSelenium.getText(sLocator);
			
			oCol.insertData(sValue);
			k++;
			sLocator = sTableLct + "//tr[" + k + "]/td[" + iColNum + "]";
			
		}
		
		return oCol;
	}
	
	/**
	 * Function grab all data from row in table.
	 * 
	 * Note: This function use `getText` be careful. Function works very slowly
	 *  
	 * @param sTableLct
	 * @param iRowNum
	 * @return
	 */
	public data.model.Row getRowData(String sTableLct, int iRowNum)
	{
		data.model.Row oRow = new data.model.Row();
		int k = 1;
		
		String sRowLocator = "//descendant::" + sTableLct.replaceFirst("//", "") + "//tr[" + iRowNum + "]";
		if (!_oSelenium.isElementPresent(sRowLocator))
		{
			return null;
		}
		
		String sCellLocator = sRowLocator + "/td[" + k+ "]";
		while (_oSelenium.isElementPresent(sCellLocator))
		{
			
			String sValue = _oSelenium.getText(sCellLocator);
			
			oRow.insertData(sValue);
			sCellLocator = sRowLocator + "/td[" + k+ "]";
			k++;
		}
		
		
		return oRow;
	}
	
	
	
}
