package org.fh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

public class configHandler
{
	static Path configFile = Paths.get("config.xml");

	public static boolean loadConfig()
	{
		XMLConfiguration config;
		try
		{
			if (Files.notExists(configFile))
			{
				Files.createFile(configFile);
			}
			config = new XMLConfiguration(configFile.toString());
			config.setExpressionEngine(new XPathExpressionEngine());
			return true;
		} catch (ConfigurationException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
