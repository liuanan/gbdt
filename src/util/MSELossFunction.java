package util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.Corpus;
import entity.Parameter;
import entity.Tree;

/**
 * 均方损失函数
 * @author double
 * @date 2013-03-20
 */
public class MSELossFunction extends LossFunction{

	public MSELossFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double setLossFunctionValue(Tree t, Corpus corpus) {
		// TODO Auto-generated method stub
		double sum = 0;
		double tmp = 0.0;
		if (t.getAvgValue().isNaN()) {
			for (int index : t.getSamplesIDs()) {
				tmp += corpus.getLabel(index);
			}
		}
		t.setAvgValue(tmp/t.getSamplesSize());
		double avg = t.getAvgValue();
		for (int index : t.getSamplesIDs()) {
			double dis = corpus.getLabel(index)-avg;
			sum += dis*dis;
		}
		t.setLossFunctionValue(sum/t.getSamplesSize());
		return t.getLossFunctionValue();
	}

	@Override
	public void minLossFunction(Tree t, List<Integer> featureSampleList, Corpus corpus, Parameter p) {
		// TODO Auto-generated method stub
		if (p.isBooleanFeature()) {
			boolFeature(t, featureSampleList, corpus, p);
		}
		else {
			numericalFeature(t, featureSampleList, corpus, p);
		}
	}
	
	/**
	 * 数值型特征
	 * @param t	树
	 * @param featureSampleList 特征集合
	 * @param corpus 样本空间
	 * @param p	超参数
	 */
	private void numericalFeature(Tree t, List<Integer> featureSampleList, Corpus corpus, Parameter p) {
		if (t.getTmpTree() != null) {
			return;
		}
		Tree[] tmpTree = null;
		tmpTree = new Tree[2];
		tmpTree[0] = new Tree();
		tmpTree[1] = new Tree();
		boolean isFirst = true;
		List<Integer> samplesIDs =  t.getSamplesIDs();
		List<Integer> leftSampleIDList = new ArrayList<Integer>();
		List<Integer> rightSampleIDList = new ArrayList<Integer>();
		for (int featureIndex : featureSampleList) {
//			Collections.sort(samplesIDs, new IntegerComparator(corpus, featureIndex));
			for (int ins : samplesIDs) {
				double splitValue = corpus.getFeature(ins, featureIndex);
				double bigThanSplitValue = splitValue;
				double leftAvg = 0.0;
				double rightAvg = 0.0;
//				List<Integer> leftSampleIDList = new ArrayList<Integer>();
//				List<Integer> rightSampleIDList = new ArrayList<Integer>();
				leftSampleIDList.clear();
				rightSampleIDList.clear();
				for (int id : t.getSamplesIDs()) {
					if (corpus.getFeature(id, featureIndex) <= splitValue) {
						leftSampleIDList.add(id);
						leftAvg += corpus.getLabel(id);
					}
					else {
						rightSampleIDList.add(id);
						rightAvg += corpus.getLabel(id);
						if (splitValue == bigThanSplitValue || corpus.getFeature(id, featureIndex) < bigThanSplitValue) {
							bigThanSplitValue = corpus.getFeature(id, featureIndex);
						}
					}
				}
				if (leftSampleIDList.size() < p.getSplitNumber() || rightSampleIDList.size() < p.getSplitNumber()) {
					continue;
				}
				leftAvg /= leftSampleIDList.size();
				rightAvg /= rightSampleIDList.size();
				double leftChildLossFunctionValue = 0.0;
				double rightChildLossFunctionValue = 0.0;
				for (int id : leftSampleIDList) {
					double dis = corpus.getLabel(id) - leftAvg;
					leftChildLossFunctionValue += dis*dis;
				}
				for (int id : rightSampleIDList) {
					double dis = corpus.getLabel(id) - rightAvg;
					rightChildLossFunctionValue += dis*dis;
				}
				leftChildLossFunctionValue /= leftSampleIDList.size();
				rightChildLossFunctionValue /= rightSampleIDList.size();
				if (isFirst || ((leftChildLossFunctionValue+rightChildLossFunctionValue) < (tmpTree[0].getLossFunctionValue()+tmpTree[1].getLossFunctionValue()))) {
					if(!isFirst && (leftChildLossFunctionValue+rightChildLossFunctionValue) > t.getLossFunctionValue()) {
//						System.out.println("no");
						continue;
					}
					tmpTree[0].setAvgValue(leftAvg);
					tmpTree[1].setAvgValue(rightAvg);
					tmpTree[0].setLossFunctionValue(leftChildLossFunctionValue);
					tmpTree[1].setLossFunctionValue(rightChildLossFunctionValue);
					tmpTree[0].setDeep(t.getDeep()+1);
					tmpTree[1].setDeep(t.getDeep()+1);
					tmpTree[0].setSamplesIDs(leftSampleIDList);
					tmpTree[1].setSamplesIDs(rightSampleIDList);
					t.setFeatureID(featureIndex);
					t.setFeatureValue((bigThanSplitValue+splitValue)/2);
					t.setTmpTree(tmpTree);
					isFirst = false;
				}
			}
		}
	}

