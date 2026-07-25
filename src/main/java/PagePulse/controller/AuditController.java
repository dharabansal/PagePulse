package PagePulse.controller;


import PagePulse.dto.AuditRequest;
import PagePulse.dto.AuditResponse;
import PagePulse.service.AuditService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/api/audit")
@CrossOrigin(origins = "*")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }


    @PostMapping
    public AuditResponse auditWebsite(@Valid @RequestBody AuditRequest request) {

        return auditService.analyzeWebsite(request.getUrl());
    }
}