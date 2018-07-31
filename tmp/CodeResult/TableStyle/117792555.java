package gruppe3.tuwien.sepm.ui.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTable;

public class TableStyle {
	public static void format(JTable table)
	{
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		table.setRowHeight(22);
		
		table.setFillsViewportHeight(true);
		
		table.getTableHeader().setBackground(new Color(97, 129, 182));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		table.getTableHeader().setForeground(new Color(255, 255, 255));
		table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 20));
		
		for(int i=0; i<table.getColumnCount(); i++)
		{
			table.getColumnModel().getColumn(i).setCellRenderer(new TableRenderer());
		}
	}
}
