package com.br.wallet.service;

import com.br.wallet.dto.request.WalletRequestDTO;
import com.br.wallet.dto.response.WalletResponseDTO;
import com.br.wallet.entity.Wallet;
import com.br.wallet.exception.WalletNotFoundException;
import com.br.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<WalletResponseDTO> getAllWallets() {
        logger.info("Iniciando o método de busca de wallets.");
        List<Wallet> wallets = walletRepository.findAll();
        logger.info("Finalizando a busca de todas as wallets.");
        return wallets.stream()
                .map(wallet -> new WalletResponseDTO(wallet.getId(), wallet.getUserId(), wallet.getBalance()))
                .collect(Collectors.toList());
    }

    public WalletResponseDTO getWalletById(Long id) {
        logger.info("Iniciando a busca por uma wallet com id {}.", id);
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet with id " + id + " not found."));
        logger.info("Finalizando a busca por uma walled com id {}.", wallet.getId());
        return new WalletResponseDTO(wallet.getId(), wallet.getUserId(), wallet.getBalance());
    }

    public WalletResponseDTO createWallet(WalletRequestDTO walletRequestDTO) {
        Wallet wallet = new Wallet(walletRequestDTO.getUserId(), walletRequestDTO.getInitialBalance());
        Wallet savedWallet = walletRepository.save(wallet);
        return new WalletResponseDTO(savedWallet.getId(), savedWallet.getUserId(), savedWallet.getBalance());
    }

    public WalletResponseDTO updateWallet(Long id, WalletRequestDTO walletRequestDTO) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet with id " + id + " not found."));

        wallet.setUserId(walletRequestDTO.getUserId());
        wallet.setBalance(walletRequestDTO.getInitialBalance());

        Wallet updatedWallet = walletRepository.save(wallet);
        return new WalletResponseDTO(updatedWallet.getId(), updatedWallet.getUserId(), updatedWallet.getBalance());
    }

    public void deleteWallet(Long id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet with id " + id + " not found."));
        walletRepository.delete(wallet);
    }
}
