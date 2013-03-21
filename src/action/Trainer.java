package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.Heap;
import util.LossFunction;
import util.MSELossFunction;

import entity.Corpus;
import entity.Model;
import entity.Parameter;
import entity.Tree;

/**
 * 参数训练
 * @author double
 * @data 2013-03-20
 * @version 0.1
 */
public class Trainer {
	
	private static Parameter p;
	private static Corpus corpus;
	private static LossFunction lossFunction;
	
	public static Model train(String args) {
		setParameter(args);
		lossFunction = new MSELossFunction();
		if (!p.isLegal()) {
			return null;
		}
		try {
			System.err.println("Load train data...");
			corpus = CorpusReader.readCorpus(p.getTrainDataFile());
			System.err.println("Load train data OK! total " + corpus.getInstanceSize() + " instance!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Model model = new Model();
		for (int i = 0; i < p.getTreeNumber(); ++i) {
			System.err.println("Tree " + i + " training...");
			ArrayList<Tree> tree = TreeTrainer();
			System.err.println("Tree " + i + " OK!");
			System.err.println("Update train data!");
			updataTrainData(tree);
			System.err.println("Update train data done");
			model.addTree(tree);
		}
		model.saveModel(p.getSaveFile());
		return model;
	}
	
	/**
	 * 每次迭代
	 * @return	返回一棵树
	 */
	private static ArrayList<Tree> TreeTrainer() {
		List<Integer> featureSampleList = FeatureFaceory.sampleFeature(corpus.getFeatureSize(), p.getFeatureSampleRate());
		System.err.println("sampling feature done!");
		List<Integer> trainDataSampleList = InstanceFactory.sampleFeature(corpus.getInstanceSize(), p.getInstanceSampleRate());
		System.err.println("sampling instance done!");
		ArrayList<Tree> tree = new ArrayList<Tree>();
		Heap heap = new Heap(new Tree[p.getSplitNumber()]);
		Tree root = new Tree(0, 0, trainDataSampleList);
		lossFunction.setLossFunctionValue(root, corpus);
		lossFunction.minLossFunction(root, featureSampleList, corpus, p);
		tree.add(root);
		if (root.getTmpTree() == null) {
			System.out.println("no");
			return tree;
		}
		heap.add(root);
		for (int i = 0; i < p.getSplitNumber(); ++i) {
			if (heap.getElementNumber() == 0) {
				return tree;
			}
			int id = heap.pop().getIndex();
			int leftChildID = tree.size();
			if (tree.get(id).getTmpTree()[0].getSamplesSize() == 0 || tree.get(id).getTmpTree()[1].getSamplesSize() == 0) {
				continue;
			}
			tree.get(id).setLeftChildID(leftChildID);
			tree.get(id).setInit(true);
			int nextIndex = tree.size();
			tree.add(tree.get(id).getTmpTree()[0]);
			tree.add(tree.get(id).getTmpTree()[1]);
			tree.get(nextIndex).setIndex(nextIndex);
			tree.get(nextIndex+1).setIndex(nextIndex+1);
//			lossFunction.setLossFunctionValue(tree.get(leftChildID), corpus);
//			lossFunction.setLossFunctionValue(tree.get(leftChildID+1), corpus);
			if (tree.get(leftChildID).getSamplesSize() >= p.getMinNumInNode()*2) {
//				lossFunction.setLossFunctionValue(tree.get(leftChildID), corpus);
				lossFunction.minLossFunction(tree.get(leftChildID), featureSampleList, corpus, p);
				if (!(tree.get(leftChildID).getTmpTree() == null)) {
					heap.add(tree.get(leftChildID));
				}
			}
			if (tree.get(leftChildID+1).getSamplesSize() >= p.getMinNumInNode()*2) {
//				lossFunction.setLossFunctionValue(tree.get(leftChildID+1), corpus);
				lossFunction.minLossFunction(tree.get(leftChildID+1), featureSampleList, corpus, p);
				if (!(tree.get(leftChildID+1).getTmpTree() == null)) {
					heap.add(tree.get(leftChildID+1));
				}
			}
		}
		System.out.println("avg:"+tree.get(tree.size()-1).getAvgValue());
		return tree;
	}
	
	private static void updataTrainData(List<Tree> tree) {
		corpus.update(tree);
	}
	private static void setParameter(String args) {
		p = new Parameter();
		String[] array = args.split("\\s+");
		for (int i = 0; i < array.length; ++i) {
			if (array[i].startsWith("-")) {
				if (array[i].equals("-o") || array[i].equals("-m")) {	//模型输出文件
					p.setSaveFile(array[i+1]);
				}
				else if (array[i].equals("-t")) {	//树的数量
					p.setTreeNumber(Integer.parseInt(array[i+1]));
				}
				else if (array[i].equals("-n")) {	//子树包含节点数目的下限
					p.setMinNumInNode(Integer.parseInt(array[i+1]));
				}
				else if (array[i].equals("-d")) {	//每棵树最多分裂次数
					p.setSplitNumber(Integer.parseInt(array[i+1]));
				}
				else if (array[i].equals("-f")) {	//特征抽样比率
					p.setFeatureSampleRate(Double.parseDouble(array[i+1]));
				}
				else if (array[i].equals("-s")) {	//样本抽样比率
					p.setInstanceSampleRate(Double.parseDouble(array[i+1]));
				}
				else if (array[i].equals("-b")) {	//是否是bool型特征
					p.setBooleanFeature(Boolean.parseBoolean(array[i+1]));
				}
				else if (array[i].equals("-i")) {	//训练数据文件
					p.setTrainDataFile(array[i+1]);
				}
				else {
					--i;
				}
				++i;
			}
		}
	}
}
