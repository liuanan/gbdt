package entity;

import java.util.List;

/**
 * 样本中包含的信息
 * @author double
 * @data 1013-03-13
 * @version 0.1
 */
public class Instance {
	private List<Double> features;
	private Double label;
	private Double predictLabel = 0.0;
	
	public Instance(List<Double> features, Double label) {
		this.setFeatures(features);
		this.setLabel(label);
	}
	public Double getLabel() {
		return label;
	}
	public void setLabel(Double label) {
		this.label = label;
	}
	public List<Double> getFeatures() {
		return features;
	}
	public void setFeatures(List<Double> features) {
		this.features = features;
	}
	public Double getFeature(int index) {
		return features.get(index);
	}
	public int getFeatureSize() {
		return features.size();
	}
	public void update(Double detLabel) {
		label -= detLabel;
	}
	public Double getPredictLabel() {
		return predictLabel;
	}
	public void setPredictLabel(Double predictLabel) {
		this.predictLabel = predictLabel;
	}
	
	public String toString() {
		return label+"\t"+predictLabel;
	}
}
