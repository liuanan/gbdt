package util;

/**
 * 定义了一个堆元素的行为
 * @author double
 * @date 2013-03-20
 */
public abstract class HeapElement {
	abstract public Double getValue();
	abstract public int getIndex();
	abstract public int compare(HeapElement e);
	public String toString() {
		return getValue().toString()+"/"+getIndex();
	}
}
