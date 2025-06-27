package model;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.*;
import java.util.Base64;

public class RSA {
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    // Constructor mặc định
    public RSA() {
    }

    // Constructor truyền vào khóa
    public RSA(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    // Tạo key mới
    public void genKey(int keyLength) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(keyLength, new java.security.SecureRandom());
        generator.initialize(keyLength);
        keyPair = generator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    // Get khóa ở dạng base64
    public String getPublicKeyBase64() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String getPrivateKeyBase64() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    // Set khóa từ base64
    public void setPublicKeyFromBase64(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        this.publicKey = factory.generatePublic(spec);
    }

    public void setPrivateKeyFromBase64(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        this.privateKey = factory.generatePrivate(spec);
    }

    // Getter
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
    public static boolean isValidPrivateKey(String base64PrivateKey) throws Exception {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(spec);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        String key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCM/ZS2U74SPcw7sZKhizMSUkiZbKmkuvZklds/3iPHmMrULjeAoNefsMBNfEMx+oszaAOd8EuL2s8fw+kjhedkdjcGTCRFw9jtOPYBvfmPEW0JpyU5VvbjpX3z59Tcy2Fl3Froz82M0t1prUDmSFvOS1ap6g8GeeS44kYhFpAJODBq7WG7ZiWwjZi7sk5of7yMBHTgOdMBVMyJGVxbeDD3Mb8cXJaeHNoCDWYXC0v0TELQdXFqjIwK/9OZZ1rXFdhhZWPmaiNnHqvD5jo5e746UnX33E/iROCZz8Q6wvCcj0/EUeFbjOOxh7C1n6/fmzdeKhcLhZEyDP5exfmbz47dAgMBAAECggEAAozMkhV9lfaEhyaT3IbBT63FF+Bt/8LoVW52202uxnuj7zRIEqHjzsTVuY7CZ6au3w2vFaJyb1g44ku66Njjr2NKZSADWhPQekULOAHrNsH25Dgf3p8WN2hSz8BCxgFl0mRGNaasy7vZCIso+DprTcyxh65Fp/C48jD0EK9aP3xpst5e5HPQDVmbhCc+2oGABRC/DHYH5BHoaDd7ktORg13F6Xvp3dH6237KWQWa3Is9nE6BSd7Zz3VFYXK+rrHpPoVhJQT+zD+QbX2+5gxRL8N7E+4uvB63eKxm7l5FY8Cx/zGZE+fNe9QB8Hb9xi66te076MJgWZEb3/aH3bjUaQKBgQC7YwOAtIzN4FtAx8zARbwUbWb+io6ZDfrfF7rn3vkSdwCBsUYf22GMhDA9iP6IdIi0QA5dTx0yw0SroVHB1pYbbNImMjzn8fzrvmFMRZeCJGaP0iTCfEg4mGIkhqtajkXSFGfFct85G/O37Qd1ugYDGhi1WOaM4gT0knqw3ECRBQKBgQDAnYvXs245347hFj/okzn1lC3i7OVg+VtxvUMCgBdYzBuFRF+QslXVSJ/8i1Bph1jkvFQhGatZzLktgZhJ1j93v/8/MPEJqz98XPOl9jh90TSKG2rYcDuroae2yQlgWZkIGOOIVd0+TmKeIksWeyeO4UtaN5ml2+BjeVF77RRN+QKBgQCuK9sOn0Mc/QN0lAGhWW/3ALUxtLHUW2UqRZFJgju6SPgklBSc+bmS9S8tkw8a4E5MVBnF6wo1q8BBUwF+DSmatiwiqqFn6cnviRZA4ZJwucrGtUnLp98tE55tWusckY8eNC88HIiVOYDwHRXI/C4oZfI/ZTFuE4sNlLULfL1azQKBgCObPCW0urSiGhSL+2ZwCO/X45+mcUS68+eTuBZ3PVmh6Zh6KjxiazGRSlnyGvxXE9y838R6rU1dZgH+iftkSrlFWdcBoh1KdeL0f+n3GTxQ9yhspxS5sp4otQs6IKyaA4sWpsEmNk71SI+5AS53t7lW9ooH6zRY8CMTYzfIfM9pAoGAaEfznxmHT6EXnyNq03zj3eJcS04OWzfGr0r8x99Fh+3UifBNrxv8TEhqJRY24etow0+If5yJDgkMELJwEN0eaz1Rea0x2B4rNLrxGy+TGKjinSF2nkWSTgg3d9iXvRm7hfVwxa/dqhli7zw3/n3qaphgtBTBQB28oSNN6Ave87o=";
        System.out.println("check: " + isValidPrivateKey(key));
    }
}
