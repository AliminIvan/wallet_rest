package com.ivanalimin.interview.test.wallet_rest.repository;

import com.ivanalimin.interview.test.wallet_rest.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    @Query(value = "select * from wallet where wallet_id = ?1 for update", nativeQuery = true)
    Optional<Wallet> getForUpdate(UUID uuid);
}
