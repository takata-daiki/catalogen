package collections;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class Vector implements Collection, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6653184623840399302L;
	private static final int BASE_SIZE = 20;
	private Object[] internalArray;
	private Handler handler = new Handler();
	private GetHandler getHandler = new GetHandler();
	private PutHandler putHandler = new PutHandler();
	private RemoveHandler removeHandler = new RemoveHandler();
	private ResizeHandler resizeHandler = new ResizeHandler();
	
	public Vector(){
		this(Vector.BASE_SIZE);
	}
	
	public Vector(final int declared_size){
		internalArray = new Object[declared_size];
	}
	
	@Override
	public Object get() {
		return this.getHandler.handleGet();
	}
	
	@Override
	public Object remove(Object object){
		return this.removeHandler.handleRemove(object);
	}

	@Override
	public void put(Object object) {
		this.putHandler.handlePut(object);
	}

	@Override
	public Object[] toArray() {
		return this.internalArray.clone();
	}
	
	/*
	 * (non-Javadoc)
	 * @see hw4hw5.Collection#toTraverser()
	 * it blows my mind that the following method works. It's an anonymous class whose output
	 * is an interface. As I always understood things you couldn't instantiate interfaces,
	 * but this is a sort of work around to that that I absolutely adore and is everything I originally wanted from
	 * the interface: a clean and clear way to separate implementation from usage. 
	 * 
	 * As I start to get used to this way of doing things I'll probably make better use of it */
	@Override
	public Traverser toTraverser(){
		
		Traverser traverser = new Traverser(){
			Vector vector = Vector.this;
			
			public boolean hasNext(){
				return this.vector.handler.incrementTil(true);
			}
			
			public Object next(){
				return this.vector.get();
			}
		};
		
		return traverser;
	}
	private void writeObject(ObjectOutputStream aOutputStream) throws IOException{
		aOutputStream.defaultWriteObject();
	}
	class Handler implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 7035940938099018175L;
		int getLoc =0;
		int putLoc =0;
		int remLoc =0;
		int objectsPut =0;
		
		Handler(){
			
		}
		
		boolean incrementTil(boolean forGet){
			if (objectsPut >= Vector.this.internalArray.length)
				Vector.this.resizeHandler.resizeArray();
			
			for (int i=0; i<Vector.this.internalArray.length; i++){
				if(forGet){
					if(Vector.this.internalArray[i] != null) {
						this.getLoc =i;
						return true;
					}
				}
				else {
					if(Vector.this.internalArray[i] == null) {
						this.putLoc =i;
						return true;
					}
				}
			}
			return false;
		}
		
		boolean findObject(Object object){
			for(int i=0; i<Vector.this.internalArray.length; i++)
				if(Vector.this.internalArray[i] == object){
					this.remLoc =i;
					return true;
				}
			
			return false;
		}
		
		Object handleOutput(){
			Object object =Vector.this.internalArray[this.getLoc];
			Vector.this.internalArray[this.getLoc] =null;
			this.getLoc =0;
			this.objectsPut -= 1;
			return object;	
		}
		
		boolean handleInput(Object object){
			Vector.this.internalArray[this.putLoc] = object;
			this.putLoc =0;
			this.objectsPut += 1;
			return true;
		}
		
		boolean handleRemove(){
			Vector.this.internalArray[this.remLoc] = null;
			this.remLoc =0;
			this.objectsPut -= 1;
			return true;
		}
	}
	class GetHandler implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 7125985559879865534L;

		Object handleGet(){
			if(!Vector.this.handler.incrementTil(true))
				return null;
			
			return Vector.this.handler.handleOutput();
		}
	}
	
	class PutHandler implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9064080083397660478L;

		boolean handlePut(Object object){
			if(!Vector.this.handler.incrementTil(false))
				return false;
			
			return Vector.this.handler.handleInput(object);
		}
	}
	
	class RemoveHandler implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4915402449629549751L;

		boolean handleRemove(Object object){
			if(!Vector.this.handler.findObject(object))
				return false;
			
			return Vector.this.handler.handleRemove();
		}
	}
	
	class ResizeHandler implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4359568487289977967L;
		static final int RESIZE_MULTIPLIER = 2;
		
		void resizeArray(){
			Object[] tempObject = new Object[Vector.this.internalArray.length * ResizeHandler.RESIZE_MULTIPLIER];
			tempObject = Arrays.copyOf(Vector.this.internalArray, tempObject.length);
			
			Vector.this.internalArray = tempObject;
		}
	}
}
