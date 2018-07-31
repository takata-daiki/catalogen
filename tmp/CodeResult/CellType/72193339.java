package com.github.cutstock.excel.model;
public enum CellType {
		TITLE("title"), HEADER("header"), CELL("cell");
		private String type;

		CellType(String type) {
			this.type = type;
		}

		public String typeValue() {
			return type;
		}
	}