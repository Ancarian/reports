package org.openpreservation.job.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class PropertiesReader {
	private final Configuration configuration;

	public PropertiesReader() {
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
				new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
						.configure(params.properties()
								.setFileName("application.properties"));
		try {
			this.configuration = builder.getConfiguration();
		} catch (ConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}

	public String readProperty(String propertyName) {
		try {
			return configuration.getProperty(propertyName).toString();
		}catch (NullPointerException e){
			throw new IllegalArgumentException(String.format("property %s is null", propertyName));
		}
	}
}
