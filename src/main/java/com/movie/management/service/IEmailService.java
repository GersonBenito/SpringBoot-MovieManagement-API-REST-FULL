package com.movie.management.service;

import java.io.File;

public interface IEmailService {
    public void sendEmail(String toUser, String subject, String message);
    public void sendEmilWithFile(String toUser, String subject, String message, File file);
}
