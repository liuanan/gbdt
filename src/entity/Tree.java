package entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

import util.HeapElement;

/**
 * 包含树（子树）的信息
 * @author double
 * @data 2013-03-13
 * @version 0.1
 */
public class Tree extends HeapElement implements Externalizable{
	private Integer featureID = -1;			//分裂的特征id
	private Double featureValue = -1.0;	//分裂的特征值
	private Integer	leftChildID = -2;		//左子树的id，右子树的id是左子树id+1。如果本节点没有分裂，那么取左子树id的相反数
	private List<Integer> samplesIDs;	//树中的元素的id
	private Double lossFunctionValue;	//损失函数值
	private Double avgValue = Double.NaN;			//均值
	private Integer deep;
	private Tree[] tmpTree;
	private boolean isInit = false;
	private Integer id;					//在数组中的位置
	
	public Tree() {};
	public Tree(int index, int deep, List<Integer> samplesIDs) {
		setIndex(index);
		setDeep(deep);
		setSamplesIDs(samplesIDs);
	}
	public int getFeatureID() {
		return featureID;
	}
	public void setFeatureID(int featureID) {
		this.featureID = featureID;
	}
	public double getFeatureValue() {
		return featureValue;
	}
	public void setFeatureValue(double featureValue) {
		this.featureValue = featureValue;
	}

	public List<Integer> getSamplesIDs() {
		return samplesIDs;
	}
	public void setSamplesIDs(List<Integer> samplesIDs) {
		this.samplesIDs = samplesIDs;
	}
	public int getSamplesSize() {
		return samplesIDs.size();
	}
	public Double getLossFunctionValue() {
		return lossFunctionValue;
	}
	public void setLossFunctionValue(Double lossFunctionValue) {
		this.lossFunctionValue = lossFunctionValue;
	}
	public Double getAvgValue() {
		return avgValue;
	}
	public void setAvgValue(Double avgValue) {
		this.avgValue = avgValue;
	}
	public int getDeep() {
		return deep;
	}
	public void setDeep(int deep) {
		this.deep = deep;
	}
	public int getLeftChildID() {
		return leftChildID;
	}
	public void setLeftChildID(int leftChildID) {
		this.leftChildID = leftChildID;
	}
	public int getRightChildID() {
		return leftChildID+1;
	}
	public Tree[] getTmpTree() {
		return tmpTree;
	}
	public void setTmpTree(Tree[] tmpTree) {
		this.tmpTree = tmpTree;
	}
	public boolean isInit() {
		return isInit;
	}
	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}
	public void setIndex(int index) {
		this.id = index;
	}
	@Override
	public Double getValue() {
		// TODO Auto-generated method stub
		return getLossFunctionValue()-(tmpTree[0].getLossFunctionValue()+tmpTree[1].getLossFunctionValue());
	}
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return id;
	}
	@Override
	public int compare(HeapElement e) {
		// TODO Auto-generated method stub
		double t = getValue() - e.getValue();
		return (t == 0 ? 0 : (t > 0) ? -1 : 1);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		this.featureID = (Integer) in.readObject();
		this.leftChildID = (Integer) in.readObject();
		this.featureValue = (Double) in.readObject();
		this.avgValue = (Double) in.readObject();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeObject(featureID);
		out.writeObject(leftChildID);
		out.writeObject(featureValue);
		out.writeObject(avgValue);
	}
}
