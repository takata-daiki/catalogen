package com.java.basics;

public class Parent {
	
	
	public Parent(){
		
		System.out.println("parent no arg const");
		
	}
	
	public void setName(String name){
		
	}

	
	public String getName(){
		System.out.println("inside parent getname");
		return "";
	}
	
	
	public void setChildrenCount(int count){
		
	}
	
	
	public int getChildrenCount(){
		return 2;
	}
	
	
	
	public static void main(String[] arg){
		
		Parent p = new Parent();
		System.out.println("inside parent main");
		
	}
	
	
	
}
