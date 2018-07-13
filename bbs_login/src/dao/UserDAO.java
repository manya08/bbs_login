package dao;



import modle.User;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author zzz
 * @time Dec 4, 2011 2:52:09 PM
 */
public class UserDAO {
    SqlManager manager;
    String sql = "";
    ResultSet rs;
    Map<String, String> maildic = new HashMap<String, String>();
        public static Map<String,String> phonedic = new HashMap<String, String>();
        public static Map<String,String> phonedicps = new HashMap<String, String>();
    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    public UserDAO() throws IOException, ClassNotFoundException, SQLException {
        manager = SqlManager.createInstance();
    }

public boolean validate(User user) throws SQLException {
        boolean result = false;
        String phone =user.getPhone();
        String password =user.getUserpass();
        sql = "select * from user where PhoneNumber=? and Password=?";
        Object[] params = new Object[]{phone, password};
        manager.connectDB();
        rs = manager.executeQuery(sql, params);
        if (rs.next() && rs.getString(1).equals(phone)) {
            result = true;
        }
        manager.closeDB();
        return result;
    }

    public boolean codetest(String mail, String code) {
        if (maildic.get(mail).equals(code)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean phonetest(String phone, Integer msg) {
        if (phonedic.get(phone).equals(msg)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean getUsersbyId(String phone) throws SQLException {
        sql = "select * from user where PhoneNumber="+phone;
        boolean result = false;
        manager.connectDB();
        rs = manager.executeQuery(sql, null);
        while (rs.next() && (rs.getString(1).equals(phone))){
            result = true;
        }
        manager.closeDB();
        return result;
    }
    public Boolean getUsersbyIdupdate(User user) throws SQLException {
        User list = new User();
        String phone =user.getPhone();
        String userpass =user.getUserpass();
        boolean result =false;
        Object[] params = new Object[]{userpass,phone};
        sql = "update user Set Password = ? where PhoneNumber = ?";
        manager.connectDB();
        result = manager.executeUpdate(sql, params);
        manager.closeDB();

//        while (rs.next()) {
//            list = new User(rs.getString("UserName"), rs.getString("Sex"), rs.getString("HeadImageAddress"),rs.getString("SumPost"),rs.getString("Attention"),
//                    rs.getString("Collect"),rs.getString("Information"));
//        }
        return result;
    }

//    public User getUserById(String id) throws SQLException {
//        sql = "select * from user where _id=" + id;
//        manager.connectDB();
//        rs = manager.executeQuery(sql, null);
//        if (rs.next()) {
//            User user = new User(rs.getInt("_id"),
//                    rs.getString("uname"), rs.getString("password"));
//            return user;
//
//        }
//        manager.closeDB();
//        return null;
//    }

    /**
     * 更新用户信息， 注意：只能根据用户名更新密码
     */
//    public boolean insertmail(User user) throws Exception {
//        String uname = user.getUsername();
//        String password = user.getUserpass();
//        String email = user.getEmail();
//        String code = UUIDtool.getUUID();
//        String nickname =user.getNickname();
////        String code = user.setCode("asdad");
//        boolean result = false;
//        sql = "insert into user(nickname,username,userpass,email) values(?,?,?,?,?)";
//        Object[] params = new Object[]{nickname,uname, password, email,code};
//        manager.connectDB();
//        result = manager.executeUpdate(sql, params);
//        manager.closeDB();
//        if (result && email != null) {
//            maildic.put(email, code);
//            SendEmail.SendMail(email, code);
//        }
//        return result;
//    }
//    public boolean getUsersbyId(String phone) throws SQLException {
//        sql = "select * from user where PhoneNumber="+phone;
//        boolean result = false;
//        manager.connectDB();
//        rs = manager.executeQuery(sql, null);
//        if (rs.next() && rs.getLong(1) == 1) {
//            result = true;
//        }
//        manager.closeDB();
//        return result;
//    }
    public boolean insertphone(User user) throws Exception {
        String uname = user.getUsername();
        String password = user.getUserpass();
        String phone = user.getPhone();
//        String code = user.setCode("asdad");
        boolean result = false;
        sql = "insert into user(UserName,Password,PhoneNumber) values(?,?,?)";
        Object[] params = new Object[]{phone, password,phone};
        manager.connectDB();
        result = manager.executeUpdate(sql, params);
        manager.closeDB();
//        if (result && email != null) {
//            maildic.put(email, a);
//            SendEmail.SendMail(email, a);
//        }
        return result;
    }

    public Boolean updateinfo(User user) throws SQLException {
        String UserName = user.getUsername();
        String Sex = user.getSex();
        String HeadImageAddress = user.getHeadImageAddress();
        String SumPost = user.getSumPost();
        String Attention = user.getAttention();
        String Collect = user.getCollect();
        int id = user.getId();
//        String code = user.setCode("asdad");
        boolean result = false;
        String sql1="UPDATE user SET ";
        String sql2="";
        String sql3="WHERE id="+id;
        if(!(UserName ==null)){ sql1+="UserName="+UserName+",";}
        if(!(Sex ==null)){ sql1+="Sex="+Sex+",";}
        if(!(HeadImageAddress ==null)){ sql1+="HeadImageAddress="+HeadImageAddress+",";}
        if(!(SumPost ==null)){ sql1+="SumPost="+SumPost+",";}
        if(!(Attention ==null)){ sql1+="Attention="+Attention+",";}
        if(!(Collect ==null)){ sql1+="Collect="+Collect+",";}
        sql=sql1+sql3;
        Object[] params = new Object[]{UserName,Sex,HeadImageAddress,SumPost,Attention,Collect,
                id};
        manager.connectDB();
        result = manager.executeUpdate(sql,null);
        manager.closeDB();
        return result;
    }
    public Boolean activationephonecode(String phone) throws SQLException {
        sql = "select * from user where PhoneNumber=" +phone;
        manager.connectDB();
        rs = manager.executeQuery(sql,null);
        if (rs !=null) {
            manager.closeDB();
            return false;
        }else {
            manager.closeDB();
            return true;
        }
    }
    public Boolean updatephonecode(String phone) throws SQLException {
        sql = "select * from user where PhoneNumber=" +phone;
        manager.connectDB();
        rs = manager.executeQuery(sql,null);
        if (rs !=null) {
            manager.closeDB();
            return true;
        }else {
            manager.closeDB();
            return false;
        }
    }
    public void SendMsg(String phone) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");
        double suij =Math.random() * 9 + 1;
        String mobile_code =String.valueOf ((int)(suij * 1000+suij*100+suij*10+suij));

        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
        phonedic.put(phone,mobile_code);
        NameValuePair[] data = {//提交短信
                new NameValuePair("account", "C54360742"), //查看用户名是登录用户中心->验证码短信->产品总览->APIID
                new NameValuePair("password", "7dec245a14c46704aa128f08f2209443 "),  //查看密码请登录用户中心->验证码短信->产品总览->APIKEY
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);

        try {
            client.executeMethod(method);

            String SubmitResult = method.getResponseBodyAsString();

            //System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");

            System.out.println(code);
            System.out.println(msg);
            System.out.println(smsid);

            if ("2".equals(code)) {
                System.out.println("�����ύ�ɹ�");
            }

        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void SendMsgpswd(String phone) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");

        String mobile_code =String.valueOf ((int)(Math.random() * 9 + 1) * 100000);

        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
        phonedicps.put(phone,mobile_code);
        NameValuePair[] data = {//提交短信
                new NameValuePair("account", "C54360742"), //查看用户名是登录用户中心->验证码短信->产品总览->APIID
                new NameValuePair("password", "7dec245a14c46704aa128f08f2209443 "),  //查看密码请登录用户中心->验证码短信->产品总览->APIKEY
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);

        try {
            client.executeMethod(method);

            String SubmitResult = method.getResponseBodyAsString();

            //System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");

            System.out.println(code);
            System.out.println(msg);
            System.out.println(smsid);

            if ("2".equals(code)) {
                System.out.println("�����ύ�ɹ�");
            }

        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
