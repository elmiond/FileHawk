package org.fh;

import java.nio.file.Path;
import java.util.ArrayList;

public class ruleSet
{
	matchRule MatchRule;
	ArrayList<action> Actions = new ArrayList<action>();
	
	public ruleSet(matchRule matchRule, ArrayList<action> actions)
	{
		MatchRule = matchRule;
		Actions = actions;
	}

	public void doWork(Path child)
	{
		if (MatchRule.isMatch(child))
		{
			System.out.println("Matched: " + child);
			for (action a : Actions)
			{
				actionReturn aR = a.doWork(child);
				if (aR.wasSuccess)
				{
					System.out.println("Success, new path: " + aR.newFilePath);
					child = aR.newFilePath;
				}
			}
		}
	}
}
