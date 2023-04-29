package backend.wal.subtitle;

public class HomeSubtitleResponse {

    private String todaySubtitle;

    private HomeSubtitleResponse() {
    }

    public HomeSubtitleResponse(final String todaySubtitle) {
        this.todaySubtitle = todaySubtitle;
    }

    public String getTodaySubtitle() {
        return todaySubtitle;
    }
}
