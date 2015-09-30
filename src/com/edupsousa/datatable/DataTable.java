package com.edupsousa.datatable;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DataTable {

	public static final int TYPE_INT = 0;
	public static final int TYPE_STRING = 1;
	
	public static final int FORMAT_CSV = 0;
	public static final int FORMAT_HTML = 1;
	
	private LinkedHashMap<String, Integer> columnsTypes = new LinkedHashMap<String, Integer>();
	private ArrayList<DataTableRow> rows = new ArrayList<DataTableRow>();
	
	public int columnsCount() {
		return columnsTypes.size();
	}

	public int rowsCount() {
		return rows.size();
	}

	public void addCollumn(String name, int type) {
		columnsTypes.put(name, type);
	}

	public boolean hasCollumn(String name) {
		return columnsTypes.containsKey(name);
	}

	public DataTableRow createRow() {
		return new DataTableRow(this);
	}

	public void insertRow(DataTableRow row) {
		checkRowCompatibilityAndThrows(row);
		rows.add(row);
	}

	public DataTableRow lastRow() {
		return rows.get(rows.size()-1);
	}

	public int getCollumnType(String collumn) {
		return columnsTypes.get(collumn);
	}
	
	private void checkRowCompatibilityAndThrows(DataTableRow row) {
		for (String collumnName : columnsTypes.keySet()) {
			if (row.hasValueFor(collumnName) && 
					!(isValueCompatible(columnsTypes.get(collumnName), row.getValue(collumnName)))) {
				throw new ClassCastException("Wrong type for collumn " + collumnName + ".");
			}
		}
	}
	
	private boolean isValueCompatible(int type, Object value) {
		if (type == this.TYPE_INT && !(value instanceof Integer)) {
			return false;
		} else if (type == this.TYPE_STRING && !(value instanceof String)) {
			return false;
		}
		return true;
	}

	public DataTableRow getRow(int i) {
		return rows.get(i);
	}

	public String export(int format) {
		DataTableRow row;
		String output = "";
		if (format == DataTable.FORMAT_CSV) {
			for (String collumnName : columnsTypes.keySet()) {
				output += collumnName + ";";
			}
			output += "\n";
			for (int i = 0; i < this.rowsCount(); i++) {
				row = this.getRow(i);
				for (String collumnName : columnsTypes.keySet()) {
					if (columnsTypes.get(collumnName) == DataTable.TYPE_STRING) {
						output += "\"" + row.getValue(collumnName) + "\";";
					} else {
						output += row.getValue(collumnName) + ";";
					}
				}
				output += "\n";
			}
		}else if (format==FORMAT_HTML) {
			output += "<table>\n<tr>";
			for (String collumnName : columnsTypes.keySet()) {
				output += "<td>" + collumnName + "</td>";
			}
			output += "</tr>\n";
			for (int i = 0; i < this.rowsCount(); i++) {
				output += "<tr>";
				row = this.getRow(i);
				for (String collumnName : columnsTypes.keySet()) {
					output += "<td>" + row.getValue(collumnName) + "</td>";
				}
				output += "</tr>\n";
			}
			output += "</table>\n";
		}
 		return output;
		
		
	}

	public void insertRowAt(DataTableRow row, int index) {
		rows.add(index, row);
	}
	
	public DataTable filterEqual(String collumn, Object value) {
		return null;
	}
	
	public DataTable sortAscending(String collumn) {
		return null;
	}
	public DataTable sortDescending(String collumn) {
		
		return null;
	}
}
