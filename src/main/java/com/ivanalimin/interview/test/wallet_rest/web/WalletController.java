package com.ivanalimin.interview.test.wallet_rest.web;

import com.ivanalimin.interview.test.wallet_rest.dto.WalletDTO;
import com.ivanalimin.interview.test.wallet_rest.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = WalletController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class WalletController {
    static final String REST_URL = "/api/v1";
    private final WalletService service;

    @GetMapping("/wallets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WalletDTO get(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        return service.findById(uuid);
    }

    @PostMapping(value = "/wallet", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAmount(@Valid @RequestBody WalletDTO wallet) {
        service.changeAmount(wallet);
    }
}
