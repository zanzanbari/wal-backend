package backend.wal.resign.controller;

import backend.wal.auth.support.Authentication;
import backend.wal.auth.support.LoginUser;
import backend.wal.resign.app.service.ResignService;
import backend.wal.resign.controller.dto.RegisterResignRequest;
import backend.wal.user.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ResignController {

    private final ResignService resignService;
    private final UserService userService;

    @Authentication
    @PostMapping("/v2/resign")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterResignRequest request, @LoginUser Long userId) {
        resignService.registerResignInfo(request.toServiceDto(userId));
        userService.resign(userId);
        return ResponseEntity.noContent().build();
    }
}
