package main.subtitles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class MicroDVDReader implements SubtitlesReader{
    @Override
    public <T extends SubtitlesFormat> LinkedList<T> read(String path) throws Exception{
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String st;
        while ((st = bufferedReader.readLine()) != null)
            System.out.println(st);

        return null;
    }

    public static void main(String[] args) {
        try {
            new MicroDVDReader().read("C:\\Users\\piotr\\Documents\\VideoPlayerV2\\src\\main\\subtitles\\subTestSRT.srt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
