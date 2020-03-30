package com.coates.oauth;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class OauthApplicationTests {
    public static void main(String[] args) {

        //对原始密码加密
        String hashpw = BCrypt.hashpw("secret", BCrypt.gensalt());
        System.out.println(hashpw);
        //校验原始密码和BCrypt密码是否一致
        boolean checkpw = BCrypt.checkpw("secret",
                "$2a$10$qoPV3mQbycBhwTXDoqMiWOF6E45g3YCQau4uKA4f19RgPcd4fe8I6");
        System.out.println(checkpw);

    }

}
