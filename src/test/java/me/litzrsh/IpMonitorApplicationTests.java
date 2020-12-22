package me.litzrsh;

import me.litzrsh.service.MailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class IpMonitorApplicationTests {

    @Autowired
    MailSenderService service;

    @Test
    void contextLoads() throws Exception {
        service.getIpAddressAndSendMail();
    }
}
