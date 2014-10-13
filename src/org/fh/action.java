package org.fh;

import java.nio.file.Path;

public interface action
{
	public actionReturn doWork(Path filePath);
}
