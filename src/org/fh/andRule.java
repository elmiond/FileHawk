package org.fh;

import java.nio.file.Path;

public class andRule implements matchRuleCollection
{

	public boolean isMatch(Path filePath)
	{
		for (matchRule r : rules)
		{
			if (!r.isMatch(filePath))
			{
				return false;
			}
		}
		return true;
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
