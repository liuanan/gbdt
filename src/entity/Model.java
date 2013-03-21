package entity;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * GBDT模型
 * @author double
 *
 */
public class Model implements Externalizable {
	private List<ArrayList<Tree>> forest;	//树
	
	public Model() {
		init();
	}
	public Model(String modelFile) {
		this.forest = loadModel(modelFile).getForest();
	}
	private void init() {
		forest = new ArrayList<ArrayList<Tree>>();
	}
	public void addTree(ArrayList<Tree> t) {
		forest.add(t);
	}
	public List<ArrayList<Tree>> getForest() {
		return this.forest;
	}
	
	/**
	 * 保存模型
	 * @param modelFile
	 */
	public void saveModel(String modelFile) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(modelFile));
			out.writeObject(this);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取模型
	 * @param modelFile
	 * @return
	 */
	public static Model loadModel(String modelFile) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(modelFile));
			return (Model) in.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		int treeNumber = in.readInt();
		init();
		for (int i = 0; i < treeNumber; ++i) {
			int nodeNumber = in.readInt();
			ArrayList<Tree> tree = new ArrayList<Tree>();
			for (int j = 0; j < nodeNumber; ++j) {
				Tree t = (Tree) in.readObject();
				tree.add(t);
			}
			this.forest.add(tree);
		}
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeInt(forest.size());
		for (ArrayList<Tree> tree : forest) {
			out.writeInt(tree.size());
			for (Tree t : tree) {
				out.writeObject(t);
			}
		}
	}
}
