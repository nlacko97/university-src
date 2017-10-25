package printer;

public class GenericCollection implements MyCollection {

	private Object[] objects;
	
	public GenericCollection() {
		System.out.println("Constructin .. GenericCollection");
		this.objects = new Object [10];
	}
	
	public void add(Object o) {
		Object[] newList = new Object[objects.length];
		for(int i = 0; i < objects.length; i++) {
			newList[i] = objects[i];
		}
		newList[newList.length - 1] = o;
		objects = newList;
	}

	public Object get(int i) {
		if (i > 0 && i < this.objects.length)
			return this.objects[i];
		return null;
	}

	@Override
	public void remove(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(int i) {
		// TODO Auto-generated method stub

	}

}
