package action;

import java.util.List;

import util.Sample;

/**
 * 特征工厂
 * @author double
 * @data 2013-03-15
 */
public class FeatureFaceory {
	/**
	 * 随机选取特征集合
	 * @param featureNumber	原始特征数目
	 * @param rate	随机选取的比例
	 * @return	随机选取的特征集合
	 */
	public static List<Integer> sampleFeature(int featureNumber, double rate) {
		return Sample.sampling(featureNumber, rate);
	}
}
