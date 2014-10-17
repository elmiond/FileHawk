package org.fh;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import com.chilkatsoft.CkRar;

public class fileUnrarAction implements action {
	
	static {
		try {
			System.loadLibrary("chilkat");
		} catch (UnsatisfiedLinkError e) {
			System.exit(1);
		}
	}

	public fileUnrarAction(Path filePath) {
		super();
	}

	@Override
	public actionReturn doWork(Path filePath) {
		CkRar rar = new CkRar();
		if (Files.isDirectory(filePath)) {
			File[] fileList = listFilesForFolder(filePath.toFile());
			for (File file : fileList) {
				if(getFileExtension(file).equals("rar")){
					String fileString = file.toString();
					if(rar.Open(fileString) && rar.Unrar(fileString)){
						return new actionReturn("Unrar", file.toPath(), true);
					}else return new actionReturn("Unrar", file.toPath(), false);
				}
			}
		}
		if(getFileExtension(filePath.toFile()).equals("rar")){
			if(rar.Open(filePath.toString()) && rar.Unrar(filePath.toString()))
				return new actionReturn("Unrar", filePath, true);
		}else return new actionReturn("Unrar", filePath, false);
		return new actionReturn("Unrar", filePath, false);
	}
	public File[] listFilesForFolder(final File folder) {
	    File[] filelist = folder.listFiles();
		return filelist;
	    
	}
	private String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
	}
	
}
