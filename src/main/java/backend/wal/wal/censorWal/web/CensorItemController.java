package backend.wal.wal.censorWal.web;

import backend.wal.wal.censorWal.application.port.in.CreateCensorItemUseCase;
import backend.wal.wal.censorWal.web.dto.CreateCensorItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/censor")
public class CensorItemController {

    private final CreateCensorItemUseCase createCensorItemUseCase;

    public CensorItemController(final CreateCensorItemUseCase createCensorItemUseCase) {
        this.createCensorItemUseCase = createCensorItemUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateCensorItemRequest request) {
        createCensorItemUseCase.create(request.toServiceDto());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
