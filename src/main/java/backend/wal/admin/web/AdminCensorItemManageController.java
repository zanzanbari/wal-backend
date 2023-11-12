package backend.wal.admin.web;

import backend.wal.admin.web.dto.RetrieveCensorItemResponse;
import backend.wal.admin.web.dto.RetrieveCensorItemRequest;
import backend.wal.admin.web.dto.UpdateCensorItemInfoRequest;
import backend.wal.admin.application.port.out.CensorItemManagePort;

import backend.wal.support.Role;
import backend.wal.support.annotation.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/censor")
public class AdminCensorItemManageController {

    private final CensorItemManagePort censorItemManagePort;

    public AdminCensorItemManageController(final CensorItemManagePort censorItemManagePort) {
        this.censorItemManagePort = censorItemManagePort;
    }

    @Authentication(Role.ADMIN)
    @GetMapping("/{categoryType}")
    public ResponseEntity<List<RetrieveCensorItemResponse>> retrieveCensorItems(
            @Valid @RequestParam RetrieveCensorItemRequest request) {
        List<RetrieveCensorItemResponse> responses = censorItemManagePort.retrieveUncheckedCensorItemInfo(request);
        return ResponseEntity.ok(responses);
    }

    @Authentication(Role.ADMIN)
    @PatchMapping("/update")
    public ResponseEntity<Void> update(@Valid @RequestBody UpdateCensorItemInfoRequest request) {
        censorItemManagePort.updateCheckStatus(request.getCensorItemId(), request.getCheckStatus());
        return ResponseEntity.noContent().build();
    }
}