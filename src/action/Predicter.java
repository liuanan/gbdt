package action;

import java.util.ArrayList;
import java.util.List;

import entity.Instance;
import entity.Model;
import entity.Tree;

/**
 * 预测类
 * @author double
 * @data 2013-03-20
 */
public class Predicter {
	/**
	 * 整个模型预测ins的值，结果保存在ins中
	 * @param ins	测试样本
	 * @param model	模型
	 */
	public static void predict(Instance ins, Model model) {
		for (ArrayList<Tree> tree : model.getForest()) {
			predict(ins, tree);
		}
	}
	
	/**
	 * 使用一棵树预测ins的增量，并且使用增量更新ins的预测值
	 * @param ins	样本
	 * @param tree	树
	 */
	public static void predict(Instance ins, List<Tree> tree) {
		int index = 0;
		while (tree.get(index).getLeftChildID() > 0) {
			if (ins.getFeature(tree.get(index).getFeatureID()) < tree.get(index).getFeatureValue()) {
				index = tree.get(index).getLeftChildID();
			}
			else {
				index = tree.get(index).getRightChildID();
			}
		}
		double predictValue = tree.get(index).getAvgValue();
		ins.setPredictLabel(ins.getPredictLabel()+predictValue);
	}
}
