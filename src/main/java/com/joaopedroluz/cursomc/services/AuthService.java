package com.joaopedroluz.cursomc.services;

import com.joaopedroluz.cursomc.domain.Cliente;
import com.joaopedroluz.cursomc.repositories.ClienteRepository;
import com.joaopedroluz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    private BCryptPasswordEncoder pe;
    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        cliente.setSenha(pe.encode(newPass));
        clienteRepository.save(cliente);

        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) { // Numero
            return (char) (rand.nextInt(10) + 48);
        } else if (opt == 1) { // Letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        } else { // Letra minuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
