package org.fh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class moveAction implements action
{
	public moveAction(Path destination)
	{
		super();
		this.destination = destination;
	}

	public Path destination;

	public actionReturn doWork(Path filePath)
	{
		Path newFilePath = destination.resolve(filePath.getFileName());
		System.out.println(newFilePath);
		try
		{
			int i = 0;
			while (!filePath.toFile().exists())
			{
				if (i >= 120)
				{
					break;
				}
				Thread.sleep(100);
				i++;
			}
			Files.move( filePath, newFilePath);
			return new actionReturn("move", newFilePath, true);
		} catch (IOException | InterruptedException e)
		{
			return new actionReturn("move", filePath, false);
		}
	}
}
