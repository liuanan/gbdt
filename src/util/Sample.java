package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 采样，
 * @author double
 *
 */
public class Sample {
	
	/**
	 * 在0~num之间（不包括num）按照rate的比率采样
	 * @param num 采样整数的上界
	 * @param rate	比率
	 * @return	采样结果
	 */
	public static List<Integer> sampling(int num, double rate) {
		List<Integer> result = new ArrayList<Integer>();
		if (num <= 0 || rate <= 0) {
			return result;
		}
		int sampleNumber = (int) (num * rate);
		if (sampleNumber > num) {
			sampleNumber = num;
		}
		for (int i = 0; i < sampleNumber; ++i) {
			result.add(i);
		}
		Random r = new Random(); 
		for (int i = sampleNumber; i < num; ++i) {
			int randomNumber = r.nextInt(i);
			if (randomNumber < sampleNumber) {
				result.set(randomNumber, i);
			}
		}
		return result;
	}
}
