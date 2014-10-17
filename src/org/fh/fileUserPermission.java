package org.fh;

import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.acl.AclNotFoundException;
import java.util.Collections;
import java.util.EnumSet;

public class fileUserPermission
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

	public actionReturn doWork(Path file, UserPrincipal userName, AclEntryType perm)
	{
		AclEntry.Builder builder;
		AclFileAttributeView aclAttr;
		try {
			aclAttr = Files.getFileAttributeView(file, AclFileAttributeView.class);
		} catch (FileSystemNotFoundException e) {
			//TODO: handle exception
			return new actionReturn("User permission(" + perm.toString() + ") for " + userName.toString() + " Added", file, false);
		} try {
	    	builder = AclEntry.newBuilder();       
		    builder.setPermissions( EnumSet.of(AclEntryPermission.READ_DATA, AclEntryPermission.EXECUTE, 
		            AclEntryPermission.READ_ACL, AclEntryPermission.READ_ATTRIBUTES, AclEntryPermission.READ_NAMED_ATTRS,
		            AclEntryPermission.WRITE_ACL, AclEntryPermission.DELETE
		    ));
		} catch (Exception e) {
			// TODO: handle exception
			return new actionReturn("User permission(" + perm.toString() + ") for " + userName.toString() + " Added", file, false);
		} try {
	    	builder.setPrincipal(user);
		    builder.setType(permission);
		    aclAttr.setAcl(Collections.singletonList(builder.build()));
		} catch (IOException e) {
			// TODO: handle exception
			return new actionReturn("User permission(" + perm.toString() + ") for " + userName.toString() + " Added", file, false); 
		}
		return new actionReturn("User permission(" + perm.toString() + ") for " + userName.toString() + " Added", file, true);   
	}	
}