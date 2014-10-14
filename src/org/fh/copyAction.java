package org.fh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class copyAction implements action
{
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
			if (!Files.notExists(newFilePath))
			{
				try {
					Files.createDirectory(newFilePath);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			Files.copy(filePath, newFilePath);
			return new actionReturn("copy", newFilePath, true);
			
		} catch (IOException | InterruptedException e)
		{
			return new actionReturn("copy", filePath, false);
		}
	}
}