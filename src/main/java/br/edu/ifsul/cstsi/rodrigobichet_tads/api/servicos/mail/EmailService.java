package br.edu.ifsul.cstsi.rodrigobichet_tads.api.servicos.mail;

public interface EmailService {

    void enviarEmail(String to, String subject, String message);

}