package org.fh;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class folder
{
	Path folderPath;
	ArrayList<ruleSet> rules;

	public folder(Path path, ArrayList<ruleSet> rulesets)
	{
		folderPath = path;
		rules = rulesets;
	}
	
	public void doWork(Path child)
	{
		for (ruleSet rule : rules)
		{
			rule.doWork(child);
		}
	}
}