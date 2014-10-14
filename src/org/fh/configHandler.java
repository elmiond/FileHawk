package org.fh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

public class configHandler
{
	String configFile = "config.xml";
	XMLConfiguration config;

	public configHandler()
	{
		try
		{
			if (Files.notExists(Paths.get(configFile)))
			{
				Files.createFile(Paths.get(configFile));

				// TODO tilføj skrivning af en tom basis struktur når når vi ved hvordan
				// den
				// skal være
			}
			config = new XMLConfiguration(configFile);
			config.setExpressionEngine(new XPathExpressionEngine());

		} catch (IOException | ConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<folder> getFolders() throws ConfigurationException
	{
		ArrayList<folder> folders = new ArrayList<folder>();

		String[] ss = config.getStringArray("folders/folder/path");
		for (String s : ss)
		{
			if (Files.exists(Paths.get(s)))
			{
				folders.add(new folder(Paths.get(s)));
			}
			else
			{
				System.out.format("Could not find folder: %s\n", s);
			}
		}
		return folders;
	}
}
