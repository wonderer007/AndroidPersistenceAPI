package com.android.persistence.api;

import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApaConfiguration {

	private Properties properties = null;
	private static ApaConfiguration instance;

	private String databaseName;
	private int databaseVersion;

	private ApaConfiguration() {
		readConfigFileAssets();
		initBuildConfigurations();
	}

	public static ApaConfiguration getInstance() {
		if (instance == null) {
			instance = new ApaConfiguration();
		}
		return instance;
	}

	private void readConfigFileAssets() {
		Resources resources = Apa.getAppContext().getResources();
		AssetManager assetManager = resources.getAssets();
		try {
			InputStream inputStream = assetManager.open("apa.properties");
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public void initBuildConfigurations() {
		if (properties != null) {
			databaseName = getStringProperty("databaseName");
			databaseVersion = Integer.parseInt(getStringProperty("databaseVersion"));
		}
	}

	private String getStringProperty(String propertyName) {
		if (properties != null && properties.containsKey(propertyName)) {
			return (String) properties.get(propertyName);
		}
		return null;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public int getDatabaseVersion() {
		return databaseVersion;
	}
}
