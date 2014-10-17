package org.fh;

import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collections;
import java.util.EnumSet;

public class fileUserPermission implements action
{
	public Path destination;
	public AclEntryType permission;
	public UserPrincipal user;
	
	public fileUserPermission(Path destination, String user, AclEntryType permission) throws IOException
	{
		super();
		this.destination = destination;
		this.permission = permission;
		try {
			this.user = destination.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(user);
		} catch (UserPrincipalNotFoundException e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public actionReturn doWork(Path destination) {
		AclEntry.Builder builder;
		AclFileAttributeView aclAttr;
		try {
			aclAttr = Files.getFileAttributeView(destination, AclFileAttributeView.class);
		} catch (FileSystemNotFoundException e) {
			return new actionReturn("User permission(" + permission.toString() + ") for " + user.toString() + " Added", destination, false);
		} try {
	    	builder = AclEntry.newBuilder();       
		    builder.setPermissions( EnumSet.of(AclEntryPermission.READ_DATA, AclEntryPermission.EXECUTE, 
		            AclEntryPermission.READ_ACL, AclEntryPermission.READ_ATTRIBUTES, AclEntryPermission.READ_NAMED_ATTRS,
		            AclEntryPermission.WRITE_ACL, AclEntryPermission.DELETE
		    ));
		} catch (Exception e) {
			return new actionReturn("User permission(" + permission.toString() + ") for " + user.toString() + " Added", destination, false);
		} try {
	    	builder.setPrincipal(user);
		    builder.setType(permission);
		    aclAttr.setAcl(Collections.singletonList(builder.build()));
		} catch (IOException e) {
			return new actionReturn("User permission(" + permission.toString() + ") for " + user.toString() + " Added", destination, false); 
		}
		return new actionReturn("User permission(" + permission.toString() + ") for " + user.toString() + " Added", destination, true);   
	}	
}