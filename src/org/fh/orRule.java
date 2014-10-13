package org.fh;

import java.nio.file.Path;

public class orRule implements matchRuleCollection
{

	public boolean isMatch(Path filePath)
	{
		for (matchRule r : rules)
		{
			if (r.isMatch(filePath))
			{
				return true;
			}
		}
		return false;
	}
	
	public void add(matchRule rule)
	{
		rules.add(rule);
	}

	public void remove(int index)
	{
		rules.remove(index);
	}
}
