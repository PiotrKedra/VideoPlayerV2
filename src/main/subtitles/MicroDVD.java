package main.subtitles;

public class MicroDVD implements SubtitlesFormat {
    //{startFrame}{endFrame} subtitle
    private int startFrame;
    private int endFrame;
    private String subtitle;

    public int getStartFrame() {
        return startFrame;
    }

    public int getEndFrame() {
        return endFrame;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setStartFrame(int startFrame) {
        this.startFrame = startFrame;
    }

    public void setEndFrame(int endFrame) {
        this.endFrame = endFrame;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
