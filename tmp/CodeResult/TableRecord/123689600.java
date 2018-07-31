package com.bhpb.xmleditor.swing.model;

public class TableRecord {
	String plugin;
	String dispName;
	String type;
	String schema;
	String config;
	String xml;
	String help;
	int    pluginIndex;
	int    configIndex;
	boolean editable;
	
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public String getPlugin() {
		return plugin;
	}
	public void setPlugin(String plugin1) {
		this.plugin = plugin1;
		pluginIndex = 0;
		if ("Delivery".endsWith(plugin)) {
			pluginIndex = 1;
		} else if ("Script".endsWith(plugin)) {
			pluginIndex = 2;
		}
		
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	public int getPluginIndex() {
		return pluginIndex;
	}
	public void setPluginIndex(int pluginIndex) {
		this.pluginIndex = pluginIndex;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
		configIndex = 0;
		if (config.indexOf("delivery") > 0) {
			configIndex = 0;
		} else if (config.indexOf("script") > 0) {
			configIndex = 1;
		}
	}
	public int getConfigIndex() {
		return configIndex;
	}
	public void setConfigIndex(int configIndex) {
		this.configIndex = configIndex;
	}
}
