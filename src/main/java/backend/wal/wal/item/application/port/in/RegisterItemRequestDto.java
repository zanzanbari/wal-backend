package backend.wal.wal.item.application.port.in;

public class RegisterItemRequestDto {

    private final String contents;
    private final String imageUrl;
    private final int categoryItemNumber;

    public RegisterItemRequestDto(String contents, String imageUrl, int categoryItemNumber) {
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

    public int getCategoryItemNumber() {
        return categoryItemNumber;
    }
}
