package util;

/**
 * 实现一个堆
 * @author double
 * @data 2013-03-20
 */
public class Heap {
	private HeapElement[] elements;
	private int elementNumber;

	public Heap(HeapElement[] elements) {
		this.elements = elements;
		elementNumber = 0;
	}
	public void add(HeapElement e) {
		elements[elementNumber] = e;
		++elementNumber;
		up(elementNumber-1);
	}
	public HeapElement pop() {
		HeapElement tmp = elements[0];
		--elementNumber;
		if (elementNumber > 0) {
			elements[0] = elements[elementNumber];
			down(0);
		}
		return tmp;
	}
	private void up(int index) {
		if (index == 0) {
			return;
		}
		if (elements[index].compare(elements[(index-1)/2]) < 0) {
			exchange(index, (index-1)/2);
			up((index-1)/2);
		}
	}
	private void down(int index) {
		if (2*index+2 < elementNumber) {
			int indexTmp = 2*index+1;
			if (elements[indexTmp].compare(elements[indexTmp+1]) > 0){
				++indexTmp;
			}
			if (elements[indexTmp].compare(elements[index]) < 0) {
				exchange(index, indexTmp);
				down(indexTmp);
			}
		}
		else if (2*index+1 < elementNumber) {
			if (elements[2*index+1].compare(elements[index]) < 0) {
				exchange(index, 2*index+1);
				down(2*index+1);
			}
		}
	}
	private void exchange(int index1, int index2) {
		HeapElement tmp = elements[index1];
		elements[index1] = elements[index2];
		elements[index2] = tmp;
	}
	
	public int getElementNumber() {
		return elementNumber;
	}
	public void printHeap() {
		for (int i = 0; i < elementNumber; ++i) {
			System.out.print(elements[i].toString());
			System.out.print(" ");
		}
		System.out.println();
	}
	public void clearHeap() {
		while (elementNumber > 0) {
			System.out.println(pop().toString());
		}
	}
}
