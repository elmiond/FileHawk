package org.fh;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class folder
{
	Path folderPath;
	matchRule MatchRule;
	ArrayList<action> Actions = new ArrayList<action>();
	public folder(Path path)
	{
		folderPath = path;
		MatchRule = new regexRule(".*Image.*");
		Actions.add(new moveAction());
		((moveAction)Actions.get(0)).destination = Paths.get("c:/images/");
	}
	public folder(Path path,matchRule rule,ArrayList<action> actions)
	{
		this(path);
		MatchRule = rule;
		Actions = actions;
	}
}