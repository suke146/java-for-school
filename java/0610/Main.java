import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class Main {
    public static void main(String[] args) {
        try {
            // プロパティファイルの候補パス
            Path[] candidates = new Path[] {
                Paths.get("mail.properties"),
                Paths.get("src", "main", "resources", "mail.properties"),
                Paths.get("0610", "src", "main", "resources", "mail.properties")
            };

            Path propsPath = null;
            for (Path p : candidates) {
                if (Files.exists(p)) {
                    propsPath = p;
                    break;
                }
            }

            Properties config = new Properties();
            if (propsPath != null) {
                try (FileInputStream in = new FileInputStream(propsPath.toFile())) {
                    config.load(in);
                }
            } else {
                System.err.println("mail.properties が見つかりません。カレントディレクトリか src/main/resources に配置してください。");
                System.exit(1);
            }

            final String username = config.getProperty("mail.username");
            final String password = config.getProperty("mail.password");
            final String smtpServer = config.getProperty("mail.smtp.host", "localhost");
            final String smtpPort = config.getProperty("mail.smtp.port", "25");

            final String toEmail = config.getProperty("mail.to");
            final String fromEmail = config.getProperty("mail.from");
            final String fromName = config.getProperty("mail.from.name", "");
            final String subject = config.getProperty("mail.subject", "テストメール");
            final String body = config.getProperty("mail.body", "テスト送信です。\n");

            Properties props = new Properties();
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", smtpPort);
            props.put("mail.smtp.auth", config.getProperty("mail.smtp.auth", "false"));
            if (Boolean.parseBoolean(config.getProperty("mail.smtp.ssl.enable", "false"))) {
                props.put("mail.smtp.ssl.enable", "true");
                props.put("mail.smtp.socketFactory.port", smtpPort);
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            }
            if (Boolean.parseBoolean(config.getProperty("mail.smtp.starttls.enable", "false"))) {
                props.put("mail.smtp.starttls.enable", "true");
            }

            Session session;
            if (username != null && password != null && !username.isEmpty()) {
                session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
            } else {
                session = Session.getInstance(props);
            }

            // メール送信
            MimeMessage message = new MimeMessage(session);
            if (fromName == null || fromName.isEmpty()) {
                message.setFrom(new InternetAddress(fromEmail));
            } else {
                message.setFrom(new InternetAddress(fromEmail, fromName, "UTF-8"));
            }
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");

            Transport.send(message);
            System.out.println("メールが正常に送信されました。");

        } catch (Exception e) {
            System.err.println("メール送信中にエラーが発生しました。");
            e.printStackTrace();
            System.exit(2);
        }
    }
}