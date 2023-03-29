package backend.wal.user.web;

import backend.wal.user.application.port.ResignUserUseCase;
import backend.wal.user.web.dto.RegisterResignRequest;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.LoginUser;
import backend.wal.user.application.port.ChangeUserInfoUseCase;
import backend.wal.user.application.port.RegisterResignInfoUseCase;
import backend.wal.user.web.dto.ModifyNicknameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/user")
public class UserController {

    private final ChangeUserInfoUseCase changeUserInfoUseCase;
    private final RegisterResignInfoUseCase registerResignInfoUseCase;
    private final ResignUserUseCase resignUserUseCase;

    @PatchMapping("/me/nickname/edit")
    public ResponseEntity<Void> changeNickname(@Valid @RequestBody ModifyNicknameRequest request, Long userId) {
        changeUserInfoUseCase.changeNickname(request.getNickname(), userId);
        return ResponseEntity.noContent().build();
    }

    @Authentication
    @PostMapping("/me/resign")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterResignRequest request, @LoginUser Long userId) {
        registerResignInfoUseCase.register(request.toServiceDto(userId));
        resignUserUseCase.resign(userId);
        return ResponseEntity.noContent().build();
    }
}
