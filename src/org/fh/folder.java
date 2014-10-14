package org.fh;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class folder
{
	Path folderPath;
	matchRule MatchRule;
	ArrayList<action> Actions = new ArrayList<action>();

	public folder(Path path, matchRule rule, ArrayList<action> actions)
	{
		folderPath = path;
		MatchRule = rule;
		Actions = actions;
	}
}