package test;

import util.Heap;
import util.HeapElement;

public class HeapTest extends HeapElement {
	private Double score;
	private int index;
	public HeapTest(double score, int index) {
		this.score = score;
		this.index = index;
	}
	@Override
	public Double getValue() {
		// TODO Auto-generated method stub
		return score;
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}

	@Override
	public int compare(HeapElement e) {
		// TODO Auto-generated method stub
		double t = getValue() - e.getValue();
		return (t == 0 ? 0 : (t > 0) ? 1 : -1);
	}

	public static void main(String[] args) {
		HeapElement[] elements = new HeapElement[5]; 
		Heap h = new Heap(elements);
		h.add(new HeapTest(1.0, 1));
		h.add(new HeapTest(1.1, 2));
		h.add(new HeapTest(0.0, 3));
		h.add(new HeapTest(2.0, 4));
		h.add(new HeapTest(0.3, 5));
		h.printHeap();
		h.clearHeap();
	}
}
