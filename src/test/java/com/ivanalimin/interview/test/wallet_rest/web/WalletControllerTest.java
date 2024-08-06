package com.ivanalimin.interview.test.wallet_rest.web;

import com.ivanalimin.interview.test.wallet_rest.model.Wallet;
import com.ivanalimin.interview.test.wallet_rest.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static com.ivanalimin.interview.test.wallet_rest.web.WalletTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/test-data.sql")
class WalletControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = WalletController.REST_URL + '/';
    private static final String REST_URL_WALLET = "wallet";
    private static final String REST_URL_WALLETS = "wallets/";
    @Autowired
    private WalletRepository repository;

    @Test
    void get() throws Exception {
        MvcResult result = perform(MockMvcRequestBuilders.get(REST_URL_SLASH + REST_URL_WALLETS + UUID_1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = toJson(WALLET_1);
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + REST_URL_WALLETS + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void changeAmountWhenDeposit() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + REST_URL_WALLET)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "walletId": "cf936b56-f9e1-42d6-85ed-0e1322674053",
                            "operationType": "DEPOSIT",
                            "amount": 2000
                        }
                        """))
                .andDo(print())
                .andExpect(status().isNoContent());
        Wallet dbWalletAfterUpdate = repository.findById(UUID_2).orElseThrow();
        assertEquals(BigDecimal.valueOf(5000), dbWalletAfterUpdate.getAmount());
    }

    @Test
    void changeAmountWhenWithdraw() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + REST_URL_WALLET)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "walletId": "cf936b56-f9e1-42d6-85ed-0e1322674053",
                            "operationType": "WITHDRAW",
                            "amount": 2000
                        }
                        """))
                .andDo(print())
                .andExpect(status().isNoContent());
        Wallet dbWalletAfterUpdate = repository.findById(UUID_2).orElseThrow();
        assertEquals(BigDecimal.valueOf(1000), dbWalletAfterUpdate.getAmount());
    }

    @Test
    void changeAmountNotFound() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + REST_URL_WALLET)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "walletId": "cf936b56-f9e1-42d6-85ed-0e1322674088",
                            "operationType": "DEPOSIT",
                            "amount": 2000
                        }
                        """))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void changeAmountWhenInsufficientFunds() throws Exception {
        MvcResult result = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + REST_URL_WALLET)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "walletId": "cf936b56-f9e1-42d6-85ed-0e1322674053",
                            "operationType": "WITHDRAW",
                            "amount": 5000
                        }
                        """))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
        assertEquals("Insufficient funds for the operation", result.getResponse().getContentAsString());
    }

    @Test
    void changeAmountWhenIncorrectJson() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + REST_URL_WALLET)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "walletId": "cf936b56-f9e1-42d6-85ed-0e1322674053",
                            "operationType": "WITHDRAW",
                            "amount": "no money"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}