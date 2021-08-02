package memento;

import java.util.Stack;

public class CareTaker {
	private Stack<Memento> stack;

	public CareTaker() {
		stack = new Stack<Memento>();
	}
	
	public void save(Memento memento) {
		stack.push(memento);
	}
	
	public Memento restore() {
		return stack.pop();
	}

	public int size() {
		return stack.size();
	}
}
