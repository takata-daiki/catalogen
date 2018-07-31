package com.mobgen.test;

public class ListEntry {

	public int id;
	public String name;
	public String faction;
	public int role;

	public ListEntry() {
		id = 0;
		name = "";
		faction = "";
		role = 0;
	}

	public ListEntry(int ID, String NAME, String FACTION, int ROLE) {
		id = ID;
		name = NAME;
		faction = FACTION;
		role = ROLE;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}