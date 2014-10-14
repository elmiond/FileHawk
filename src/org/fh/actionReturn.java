package org.fh;

import java.nio.file.Path;

public class actionReturn
{
	public final Path newFilePath;
	public final boolean wasSuccess;
	public final String action;
	

	public actionReturn(String action, Path newFilePath, boolean wasSuccess)
	{
		this.newFilePath = newFilePath;
		this.wasSuccess = wasSuccess;
		this.action = action;
	}
}
	
