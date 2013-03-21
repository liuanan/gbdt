package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import util.IOReader;
import action.CorpusReader;
import action.Predicter;
import action.Trainer;
import entity.Corpus;
import entity.Instance;
import entity.Model;

public class GBDTTest {

	public static void train(String args) {
		Trainer.train(args);
	}
	public static void test(String args) {
		String[] c = args.split("\\s+");
		String modelFile = null;
		String testFile = null;
		for (int i = 0; i < c.length; ++i) {
			if (c[i].equals("-m")) {
				modelFile = c[i+1];
			}
			else if (c[i].equals("-t")) {
				testFile = c[i+1];
			}
			else {
				--i;
			}
			++i;
		}
		Corpus corpus = null;
		Model model = Model.loadModel(modelFile);
		try {
			corpus = CorpusReader.readCorpus(testFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Instance> testInstance = corpus.getInstanceList();
		System.out.println("gold\tpredict");
		int all = 0;
		int right = 0;
		for (Instance ins : testInstance) {
			Predicter.predict(ins, model);
			System.out.println(ins.toString());
			if (ins.getLabel()-ins.getPredictLabel() < 0.5) {
				++right;
			}
			++all;
		}
		System.out.println(right+"/"+all);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			IOReader reader = new IOReader("./util.txt");
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("train")) {
					GBDTTest.train(line);
				}
				else if (line.startsWith("test")) {
					GBDTTest.test(line);
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
