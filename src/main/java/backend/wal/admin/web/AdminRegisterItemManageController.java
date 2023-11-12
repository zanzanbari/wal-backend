package backend.wal.admin.web;

import backend.wal.admin.application.port.out.RegisterItemManagePort;
import backend.wal.admin.web.dto.RetrieveCensorItemRequest;
import backend.wal.support.Role;
import backend.wal.support.annotation.Authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/item")
public class AdminRegisterItemManageController {

    private final RegisterItemManagePort registerItemManagePort;

    public AdminRegisterItemManageController(final RegisterItemManagePort registerItemManagePort) {
        this.registerItemManagePort = registerItemManagePort;
    }

    @Authentication(Role.ADMIN)
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RetrieveCensorItemRequest request) {
        registerItemManagePort.registerItemsBy(request.getCategoryType());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
