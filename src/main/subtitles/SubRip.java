package main.subtitles;

public class SubRip implements SubtitlesFormat {
    private int startTime;
    private int endTime;
    private String[] subtitle;

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String[] getSubtitle() {
        return subtitle;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setSubtitle(String[] subtitle) {
        this.subtitle = subtitle;
    }
}
