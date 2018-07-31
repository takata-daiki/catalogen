package com.java.basics;

public class Child extends Parent{
	
	
	public Child(){
		System.out.println("child no arg const");
		//super.getName(); //parent methods can be accessed using super keyword.
	}
	
	
	public void play(){
		System.out.println("child is playing");
	}

	
	public static void main(String[] arg){
		
		
		Child c = new Child();
		Parent p = new Child();
		
		Child c2 = (Child)p; //casting parent obj to child
		
		
		
		
	}
	
	

}
