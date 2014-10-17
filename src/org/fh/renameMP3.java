package org.fh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class renameMP3 {
	Path destination;

	public renameMP3(File dir) throws FileNotFoundException, IOException {
		renameMP3(dir);
	}

	public void renameMP3(File dir) throws FileNotFoundException, IOException {
		File[] MP3Files;

		int i = 0;

		if (dir.isDirectory()) {
			MP3Files = dir.listFiles(new MP3FileFilter());
			for (File f : MP3Files) {
				i++;

				if (f.isDirectory()) {
					renameMP3(f);
					continue;
				}

				id3Editor ID3Tag = new id3Editor(f);
				if (f.isFile() && ID3Tag.ID3Exists()) {
					ID3Tag.readID3();

					if (!ID3Tag.getArtist().equals("")
							&& !ID3Tag.getTitle().equals("")) {
						File newFile = new File(dir, ID3Tag.getArtist() + " - "
								+ ID3Tag.getTitle() + ".mp3");
					}
				}

			}

		}
	}
}
