package backend.wal.admin.exception;

import backend.wal.advice.exception.NotFoundException;

public class NotFoundAdminException extends NotFoundException {

    public NotFoundAdminException(String message) {
        super(message);
    }

    public static NotFoundAdminException notExists(String email) {
        return new NotFoundAdminException(String.format("존재하지 않는 관리자 계정 (%s) 입니다", email));
    }
}
