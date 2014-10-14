package org.fh;

import java.util.ArrayList;

public class service
{

	public static void main(String[] args) throws Exception
	{
		watcherService ws = new watcherService();

		configHandler ch = new configHandler();
		ArrayList<folder> folders = ch.getFolders();
		for (folder f : folders)
		{
			ws.registerAll(f.folderPath);
		}
		

		Thread t1 = new Thread(ws);
		t1.start();

	}

}
