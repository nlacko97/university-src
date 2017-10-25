package iterator;

public interface MyCollection {
	void add(Object o);
	Object get(int i);
	void remove(Object o);
	void remove(int i);
	int size();
}
