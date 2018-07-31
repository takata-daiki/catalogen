package org.reichel.ambiente.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import org.reichel.ambiente.gui.panel.manager.ContextDeployProjectsManagerPanel;
import org.reichel.config.Config;
import org.reichel.config.gui.ConfigManager;

public class ButtonSaveConfigActionListener implements ActionListener {

	private JTable tableProperties;
	private Config config;
	private JButton buttonSave;
	private Map<String, JCheckBox> comboBoxProjetos;
	private ConfigManager configManager;
	private ContextDeployProjectsManagerPanel contextDeployProjetosPanel;
	
	public ButtonSaveConfigActionListener(ConfigManager configManager, Config config, Map<String, JCheckBox> cbProjetos,  ContextDeployProjectsManagerPanel contextDeployProjetosPanel){
		this.tableProperties = configManager.getTablePanel().getTableProperties();
		this.config = config;
		this.buttonSave = configManager.getButtonsPanel().getButtonSave();
		this.comboBoxProjetos = cbProjetos;
		this.configManager = configManager;
		this.contextDeployProjetosPanel = contextDeployProjetosPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < tableProperties.getRowCount(); i++){
			String key = (String) tableProperties.getValueAt(i, 0);
			String value = (String) tableProperties.getValueAt(i, 1);
			config.put(key, value);
		}
		
		String value = "";
		for(Entry<String,JCheckBox> entry : this.comboBoxProjetos.entrySet()){
			if(entry.getValue().isSelected()){
				value += entry.getKey() + ";";
			}
		}
		config.put("projetos", value);
		config.saveAndReload();
		configManager.getTablePanel().getTableHelper().fillTable(config.getKeys());
		buttonSave.setEnabled(false);
		contextDeployProjetosPanel.repaint();
	}
}
