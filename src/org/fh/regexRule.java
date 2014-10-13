package org.fh;

import java.nio.file.Path;

public class regexRule implements matchRule
{
	public String pattern = "";
	
	public regexRule(String pattern)
	{
		this.pattern = pattern;
	}
	
	public boolean isMatch(Path filePath)
	{
		return filePath.getFileName().toString().matches(pattern);
	}

}
