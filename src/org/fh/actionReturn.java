package org.fh;

import java.nio.file.Path;

public class actionReturn
{
	public final Path newFilePath;
	public final boolean wasSuccess;

	public actionReturn(Path newFilePath, boolean wasSuccess)
	{
		this.newFilePath = newFilePath;
		this.wasSuccess = wasSuccess;
	}
}
