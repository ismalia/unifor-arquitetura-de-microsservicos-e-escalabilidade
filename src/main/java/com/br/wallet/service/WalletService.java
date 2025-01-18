package com.br.wallet.service;

import com.br.wallet.dto.request.WalletRequestDTO;
import com.br.wallet.dto.response.WalletResponseDTO;
import com.br.wallet.entity.Wallet;
import com.br.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public WalletResponseDTO createWallet(WalletRequestDTO walletRequestDTO) {
        Wallet wallet = new Wallet(walletRequestDTO.getUserId(), walletRequestDTO.getInitialBalance());
        Wallet savedWallet = walletRepository.save(wallet);

        return new WalletResponseDTO(savedWallet.getId(), savedWallet.getUserId(), savedWallet.getBalance());
    }
}
