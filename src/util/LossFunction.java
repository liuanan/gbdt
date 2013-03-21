package util;

import java.util.List;

import entity.Corpus;
import entity.Parameter;
import entity.Tree;

/**
 * 损失函数的接口
 * @author double
 *
 */
public abstract class LossFunction {
	/**
	 * 计算损失函数值
	 * @param t 节点（树）
	 * @param corpus
	 * @return 损失函数值
	 */
	abstract public double setLossFunctionValue(Tree t, Corpus corpus);
	
	/**
	 * 最小化子树的损失函数和
	 * @param t 节点（树）
	 * @param corpus
	 */
	abstract public void minLossFunction(Tree t, List<Integer> featureSampleList,
			Corpus corpus, Parameter p);
	
}
