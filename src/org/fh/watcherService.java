package org.fh;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class watcherService implements Runnable
{
	private final WatchService watcher;
	private Map<WatchKey, folder> folders = new HashMap<WatchKey, folder>();

	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event)
	{
		return (WatchEvent<T>) event;
	}

	/**
	 * Register the given directory with the WatchService
	 */
	private void register(Path dir, ArrayList<ruleSet> rules) throws IOException
	{
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE,
				ENTRY_MODIFY);
		if (folders.get(key) == null)
		{
			System.out.format("register: %s\n", dir);
		} else
		{
			Path prev = folders.get(key).folderPath;
			if (!dir.equals(prev))
			{
				System.out.format("update: %s -> %s\n", prev, dir);
			}
		}
		folders.put(key, new folder(dir, rules));
	}

	/**
	 * Register the given directory, and all its sub-directories, with the
	 * WatchService.
	 */
	public void registerAll(final folder f) throws IOException
	{
		// register directory and sub-directories
		Files.walkFileTree(f.folderPath, new SimpleFileVisitor<Path>()
		{
			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException
			{
				register(dir, new ArrayList<ruleSet>(f.rules));
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * Creates a WatchService and registers the given directory
	 */
	watcherService() throws IOException
	{
		this.watcher = FileSystems.getDefault().newWatchService();
	}

	/**
	 * Process all events for keys queued to the watcher
	 */
	public void run()
	{
		Path config = Paths.get("config.xml");
		ArrayList<ruleSet> r = new ArrayList<ruleSet>();
		try
		{
			register(config.toAbsolutePath().getParent(), r);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true)
		{

			// wait for key to be signalled
			WatchKey key;
			try
			{
				key = watcher.take();
			} catch (InterruptedException x)
			{
				return;
			}
			folder f = folders.get(key);
			if (f.folderPath == null)
			{
				System.err.println("WatchKey not recognized!!");
				continue;
			}

			for (WatchEvent<?> event : key.pollEvents())
			{
				WatchEvent.Kind kind = event.kind();

				// TBD - provide example of how OVERFLOW event is handled
				if (kind == OVERFLOW)
				{
					continue;
				}

				// Context for directory entry event is the file name of entry
				WatchEvent<Path> ev = cast(event);
				Path name = ev.context();
				Path child = f.folderPath.resolve(name);

				// print out event
				System.out.format("%s: %s\n", event.kind().name(), child);

				// if directory is created, and watching recursively, then
				// register it and its sub-directories

				// child.getFileName().toString().equals("config.xml")
				if (child.toAbsolutePath().equals(config.toAbsolutePath()))
				{
					//discard events for FileHawk root dir not pertaining to the config file
					if (child.getFileName().toString().equals("config.xml"))
					{
						System.out.println("config changed");
					}
				} else if (!child.getFileName().toString().startsWith("~"))
				{

					if (kind == ENTRY_CREATE)
					{
						try
						{
							if (Files.isDirectory(child, NOFOLLOW_LINKS))
							{
								registerAll(f);
							} else
							{
								f.doWork(child);
							}
						} catch (IOException x)
						{
							// ignore to keep sample readable
						}
					}
					if (kind == ENTRY_MODIFY)
					{
						if (!Files.isDirectory(child, NOFOLLOW_LINKS))
						{
							f.doWork(child);
						}
					}

				}
			}

			// reset key and remove from set if directory no longer accessible
			if (!key.reset())
			{
				folders.remove(key);

				// all directories are inaccessible
				if (folders.isEmpty())
				{
					break;
				}
			}
		}
	}
}
