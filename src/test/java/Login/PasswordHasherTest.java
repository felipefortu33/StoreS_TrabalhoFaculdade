package Login;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PasswordHasherTest {
    @Test
    void hashValidoDeveAceitarSenhaOriginal() {
        String hash = PasswordHasher.hash("senha-segura");

        assertTrue(PasswordHasher.matches("senha-segura", hash));
        assertFalse(PasswordHasher.matches("senha-incorreta", hash));
    }

    @Test
    void hashesDaMesmaSenhaDevemUsarSaltDiferente() {
        assertNotEquals(PasswordHasher.hash("senha-segura"), PasswordHasher.hash("senha-segura"));
    }

    @Test
    void hashMalformadoDeveSerRecusado() {
        assertFalse(PasswordHasher.matches("senha-segura", "hash-invalido"));
    }
}
