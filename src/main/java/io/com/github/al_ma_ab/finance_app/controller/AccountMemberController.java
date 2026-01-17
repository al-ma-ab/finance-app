package io.com.github.al_ma_ab.finance_app.controller;

import io.com.github.al_ma_ab.finance_app.dto.AccountResponse;
import io.com.github.al_ma_ab.finance_app.dto.InviteMemberRequest;
import io.com.github.al_ma_ab.finance_app.service.AccountMemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountMemberController {

    private final AccountMemberService accountMemberService;

    public AccountMemberController(AccountMemberService accountMemberService) {
        this.accountMemberService = accountMemberService;
    }

    @PostMapping("/{accountId}/invite")
    public ResponseEntity<AccountResponse> invite(
            @PathVariable UUID accountId,
            @Valid @RequestBody InviteMemberRequest request,
            Authentication authentication
    ) {
        String inviterEmail = authentication.getName();
        var resp = accountMemberService.inviteMember(accountId, inviterEmail, request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}
