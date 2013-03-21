package entity;

/**
 * 模型的超参数
 * @author double
 * @data 2013-03-13
 * @version 0.1
 */
public class Parameter {
	private int treeNumber = 1;				//模型中树的数目
	private int splitNumber = 1;			//每棵树的分裂次数
	private int minNumInNode = 1;			//子树中包含的节点数目下限
	private double featureSampleRate = 0.5;	//特征抽样比率
	private double instanceSampleRate = 0.5;	//样本抽样比率
	private String saveFile;				//模型保存路径
	private String  trainDataFile;			//训练数据文件名
	private boolean isBooleanFeature =  false;		//是否是二元特征
	
	public boolean isLegal() {
		if (saveFile == null) {
			System.out.println("请指定参数saveFile!");
			return false;
		}
		if (featureSampleRate > 1 || featureSampleRate < 0) {
			System.out.println("参数featureSampleRate超出范围：(0,1]");
			return false;
		}
		if (instanceSampleRate > 1 || instanceSampleRate < 0) {
			System.out.println("参数instanceSampleRate超出范围：(0,1]");
			return false;
		}
		if (trainDataFile == null) {
			System.out.println("请指定参数trainDataFile!");
			return false;
		}
		return true;
	}
	
	public int getTreeNumber() {
		return treeNumber;
	}
	public void setTreeNumber(int treeNumber) {
		this.treeNumber = treeNumber;
	}
	public int getSplitNumber() {
		return splitNumber;
	}
	public void setSplitNumber(int splitNumber) {
		this.splitNumber = splitNumber;
	}
	public int getMinNumInNode() {
		return minNumInNode;
	}
	public void setMinNumInNode(int minNumInNode) {
		this.minNumInNode = minNumInNode;
	}
	public double getFeatureSampleRate() {
		return featureSampleRate;
	}
	public void setFeatureSampleRate(double featureSampleRate) {
		this.featureSampleRate = featureSampleRate;
	}
	public double getInstanceSampleRate() {
		return instanceSampleRate;
	}
	public void setInstanceSampleRate(double instanceSampleRate) {
		this.instanceSampleRate = instanceSampleRate;
	}
	public String getSaveFile() {
		return saveFile;
	}
	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}
	public String getTrainDataFile() {
		return trainDataFile;
	}
	public void setTrainDataFile(String trainDataFile) {
		this.trainDataFile = trainDataFile;
	}

	public boolean isBooleanFeature() {
		return isBooleanFeature;
	}

	public void setBooleanFeature(boolean isBooleanFeature) {
		this.isBooleanFeature = isBooleanFeature;
	}
}
