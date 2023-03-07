package backend.wal.user.controller;

import backend.wal.user.controller.dto.ModifyNicknameRequest;
import backend.wal.user.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/v2/user/me/nickname/edit")
    public ResponseEntity<Void> changeNickname(@Valid @RequestBody ModifyNicknameRequest request, Long userId) {
        userService.changeUserNickname(request.getNickname(), userId);
        return ResponseEntity.noContent().build();
    }
}
