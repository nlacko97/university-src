package string;

import java.util.Iterator;

public class MyString implements Iterable<Character> {
	
	private String s;
	
	public MyString() {
		s = "";
	}
	
	public MyString(String s) {
		this.s = s;
	}
	
	public void append(String str) {
		this.s = this.s.concat(str);
	}
	
	public void append(char ch) {
		this.s += ch;
	}
	
	public void insert(int pos, String str) {
		if (pos < 0 || pos >= this.s.length()) {
			throw new IndexOutOfBoundsException();
		}
		String newS = new String();
		newS = this.s.substring(0, pos + 1);
		newS += str;
		newS += this.s.substring(pos);
		this.s = newS;
	}
	
	public void insert(int pos, char ch) {
		if (pos < 0 || pos >= this.s.length()) {
			throw new IndexOutOfBoundsException();
		}
		String newS = new String();
		newS = this.s.substring(0, pos + 1);
		newS += ch;
		newS += this.s.substring(pos);
		this.s = newS;
	}
	
	public void delete (int pos, int length) {
		if (pos < 0 || pos >= this.s.length()) {
			throw new IndexOutOfBoundsException();
		}
		String newS = new String();
		newS = this.s.substring(0, pos);
		newS += this.s.substring(pos + length, this.s.length());
		this.s = newS;
	}
	
	public String toString() {
		return this.s;
	}

	public Iterator<Character> iterator() {
		return new Iterator<Character>() {
			
			private int ind = 0;

			public boolean hasNext() {
				return ind < s.length();
			}

			public Character next() {
				return s.charAt(ind++);
			}
			
		};
	}


}
