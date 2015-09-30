package com.edupsousa.datatable.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.edupsousa.datatable.DataTable;
import com.edupsousa.datatable.DataTableRow;

public class DataTableTest {

	DataTable dt;
	
	@Before
	public void instanceDataTable() {
		dt = new DataTable();
	}
	
	@Test
	public void emptyDataTable() {
		assertEquals(0, dt.columnsCount());
		assertEquals(0, dt.rowsCount());
	}
	
	@Test
	public void addCollumn() {
		dt.addCollumn("id", DataTable.TYPE_INT);
		assertEquals(1, dt.columnsCount());
		assertTrue(dt.hasCollumn("id"));
	}
	
	@Test
	public void addRow() {
		dt.addCollumn("id", DataTable.TYPE_INT);
		DataTableRow row = dt.createRow();
		row.setValue("id", 1);
		dt.insertRow(row);
		
		assertEquals(1, dt.rowsCount());
		assertEquals(1, dt.lastRow().getValue("id"));
	}
	
	@Test
	public void wrongTypeToIntegerCollumn() {
		dt.addCollumn("id", DataTable.TYPE_INT);
		DataTableRow row = dt.createRow();
		row.setValue("id", "A");
		try {
			dt.insertRow(row);
		} catch (ClassCastException e) {
			assertEquals("Wrong type for collumn id.", e.getMessage());
			return;
		}
		fail();
	}
	
	@Test
	public void wrongTypeToStringCollumn() {
		dt.addCollumn("name", DataTable.TYPE_STRING);
		DataTableRow row = dt.createRow();
		row.setValue("name", 1);
		try {
			dt.insertRow(row);
		} catch (ClassCastException e) {
			assertEquals("Wrong type for collumn name.", e.getMessage());
			return;
		}
		fail();
	}
	
	@Test
	public void add10RowsAndVerifyOrder() {
		DataTableRow row;
		dt.addCollumn("id", DataTable.TYPE_INT);
		dt.addCollumn("name", DataTable.TYPE_STRING);
		
		for (int i = 0; i < 10; i++) {
			row = dt.createRow();
			row.setValue("id", i);
			row.setValue("name", "row" + i);
			dt.insertRow(row);
		}
		
		for (int i = 0; i < 10; i++) {
			row = dt.getRow(i);
			assertEquals(i, row.getValue("id"));
			assertEquals("row" + i, row.getValue("name"));
		}
	}
	
	@Test
	public void exportToCSV() {
		DataTableRow row;
		dt.addCollumn("id", DataTable.TYPE_INT);
		dt.addCollumn("name", DataTable.TYPE_STRING);
		
		for (int i = 0; i < 3; i++) {
			row = dt.createRow();
			row.setValue("id", i);
			row.setValue("name", "row" + i);
			dt.insertRow(row);
		}
		String csvOutput = dt.export(DataTable.FORMAT_CSV);
		assertEquals("id;name;\n0;\"row0\";\n1;\"row1\";\n2;\"row2\";\n", csvOutput);
	}
	
	@Test
	public void exportToHTML() {
		DataTableRow row;
		dt.addCollumn("id", DataTable.TYPE_INT);
		dt.addCollumn("name", DataTable.TYPE_STRING);
		
		for (int i = 0; i < 3; i++) {
			row = dt.createRow();
			row.setValue("id", i);
			row.setValue("name", "row" + i);
			dt.insertRow(row);
		}
		String expectedHtml = "<table>\n";
		expectedHtml += "<tr><td>id</td><td>name</td></tr>\n";
		expectedHtml += "<tr><td>0</td><td>row0</td></tr>\n";
		expectedHtml += "<tr><td>1</td><td>row1</td></tr>\n";
		expectedHtml += "<tr><td>2</td><td>row2</td></tr>\n";
		expectedHtml += "</table>\n";
		String htmlOutput = dt.export(DataTable.FORMAT_HTML);
		assertEquals(expectedHtml, htmlOutput);
	}
	
	@Test
	public void filterRowsEqual() {
		DataTableRow row;
		dt.addCollumn("id", DataTable.TYPE_INT);
		dt.addCollumn("class", DataTable.TYPE_STRING);
		
		for (int i = 1; i <= 100; i++) {
			row = dt.createRow();
			row.setValue("id", i);
			row.setValue("class", (i % 2 == 0 ? "even" : "odd"));
			dt.insertRow(row);
		}
		
		DataTable filteredTable = dt.filterEqual("class", "even");
		assertEquals(50, filteredTable.rowsCount());
		for (int i = 0; i < 50; i++) {
			row = filteredTable.getRow(i);
			assertEquals((i+1)*2, row.getValue("id"));
			assertEquals("even", row.getValue("class"));
		}
	}
	
	
	@Test
	public void sortRowsAscending() {
		DataTableRow row;
		dt.addCollumn("id", DataTable.TYPE_INT);
		dt.addCollumn("number", DataTable.TYPE_INT);
		
		for (int i = 0; i < 5; i++) {
			row = dt.createRow();
			row.setValue("id", i);
			row.setValue("number", 4-i);
			dt.insertRow(row);
		}
		
		DataTable sortedTable = dt.sortAscending("number");
		for (int i = 0; i < 5; i++) {
			row = sortedTable.getRow(i);
			assertEquals(4-i, row.getValue("id"));
			assertEquals(i, row.getValue("number"));
			
		}
	}
	
	@Test
	public void sortRowsTypeException() {
		dt.addCollumn("name", DataTable.TYPE_STRING);
		
		try {
			dt.sortAscending("name");
		} catch (ClassCastException e) {
			assertEquals("Only Integer columns can be sorted.", e.getMessage());
			return;
		}
		
	}
}
