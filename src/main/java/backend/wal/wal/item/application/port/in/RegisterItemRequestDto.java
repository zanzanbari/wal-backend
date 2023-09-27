package backend.wal.wal.item.application.port.in;

public class RegisterItemRequestDto {

    private final String contents;
    private final String imageUrl;
    private final double categoryItemNumber;

    public RegisterItemRequestDto(String contents, String imageUrl, double categoryItemNumber) {
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.categoryItemNumber = categoryItemNumber;
    }

    public String getContents() {
        return contents;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getCategoryItemNumber() {
        return categoryItemNumber;
    }
}
