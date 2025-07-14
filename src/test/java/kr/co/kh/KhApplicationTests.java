package kr.co.kh;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KhApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void jasypt() {
        String url = "jdbc:log4jdbc:oracle:thin:@localhost:1521:xe";
        String username = "scott";
        String password = "tiger";
        System.out.println(jasyptEncoding(url));
        System.out.println(jasyptEncoding(username));
        System.out.println(jasyptEncoding(password));
    }

    public String jasyptEncoding(String value) {
        String key = "jasyptStringEncryptorKey";
        StandardPBEStringEncryptor pbeStringEncryptor = new StandardPBEStringEncryptor();
        pbeStringEncryptor.setAlgorithm("PBEWithMD5AndDES");
        pbeStringEncryptor.setPassword(key);
        return pbeStringEncryptor.encrypt(value);
    }

}
