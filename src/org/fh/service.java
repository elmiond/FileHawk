package org.fh;

import java.util.ArrayList;

public class service
{

	public static void main(String[] args) throws Exception
	{
		watcherService ws = new watcherService();

		
		
		

		Thread t1 = new Thread(ws);
		t1.start();

	}

}
