package main.subtitles;

import java.util.LinkedList;

interface SubtitlesReader {
    <T extends SubtitlesFormat> LinkedList<T> read(final String path) throws Exception;
}
