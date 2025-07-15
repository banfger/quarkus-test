package hu.banfalvig.util;

import io.smallrye.jwt.build.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;

import java.util.Arrays;
import java.util.HashSet;

/**
 * A utility class to generate and print a JWT token string to stdout.
 */
@Slf4j
public class GenerateToken {

    private GenerateToken() {
    }

    /**
     * Generates and prints a JWT token.
     */
    public static void token() {
        String token = Jwt.issuer("https://example.com/issuer")
                .upn("jdoe@quarkus.io")
                .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                .claim(Claims.birthdate.name(), "2001-07-13")
                .sign();

        log.info(token);
    }
}