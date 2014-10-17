package org.fh;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class deleteAction implements action
{
	public Path destination;

	public deleteAction(Path destination)
	{
		super();
		this.destination = destination;
	}

	public actionReturn doWork(Path filePath)
	{
		try
		{
			Files.walkFileTree(filePath, new SimpleFileVisitor<Path>()
			{
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
	                throws IOException
	        {
	            Files.delete(file);
	            return FileVisitResult.CONTINUE;
	        }

	        @Override
	        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
	        {
	            // try to delete the file anyway, even if its attributes
	            // could not be read, since delete-only access is
	            // theoretically possible
	            Files.delete(file);
	            return FileVisitResult.CONTINUE;
	        }

	        @Override
	        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
	        {
	            if (exc == null)
	            {
	                Files.delete(dir);
	                return FileVisitResult.CONTINUE;
	            }
	            else
	            {
	                // directory iteration failed; propagate exception
	                throw exc;
	            }
	        }
			});
			
			return new actionReturn("delete", filePath, true);
			
		} catch (IOException e)
		{
			return new actionReturn("delete", filePath, false);
		}
	}
}

