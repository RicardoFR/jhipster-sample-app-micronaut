package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.BankAccount;
import io.github.jhipster.sample.repository.BankAccountRepository;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the {@Link BankAccountResource} REST controller.
 */
@MicronautTest(transactional = false)
@Property(name = "micronaut.security.enabled", value = "false")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankAccountResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1.00);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    @Inject
    private BankAccountRepository bankAccountRepository;

    @Inject @Client("/")
    RxHttpClient client;

    private BankAccount bankAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankAccount createEntity() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName(DEFAULT_NAME);
        bankAccount.setBalance(DEFAULT_BALANCE);
        return bankAccount;
    }

    @BeforeEach
    public void initTest() {
        bankAccount = createEntity();
    }

    @Test
    @Transactional
    public void createBankAccount() throws Exception {
        long databaseSizeBeforeCreate = bankAccountRepository.count();

        // Create the BankAccount
        HttpResponse<BankAccount> response = client.exchange(HttpRequest.POST("/api/bank-accounts", bankAccount), BankAccount.class).blockingFirst();

        assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList.size()).isEqualTo(databaseSizeBeforeCreate + 1);
        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertEquals(testBankAccount.getBalance().compareTo(DEFAULT_BALANCE), 0);
    }

//    @Test
//    @Transactional
//    public void createBankAccountWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = bankAccountRepository.findAll().size();
//
//        // Create the BankAccount with an existing ID
//        bankAccount.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restBankAccountMockMvc.perform(post("/api/bank-accounts")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the BankAccount in the database
//        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
//        assertThat(bankAccountList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
//        // set the field null
//        bankAccount.setName(null);
//
//        // Create the BankAccount, which fails.
//
//        restBankAccountMockMvc.perform(post("/api/bank-accounts")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
//            .andExpect(status().isBadRequest());
//
//        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
//        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void checkBalanceIsRequired() throws Exception {
//        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
//        // set the field null
//        bankAccount.setBalance(null);
//
//        // Create the BankAccount, which fails.
//
//        restBankAccountMockMvc.perform(post("/api/bank-accounts")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
//            .andExpect(status().isBadRequest());
//
//        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
//        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllBankAccounts() throws Exception {
//        // Initialize the database
//        bankAccountRepository.saveAndFlush(bankAccount);
//
//        // Get all the bankAccountList
//        restBankAccountMockMvc.perform(get("/api/bank-accounts?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(bankAccount.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
//            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())));
//    }
//
//    @Test
//    @Transactional
//    public void getBankAccount() throws Exception {
//        // Initialize the database
//        bankAccountRepository.saveAndFlush(bankAccount);
//
//        // Get the bankAccount
//        restBankAccountMockMvc.perform(get("/api/bank-accounts/{id}", bankAccount.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(bankAccount.getId().intValue()))
//            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
//            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingBankAccount() throws Exception {
//        // Get the bankAccount
//        restBankAccountMockMvc.perform(get("/api/bank-accounts/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateBankAccount() throws Exception {
//        // Initialize the database
//        bankAccountRepository.saveAndFlush(bankAccount);
//
//        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
//
//        // Update the bankAccount
//        BankAccount updatedBankAccount = bankAccountRepository.findById(bankAccount.getId()).get();
//        // Disconnect from session so that the updates on updatedBankAccount are not directly saved in db
//        em.detach(updatedBankAccount);
//        updatedBankAccount.setName(UPDATED_NAME);
//        updatedBankAccount.setBalance(UPDATED_BALANCE);
//
//        restBankAccountMockMvc.perform(put("/api/bank-accounts")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedBankAccount)))
//            .andExpect(status().isOk());
//
//        // Validate the BankAccount in the database
//        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
//        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
//        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
//        assertThat(testBankAccount.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testBankAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingBankAccount() throws Exception {
//        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
//
//        // Create the BankAccount
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restBankAccountMockMvc.perform(put("/api/bank-accounts")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the BankAccount in the database
//        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
//        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteBankAccount() throws Exception {
//        // Initialize the database
//        bankAccountRepository.saveAndFlush(bankAccount);
//
//        int databaseSizeBeforeDelete = bankAccountRepository.findAll().size();
//
//        // Delete the bankAccount
//        restBankAccountMockMvc.perform(delete("/api/bank-accounts/{id}", bankAccount.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isNoContent());
//
//        // Validate the database is empty
//        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
//        assertThat(bankAccountList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankAccount.class);
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(1L);
        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setId(bankAccount1.getId());
        assertThat(bankAccount1).isEqualTo(bankAccount2);
        bankAccount2.setId(2L);
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);
        bankAccount1.setId(null);
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);
    }
}
