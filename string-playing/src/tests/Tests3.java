package tests;

import java.util.HashMap;

public class Tests3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		StringBuilder str = new StringBuilder(); 
		str.append("my.song.mp3 11b\n");
		str.append("greatSong.flac 1000b\n");
		str.append("not3.txt 5b\n");
		str.append("video.mp4 200b\n");
		str.append("game.exe 100b\n");
		str.append("mov!e.mkv 10000b\n");
		
		HashMap<String, Integer> dataSize = new HashMap<>();
		
		dataSize.put("music", 0);
		dataSize.put("images", 0);
		dataSize.put("movies", 0);
		dataSize.put("other", 0);
		
		String fileType = null;
		Integer size = 0;
		
		String lines[] = str.toString().split("\\r?\\n");
		
		for (String string : lines) {
			
			fileType = null;
			size = 0;
			
			String data[] = string.split(" ");
			
			fileType = data[0].substring(data[0].lastIndexOf(".")+1);
			
			size = Integer.valueOf(data[1].substring(0, data[1].lastIndexOf("b")));
			
			if (fileType.equalsIgnoreCase("mp3") || fileType.equalsIgnoreCase("aac") || fileType.equalsIgnoreCase("flac")) {
				
				dataSize.put("music", dataSize.get("music") + size);
				
			} else if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("bmp") || fileType.equalsIgnoreCase("gif")) {
				
				dataSize.put("images", dataSize.get("images") + size);
				
			} else if (fileType.equalsIgnoreCase("mp4") || fileType.equalsIgnoreCase("avi") || fileType.equalsIgnoreCase("mkv")) {
				
				dataSize.put("movies", dataSize.get("movies") + size);
				
			} else {
				dataSize.put("other", dataSize.get("other") + size);
			}
			
		}
		
		StringBuilder data = new StringBuilder();
		
		data.append("music");
		data.append(" ");
		data.append(dataSize.get("music"));
		data.append("b\n");
		
		data.append("images");
		data.append(" ");
		data.append(dataSize.get("images"));
		data.append("b\n");
		
		data.append("movies");
		data.append(" ");
		data.append(dataSize.get("movies"));
		data.append("b\n");
		
		data.append("other");
		data.append(" ");
		data.append(dataSize.get("other"));
		data.append("b");
		
		System.out.println(data.toString());
	}

}
