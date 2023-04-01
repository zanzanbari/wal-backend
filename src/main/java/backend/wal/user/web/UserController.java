package backend.wal.user.web;

import backend.wal.user.application.port.in.ResignUserUseCase;
import backend.wal.user.application.port.in.RetrieveUserInfoUseCase;
import backend.wal.user.application.port.in.ChangeUserInfoUseCase;
import backend.wal.user.application.port.in.RegisterResignInfoUseCase;
import backend.wal.user.web.dto.*;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/user/me")
public class UserController {

    private final ChangeUserInfoUseCase changeUserInfoUseCase;
    private final RegisterResignInfoUseCase registerResignInfoUseCase;
    private final ResignUserUseCase resignUserUseCase;
    private final RetrieveUserInfoUseCase retrieveUserInfoUseCase;

    @PatchMapping("/nickname/edit")
    public ResponseEntity<Void> changeNickname(@Valid @RequestBody ModifyNicknameRequest request, Long userId) {
        changeUserInfoUseCase.changeNickname(request.getNickname(), userId);
        return ResponseEntity.noContent().build();
    }

    @Authentication
    @PostMapping("/resign")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterResignRequest request, @LoginUser Long userId) {
        registerResignInfoUseCase.register(request.toServiceDto(userId));
        resignUserUseCase.resign(userId);
        return ResponseEntity.noContent().build();
    }

    @Authentication
    @GetMapping("/nickname")
    public ResponseEntity<NicknameInfoResponse> retrieveNickname(@LoginUser Long userId) {
        String nickname = retrieveUserInfoUseCase.retrieveNickname(userId);
        return ResponseEntity.ok(new NicknameInfoResponse(nickname));
    }

    @Authentication
    @GetMapping("/time")
    public ResponseEntity<TimeInfoResponse> retrieveTimeInfo(@LoginUser Long userId) {
        return ResponseEntity.ok(retrieveUserInfoUseCase.retrieveTimeInfo(userId).toWebResponse());
    }

    @Authentication
    @GetMapping("/category")
    public ResponseEntity<CategoryInfoResponse> retrieveCategoryInfo(@LoginUser Long userId) {
        return ResponseEntity.ok(retrieveUserInfoUseCase.retrieveCategoryInfo(userId).toWebResponse());
    }
}
