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
	
}
