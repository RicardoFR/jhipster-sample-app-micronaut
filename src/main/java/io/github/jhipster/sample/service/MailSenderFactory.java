package io.github.jhipster.sample.service;

import io.micronaut.context.annotation.Factory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.inject.Singleton;

@Factory
public class MailSenderFactory {

    @Singleton
    JavaMailSender mailSender() {
        return new JavaMailSenderImpl();
    }
}
