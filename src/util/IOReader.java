package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class IOReader {
	private BufferedReader reader = null;
	
	public IOReader(String file) throws UnsupportedEncodingException, FileNotFoundException {
		// TODO Auto-generated constructor stub
		init(file, "utf-8");
	}
	public IOReader(String file, String encoding) throws UnsupportedEncodingException, FileNotFoundException {
		init(file, encoding);
	}
	private void init(String file, String encoding) throws UnsupportedEncodingException, FileNotFoundException {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
	}
	
	public String readLine() throws IOException {
		return reader.readLine();
	}
}
