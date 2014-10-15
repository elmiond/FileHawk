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
				ArrayList<ruleSet> rules = new ArrayList<ruleSet>();
				String[] mat = config.getStringArray("folders/folder[path = '" + s
						+ "']/rulesets/ruleset/matchsetname");
				String[] act = config.getStringArray("folders/folder[path = '" + s
						+ "']/rulesets/ruleset/actionsetname");
				int i = 0;
				boolean run = true;
				while (run)
				{
					
					System.out.println(mat[i]);
					System.out.println(act[i]);
					rules.add(new ruleSet(getMatchRule(mat[i]), getActions(act[i])));
					i++;
					if (i >= mat.length || i >= act.length)
					{
						run = false;
					}
				}
				
				// getMatchRule(mat), getActions(act)
				folders.add(new folder(Paths.get(s), rules));
			} else
			{
				System.out.format("Could not find folder: %s\n", s);
			}
		}
		return folders;
	}

	private matchRule getMatchRule(String name)
	{
		String kind = config.getString("matches/match[name = '" + name + "']/kind");
		System.out.println(kind);
		switch (kind)
		{
		case "regex":
			String pattern = config.getString("matches/match[name = '" + name
					+ "']/pattern");
			return new regexRule(pattern);

		default:
			return new regexRule("");
		}
	}

	private ArrayList<action> getActions(String name)
	{
		ArrayList<action> al = new ArrayList<action>();

		String[] kinds = config.getStringArray("actionsets/actionset[name = '"
				+ name + "']/actions/action/kind");

		int i = 1;
		for (String kind : kinds)
		{
			System.out.println(kind);
			al.add(getAction(name, i, kind));
			i++;
		}
		return al;
	}

	private action getAction(String name, int index, String kind)
	{
		switch (kind)
		{
		case "move":

			return new moveAction(Paths.get(config
					.getString("actionsets/actionset[name = '" + name
							+ "']/actions/action[" + index + "]/destination")));
		default:
			return new moveAction(Paths.get(""));
		}
	}
}