	/**
	 * bool型特征
	 * @param t	树
	 * @param featureSampleList 特征集合
	 * @param corpus 样本空间
	 * @param p	超参数
	 */
	private void boolFeature(Tree t, List<Integer> featureSampleList, Corpus corpus, Parameter p) {
		if (t.getTmpTree() != null) {
			return;
		}
		Tree[] tmpTree = null;
		tmpTree = new Tree[2];
		tmpTree[0] = new Tree();
		tmpTree[1] = new Tree();
		boolean isFirst = true;
		for (int featureIndex : featureSampleList) {
			double leftAvg = 0.0;
			double rightAvg = 0.0;
			List<Integer> leftSampleIDList = new ArrayList<Integer>();
			List<Integer> rightSampleIDList = new ArrayList<Integer>();
			for (int id : t.getSamplesIDs()) {
				if (corpus.getFeature(id, featureIndex) < 0.5) {
					leftSampleIDList.add(id);
					leftAvg += corpus.getLabel(id);
				}
				else {
					rightSampleIDList.add(id);
					rightAvg += corpus.getLabel(id);
				}
			}
			if (leftSampleIDList.size() < p.getSplitNumber() || rightSampleIDList.size() < p.getSplitNumber()) {
				continue;
			}
			leftAvg /= leftSampleIDList.size();
			rightAvg /= rightSampleIDList.size();
			double leftChildLossFunctionValue = 0.0;
			double rightChildLossFunctionValue = 0.0;
			for (int id : leftSampleIDList) {
				double dis = corpus.getLabel(id) - leftAvg;
				leftChildLossFunctionValue += dis*dis;
			}
			for (int id : rightSampleIDList) {
				double dis = corpus.getLabel(id) - rightAvg;
				rightChildLossFunctionValue += dis*dis;
			}
			leftChildLossFunctionValue /= leftSampleIDList.size();
			rightChildLossFunctionValue /= rightSampleIDList.size();
			if (isFirst || ((leftChildLossFunctionValue+rightChildLossFunctionValue) < (tmpTree[0].getLossFunctionValue()+tmpTree[1].getLossFunctionValue()))) {
				tmpTree[0].setAvgValue(leftAvg);
				tmpTree[1].setAvgValue(rightAvg);
				tmpTree[0].setLossFunctionValue(leftChildLossFunctionValue);
				tmpTree[1].setLossFunctionValue(rightChildLossFunctionValue);
				tmpTree[0].setDeep(t.getDeep()+1);
				tmpTree[1].setDeep(t.getDeep()+1);
				tmpTree[0].setSamplesIDs(leftSampleIDList);
				tmpTree[1].setSamplesIDs(rightSampleIDList);
				t.setFeatureID(featureIndex);
				t.setFeatureValue(0.5);
				t.setTmpTree(tmpTree);
				isFirst = false;
				if (leftAvg < 0 || rightAvg < 0) {
					System.out.println("Impossible");
				}
			}
		}
	}
	/**
	 * 对样本的标号按照特征值排序 <unuse>
	 * @author double
	 *
	 */
	public class IntegerComparator implements Comparator<Integer> {
		private Corpus corpus = null;
		private int featureIndex;
		
		public IntegerComparator(Corpus corpus, int featureIndex) {
			this.corpus = corpus;
			this.featureIndex = featureIndex;
		}
		public int compare(Integer arg0, Integer arg1) {
			// TODO Auto-generated method stub
			return corpus.getFeature(arg0, featureIndex).compareTo(corpus.getFeature(arg1, featureIndex));
		}
	}
}
