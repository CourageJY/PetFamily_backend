package com.pet.util.utils;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionTest {
    @org.junit.jupiter.api.Test
    void shiroEncryption() {
        String pwd="123456";
        String salt=Encryption.generateSalt();
        System.out.println(Encryption.shiroEncryption(pwd,salt));
    }

    @org.junit.jupiter.api.Test
    void generateSalt() {
        System.out.println(Encryption.generateSalt());
    }
}