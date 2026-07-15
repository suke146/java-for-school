mport java.util.Properties;
import javax.swing.*;
import java.awt.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class Main extends JFrame {
    private JTextField tfHost, tfPort, tfUser, tfTo, tfSubject, tfFrom, tfName;
    private JPasswordField pfPass;
    private JTextArea taBody;
    private JButton btnSend;

    public Main() {
        super("メール送信アプリケーション");

        // =========================
        // ロリポップの設定情報
        // =========================
        final String username = "test@suke146monmon.kilo.jp";    //自分のメールアドレス
        final String password = "Trustno1-Trustno1";    //パスワード
        final String smtpServer = "smtp.lolipop.jp";    //SMTPサーバーのアドレス
        final String smtpPort = "465";    //SMTPサーバーのポート番号
        
        // =========================
        // メールの内容
        // =========================
        final String toEmail = "2510016@i-seifu.jp";    // 送信先のメールアドレス
        final String fromEmail = "tomogane@i-seifu.jp"; // 送信者のメールアドレス (捏造可能)
        final String fromName = "友金牧人"; // 送信者名 (捏造可能)
        final String subject = "■ 企業説明会のお知らせ"; // 件名
        final String body = """
                    お疲れ様です。
                    友金です。

                    6月1日（木）に企業説明会を開催します。
                    参加希望の方は、以下のURLから申し込みをお願いします。
                    https://example.com/registration
                """; // 本文

        // 起動時のGUI画面の設定
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // レイアウト
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel top = new JPanel(new GridLayout(8, 2, 5, 5));

        top.add(new JLabel("SMTPホスト:"));
        tfHost = new JTextField("smtp.lolipop.jp");
        top.add(tfHost);

        top.add(new JLabel("ポート:"));
        tfPort = new JTextField("465");
        top.add(tfPort);

        top.add(new JLabel("ユーザー名:"));
        tfUser = new JTextField(username);
        top.add(tfUser);

        top.add(new JLabel("パスワード:"));
        pfPass = new JPasswordField(password);
        top.add(pfPass);

        top.add(new JLabel("送信元のメールアドレス(偽装可能)"));
        tfFrom = new JTextField(fromEmail);
        top.add(tfFrom);

        top.add(new JLabel("送信元の名前(偽装可能)"));
        tfName = new JTextField(fromName);
        top.add(tfName);

        top.add(new JLabel("宛先(To):"));
        tfTo = new JTextField(toEmail);
        top.add(tfTo);

        top.add(new JLabel("件名:"));
        tfSubject = new JTextField(subject);
        top.add(tfSubject);

        panel.add(top, BorderLayout.NORTH);

        taBody = new JTextArea(body, 10, 40);
        JScrollPane scroll = new JScrollPane(taBody);
        panel.add(scroll, BorderLayout.CENTER);

        btnSend = new JButton("送信する");
        btnSend.addActionListener(e -> sendMail());
        panel.add(btnSend, BorderLayout.SOUTH);

        add(panel);
    }

    public void sendMail() {
        String toEmail = tfTo.getText();
        String fromEmail = tfFrom.getText();
        String fromName = tfName.getText();
        String subject = tfSubject.getText();
        String body = taBody.getText();

        Properties props = new Properties();
        props.put("mail.smtp.host", tfHost.getText());
        props.put("mail.smtp.port", tfPort.getText());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(tfUser.getText(), new String(pfPass.getPassword()));
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, fromName, "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("メールが送信されました。");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("メールの送信に失敗しました。");
        }
    }
