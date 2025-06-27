package model;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DS {

    PublicKey publicKey;
    PrivateKey privateKey;
    public DS(){}

    public void exportPublicKey(String filename) throws IOException {
        PrintWriter pw= new PrintWriter(new FileOutputStream(filename));
        pw.println("-----BEGIN PUBLIC KEY-----");
        String key=Base64.getEncoder().encodeToString(publicKey.getEncoded());
        for (int i = 0; i < key.length(); i += 64) {
            int end = Math.min(i + 64, key.length());
            pw.println(key.substring(i,end));
        }
        pw.println("-----END PUBLIC KEY-----");
        pw.close();
    }

    public void exportPrivateKey(String filename) throws IOException {
        PrintWriter pw= new PrintWriter(new FileOutputStream(filename));
        pw.println("-----BEGIN PRIVATE KEY-----");
        String key=Base64.getEncoder().encodeToString(privateKey.getEncoded());
        for (int i = 0; i < key.length(); i += 64) {
            int end = Math.min(i + 64, key.length());
            pw.println(key.substring(i,end));
        }
        pw.println("-----END PRIVATE KEY-----");
        pw.close();
    }

    public void importPublicKey(String filename) throws Exception {
        BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String line;
        String key="";

        while ((line=br.readLine())!=null){
            if(line.contains("PUBLIC KEY")) continue;
            key+=line;
        }

        byte[] encoded = Base64.getDecoder().decode(key);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.publicKey= keyFactory.generatePublic(keySpec);
    }

    public void importPrivateKey(String filename) throws Exception {
        BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String line;
        String key="";

        while ((line=br.readLine())!=null){
            if(line.contains("PRIVATE KEY")) continue;
            key+=line;
        }

        byte[] encoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey= keyFactory.generatePrivate(keySpec);
    }
// hàm kí cho web
    public  String sign(String data, String privateKeyBase64) throws Exception {
        // Giải mã khóa riêng từ Base64
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyBase64);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = factory.generatePrivate(spec);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes("UTF-8")); // Đảm bảo dùng UTF-8 cho nhất quán

        byte[] signedBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signedBytes);
    }
    public String signFile(String src) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, IOException {
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initSign(this.privateKey);

        BufferedInputStream bis= new BufferedInputStream(new FileInputStream(src));
        int i;
        byte[] read= new byte[1024];
        while ((i=bis.read(read))!=-1) {
            s.update(read,0,i);
        }
        byte[] sign = s.sign();


        return Base64.getEncoder().encodeToString(sign);
    }

    public boolean verify(String data, String sign) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initVerify(publicKey);
        s.update(data.getBytes());
        return  s.verify(Base64.getDecoder().decode(sign));
    }
    public boolean verifyFile(String src, String sign) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, IOException {
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initVerify(publicKey);
        BufferedInputStream bis= new BufferedInputStream(new FileInputStream(src));
        int i;
        byte[] read= new byte[1024];
        while ((i=bis.read(read))!=-1) {
            s.update(read,0,i);
        }
        return  s.verify(Base64.getDecoder().decode(sign));
    }

    public static void main(String[] args) throws Exception {
        String s = "CNTT";
        DS ds =  new DS();
//        ds.genKey();
//        ds.exportPublicKey("public.pem");
//        ds.exportPrivateKey("priv.pem");

//        String sign = ds.sign(s);
        String privatekey= "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCM/ZS2U74SPcw7sZKhizMSUkiZbKmkuvZklds/3iPHmMrULjeAoNefsMBNfEMx+oszaAOd8EuL2s8fw+kjhedkdjcGTCRFw9jtOPYBvfmPEW0JpyU5VvbjpX3z59Tcy2Fl3Froz82M0t1prUDmSFvOS1ap6g8GeeS44kYhFpAJODBq7WG7ZiWwjZi7sk5of7yMBHTgOdMBVMyJGVxbeDD3Mb8cXJaeHNoCDWYXC0v0TELQdXFqjIwK/9OZZ1rXFdhhZWPmaiNnHqvD5jo5e746UnX33E/iROCZz8Q6wvCcj0/EUeFbjOOxh7C1n6/fmzdeKhcLhZEyDP5exfmbz47dAgMBAAECggEAAozMkhV9lfaEhyaT3IbBT63FF+Bt/8LoVW52202uxnuj7zRIEqHjzsTVuY7CZ6au3w2vFaJyb1g44ku66Njjr2NKZSADWhPQekULOAHrNsH25Dgf3p8WN2hSz8BCxgFl0mRGNaasy7vZCIso+DprTcyxh65Fp/C48jD0EK9aP3xpst5e5HPQDVmbhCc+2oGABRC/DHYH5BHoaDd7ktORg13F6Xvp3dH6237KWQWa3Is9nE6BSd7Zz3VFYXK+rrHpPoVhJQT+zD+QbX2+5gxRL8N7E+4uvB63eKxm7l5FY8Cx/zGZE+fNe9QB8Hb9xi66te076MJgWZEb3/aH3bjUaQKBgQC7YwOAtIzN4FtAx8zARbwUbWb+io6ZDfrfF7rn3vkSdwCBsUYf22GMhDA9iP6IdIi0QA5dTx0yw0SroVHB1pYbbNImMjzn8fzrvmFMRZeCJGaP0iTCfEg4mGIkhqtajkXSFGfFct85G/O37Qd1ugYDGhi1WOaM4gT0knqw3ECRBQKBgQDAnYvXs245347hFj/okzn1lC3i7OVg+VtxvUMCgBdYzBuFRF+QslXVSJ/8i1Bph1jkvFQhGatZzLktgZhJ1j93v/8/MPEJqz98XPOl9jh90TSKG2rYcDuroae2yQlgWZkIGOOIVd0+TmKeIksWeyeO4UtaN5ml2+BjeVF77RRN+QKBgQCuK9sOn0Mc/QN0lAGhWW/3ALUxtLHUW2UqRZFJgju6SPgklBSc+bmS9S8tkw8a4E5MVBnF6wo1q8BBUwF+DSmatiwiqqFn6cnviRZA4ZJwucrGtUnLp98tE55tWusckY8eNC88HIiVOYDwHRXI/C4oZfI/ZTFuE4sNlLULfL1azQKBgCObPCW0urSiGhSL+2ZwCO/X45+mcUS68+eTuBZ3PVmh6Zh6KjxiazGRSlnyGvxXE9y838R6rU1dZgH+iftkSrlFWdcBoh1KdeL0f+n3GTxQ9yhspxS5sp4otQs6IKyaA4sWpsEmNk71SI+5AS53t7lW9ooH6zRY8CMTYzfIfM9pAoGAaEfznxmHT6EXnyNq03zj3eJcS04OWzfGr0r8x99Fh+3UifBNrxv8TEhqJRY24etow0+If5yJDgkMELJwEN0eaz1Rea0x2B4rNLrxGy+TGKjinSF2nkWSTgg3d9iXvRm7hfVwxa/dqhli7zw3/n3qaphgtBTBQB28oSNN6Ave87o=";
        String data= "{\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"productId\": 1,\n" +
                "      \"productName\": \"Áo Hoodie\",\n" +
                "      \"quantity\": 2,\n" +
                "      \"price\": 250000.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"productId\": 3,\n" +
                "      \"productName\": \"Mũ Mixi\",\n" +
                "      \"quantity\": 1,\n" +
                "      \"price\": 120000.0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 620000.0,\n" +
                "  \"address\": \"123 Đường ABC\",\n" +
                "  \"phone\": \"0901234567\",\n" +
                "  \"userId\": 5,\n" +
                "  \"username\": \"du123\"\n" +
                "}";
        String data1= "{\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"productId\": 1,\n" +
                "      \"productName\": \"Áo Hoodie\",\n" +
                "      \"quantity\": 2,\n" +
                "      \"price\": 250000.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"productId\": 3,\n" +
                "      \"productName\": \"Mũ Mixi\",\n" +
                "      \"quantity\": 1,\n" +
                "      \"price\": 120000.0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": 620000.0,\n" +
                "  \"address\": \"123 Đường ABC\",\n" +
                "  \"phone\": \"0901234567\",\n" +
                "  \"userId\": 5,\n" +
                "  \"username\": \"du12ss3\"\n" +
                "}";
        String sign = ds.sign(data,privatekey);
        System.out.println(sign);
        String sign1 = ds.sign(data1,privatekey);
        System.out.println(sign1);
//
//        System.out.println(ds.verifyFile(f1,sign));

//        System.out.println(sign);
//        System.out.println(ds.verify("CNTt",sign));
    }
}

