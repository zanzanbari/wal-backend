package backend.wal.admin.web;

import backend.wal.admin.web.dto.RetrieveCensorItemResponse;
import backend.wal.admin.web.dto.RetrieveCensorItemRequest;
import backend.wal.admin.web.dto.UpdateCensorItemInfoRequest;
import backend.wal.admin.application.port.out.CensorItemManagePort;

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

    @GetMapping("/retrieve")
    public ResponseEntity<List<RetrieveCensorItemResponse>> retrieveCensorItems(
            @Valid @RequestBody RetrieveCensorItemRequest request) {
        List<RetrieveCensorItemResponse> responses = censorItemManagePort.retrieveCensorItemInfo(request);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> update(@Valid @RequestBody UpdateCensorItemInfoRequest request) {
        censorItemManagePort.updateCheckStatus(request.getCensorItemId(), request.getCheckStatus());
        return ResponseEntity.noContent().build();
    }
}