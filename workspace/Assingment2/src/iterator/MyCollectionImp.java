package iterator;

import java.util.Iterator;

public class MyCollectionImp implements MyCollection, Iterable {

	private Object[] list;
	private int currentPos = 0;
	
	public MyCollectionImp() 
	{
		this.list = new Object [10];
	}
	
	@Override
	public void add(Object o) {
		// TODO Auto-generated method stub
		if (this.currentPos == this.list.length)
		{
			Object[] newList = new Object[this.list.length + 10];
			for(int i = 0; i < this.list.length; i++)
			{
				newList[i] = this.list[i];
			}
			this.list = newList;
		}
		this.list[currentPos++] = o;

	}

	@Override
	public Object get(int i) {
		// TODO Auto-generated method stub
		if (i > 0 && i < this.list.length) {
			return this.list[i];
		}
		throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public void remove(Object o) {
		// TODO Auto-generated method stub
		int i;
		for(i = 0; i < this.list.length && this.list[i] != o; i++);
		for(int j = i; j < this.list.length; j++)
		{
			this.list[j] = this.list[j + 1];
		}
		this.currentPos--;
	}

	@Override
	public void remove(int i) {
		// TODO Auto-generated method stub
		if (i >= 0 && i <= this.currentPos)
		{
			for(int j = i; j < this.list.length - 1; j++)
			{
				this.list[j] = this.list[j + 1];
			}
			this.currentPos--;
		}
		else
			throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.currentPos;
	}
	
	public Iterator iterator()
	{
		return new Iterator() {
			private int ind = 0;
			
			public boolean hasNext() {
				return ind < size();
			}
			
			public Object next() {
				return list[ind++];
			}
			
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	

}
