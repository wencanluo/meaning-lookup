package org.ml.util;

import java.io.InputStream;
import java.util.Properties;

public class FileHelper {
	
	public Properties getProperties(String filename) {
		InputStream is = getClass().getResourceAsStream(filename);
		Properties properties = new Properties();
		try {
			properties.load(is);
			return properties;
		}
		catch (Exception e) {
			return null;
		}
	}
	
}
