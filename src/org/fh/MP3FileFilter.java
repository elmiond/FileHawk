package org.fh;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class MP3FileFilter implements FileFilter {
	public boolean accept(File pathname) {
		return pathname.getName().toLowerCase().endsWith(".mp3")
				|| pathname.isDirectory();
	}
}
