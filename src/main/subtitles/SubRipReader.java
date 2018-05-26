package main.subtitles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class SubRipReader implements SubtitlesReader {
    @Override
    public <T extends SubtitlesFormat> LinkedList<T> read(String path) throws Exception {
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        LinkedList<SubRip> subtitles = new LinkedList<>();

        String line;
        int iteratorOfKindOfLine = 1; //1 stands for index, 2 for time, 3 for subtitles
        int i=0;
        while ((line = bufferedReader.readLine()) != null) {
            if(line.equals("")){

            }
            //System.out.println(line);
            switch (iteratorOfKindOfLine){
                case 1:
                    SubRip subRip = new SubRip();
                    subtitles.add(subRip);
                    iteratorOfKindOfLine++;
                case 2:


            }


        }

        return null;
    }

    public static void main(String[] args) {
        try {
            new SubRipReader().read("C:\\Users\\piotr\\Documents\\VideoPlayerV2\\src\\main\\subtitles\\subTestSRT.srt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
