package dao;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @ClassName: Sendmail
 * @Description: 发送Email
 * @author: 孤傲苍狼
 * @date: 2015-1-12 下午9:42:56
 *
 */
public class SendEmail {


    /**
     * @param args
     *
     * @throws Exception
     */
    static String in =Thread.currentThread().getContextClassLoader().getResource("").getPath();
    static String mail = Ceshi.GetValueByKey(in+"mail.properties","mail");
    static String mailFrom ;
    static String mailHost = Ceshi.GetValueByKey(in+"mail.properties","mailHost");
    static String mailPassword = Ceshi.GetValueByKey(in+"mail.properties","mailPassword");
    static String mailUsername = Ceshi.GetValueByKey(in+"mail.properties","mailUsername");
    public static String mailcode = Ceshi.GetValueByKey(in+"mail.properties","mailcode");
    public static void SendMail(String email,String code) throws Exception {
        mailFrom =email;
        mailcode =code;
        Properties prop = new Properties();// 用于连接邮件服务器的参数配置
        // 开启debug调试
        prop.setProperty("mail.debug", "true");
        // 发件人的邮箱的 SMTP 服务器地址
        prop.setProperty("mail.host", mailHost);
        //  使用的协议
        prop.setProperty("mail.transport.protocol", "smtp");
        // 需要请求认证
        prop.setProperty("mail.smtp.auth", "true");
//        final String smtpPort = "465";端口
        //SSL加密
        MailSSLSocketFactory sf =new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.socketFactory",sf);
        //使用JavaMail发送邮件的5个步骤
        //1、创建session// 根据参数配置，创建会话对象（为了发送邮件准备的）
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(mailHost, mailUsername, mailPassword);
        //4、创建邮件
        Message message = createSimpleMail(session);
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());// 发送邮件给所有收件人
        ts.close();//关闭连接
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     * @Anthor:孤傲苍狼
     *
     * @param session
     * @return
     * @throws Exception
     */
    public static MimeMessage createSimpleMail(Session session)
            throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(mailFrom,"测试邮件", "UTF-8"));
        //指明邮件的收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mailFrom));
        //邮件的标题
        message.setSubject("只包含文本的简单邮件");
        //邮件的文本内容
        message.setContent("<h1>来自xx网站的激活邮件，请点击以下链接：</h1>"+"http://localhost:8080/user?method=4&email="+mailFrom+"&code="+mailcode, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}