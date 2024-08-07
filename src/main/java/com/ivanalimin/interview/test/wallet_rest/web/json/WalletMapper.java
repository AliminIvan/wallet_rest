package com.ivanalimin.interview.test.wallet_rest.web.json;

import com.ivanalimin.interview.test.wallet_rest.dto.WalletDTO;
import com.ivanalimin.interview.test.wallet_rest.model.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    Wallet dtoToModel(WalletDTO dto);
    WalletDTO modelToDto(Wallet wallet);
}
