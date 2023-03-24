package backend.wal.resign.controller;

import backend.wal.auth.support.Authentication;
import backend.wal.auth.support.LoginUser;
import backend.wal.resign.app.service.ResignService;
import backend.wal.resign.controller.dto.RegisterResignRequest;
import backend.wal.user.app.service.ResignUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/resign")
public class ResignController {

    private final ResignService resignService;
    private final ResignUserService resignUserService;

    @Authentication
    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterResignRequest request, @LoginUser Long userId) {
        resignService.registerResignInfo(request.toServiceDto(userId));
        resignUserService.resign(userId);
        return ResponseEntity.noContent().build();
    }
}
