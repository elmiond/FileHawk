package org.fh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalNotFoundException;

public class fileEditOwner implements action {		
	public Path destination;
	public AclEntryType permission;
	public UserPrincipal user;
	
	public fileEditOwner(Path destination, String user) throws IOException
	{
		super();
		this.destination = destination;
		try {
			this.user = destination.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(user);
		} catch (UserPrincipalNotFoundException e) {
			// TODO: handle exception
		}
			
	}
	@Override
	public actionReturn doWork(Path destination)
	{
		try {
			Files.setOwner(destination, user);
		} catch (IOException e) {
			return new actionReturn("Change Owner for " + user.toString(), destination, false); 
		}
		return new actionReturn("Change Owner for " + user.toString(), destination, true);   
	}
}