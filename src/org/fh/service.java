package org.fh;

import java.nio.file.Paths;

public class service
{

	public static void main(String[] args) throws Exception
	{
		watcherService ws = new watcherService();

		ws.registerAll(Paths.get("c:/test/"));

		Thread t1 = new Thread(ws);
		t1.start();

	}

}
