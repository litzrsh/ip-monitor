package me.litzrsh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.litzrsh.config.ApplicationConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderService {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final JavaMailSender mailSender;

    private final MyIpAddressExtractorService service;

    private final ApplicationConfigurationProperties properties;

    @Scheduled(cron = "0 0 6,18 * * *")
    public void getIpAddressAndSendMail() throws Exception {
        String ipAddress = service.getMyIpAddress();
        log.info("My IP Address: " + ipAddress);

        if (!StringUtils.hasText(properties.getFrom())) {
            throw new Exception("No sender in properties");
        }

        Set<String> to = new HashSet<>();
        to.addAll(Arrays.asList(properties.getTo()));

        if (CollectionUtils.isEmpty(to)) {
            throw new Exception("No receiver in properties");
        }

        log.info("Send email to: " + to);

        Set<String> cc = new HashSet<>();
        cc.addAll(Arrays.asList(properties.getCc()));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(properties.getFrom());
        message.setTo(to.toArray(new String[]{}));
        if (!CollectionUtils.isEmpty(cc)) {
            message.setCc(cc.toArray(new String[]{}));
        }

        log.info("Write E-mail message...");
        message.setSubject(String.format("[%s] Remote IP Address Alert", DATE_FORMAT.format(new Date())));
        message.setText(String.format("Remote IP Address is %s.", ipAddress));

        log.info("Prepare to send mail...");
        mailSender.send(message);
    }
}
