package data.model;

public class Table
{
	private int _iRowNum;
	private int _iColNum;
	private Column _oCol;
	private Row _oRow;
	private TableData _oData;
	
	public Table()
	{
		this.setRowNum(0);
		this.setColNum(0);
	}

	/**
	 * <pre>
	 * <b>Creation date:</b>31.03.2009 by oleksii.zozulenko
	 * <b>Modification date:</b>31.03.2009 by oleksii.zozulenko
	 * </pre>
	 * @param _iRowNum the _iRow to set
	 */
	public void setRowNum(int iRow)
	{
		this._iRowNum = iRow;
	}

	/**
	 * <pre>
	 * <b>Creation date:</b>31.03.2009 by oleksii.zozulenko
	 * <b>Modification date:</b>31.03.2009 by oleksii.zozulenko
	 * </pre>
	 * @return the _iRow
	 */
	public int getRowNum()
	{
		return _iRowNum;
	}

	/**
	 * <pre>
	 * <b>Creation date:</b>31.03.2009 by oleksii.zozulenko
	 * <b>Modification date:</b>31.03.2009 by oleksii.zozulenko
	 * </pre>
	 * @param _iColNum the _iCol to set
	 */
	public void setColNum(int iCol)
	{
		this._iColNum = iCol;
	}

	/**
	 * <pre>
	 * <b>Creation date:</b>31.03.2009 by oleksii.zozulenko
	 * <b>Modification date:</b>31.03.2009 by oleksii.zozulenko
	 * </pre>
	 * @return the _iCol
	 */
	public int getColNum()
	{
		return _iColNum;
	}
	
	private Cell [][] tableCells;
	
	public Cell getCell(int col, int row)
	{		
		return tableCells[col][row];
	}
	
	public void setCell(int col, int row, String value)
	{
		tableCells[col][row] = new Cell();
		tableCells[col][row].setCol(col);
		tableCells[col][row].setRow(row);
		tableCells[col][row].setValue(value);
	}
	
	protected class Cell
	{
		private int row;
		private int col;
		private String value;
		/**
		 * @param row the row to set
		 */
		public void setRow(int row)
		{
			this.row = row;
		}
		/**
		 * @return the row
		 */
		public int getRow()
		{
			return row;
		}
		/**
		 * @param col the col to set
		 */
		public void setCol(int col)
		{
			this.col = col;
		}
		/**
		 * @return the col
		 */
		public int getCol()
		{
			return col;
		}
		/**
		 * @param value the value to set
		 */
		public void setValue(String value)
		{
			this.value = value;
		}
		/**
		 * @return the value
		 */
		public String getValue()
		{
			return value;
		}
		
	}
}
