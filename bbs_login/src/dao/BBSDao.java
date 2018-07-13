package dao;

import modle.BBSmaintext;
import modle.User;
import modle.reply;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//有关发表，读取，回复文章
public class BBSDao {
    SqlManager manager;
    String sql = "";
    String sql2 = "";
    ResultSet rs;
    public BBSDao() throws IOException, ClassNotFoundException, SQLException {
        manager = SqlManager.createInstance();
    }
    //查找所有的文章
    public List<BBSmaintext> getUsersbyId() throws SQLException {
        sql = "select * from post ";
        boolean result = false;
        ArrayList<BBSmaintext> list = new ArrayList<BBSmaintext>();
        BBSmaintext bbsmaintext = new BBSmaintext();
        manager.connectDB();
        int cont =0;
        rs = manager.executeQuery(sql, null);
        while (rs.next()) {
            bbsmaintext = new BBSmaintext(rs.getString("Title"), rs.getString("PostImage"), rs.getString("PostUserName"),rs.getString("PostContent"),rs.getString("PostTime"),
                    rs.getString("ReplyNumber"),rs.getString("PostReadNumber"),rs.getString("PostType"),rs.getInt("PostID"));
            list.add(bbsmaintext);
            cont++;
        }
        manager.closeDB();
        return list;
    }
    //通过手机号查个人文章
    public List<BBSmaintext> getUsersbymyId(String phone) throws SQLException {
        sql = "select * from post where PostUserName="+phone;
        boolean result = false;
        ArrayList<BBSmaintext> list = new ArrayList<BBSmaintext>();
        BBSmaintext bbsmaintext = new BBSmaintext();
        manager.connectDB();
        int cont =0;
        rs = manager.executeQuery(sql, null);
        while (rs.next()) {
            bbsmaintext = new BBSmaintext(rs.getString("Title"), rs.getString("PostImage"), rs.getString("PostUserName"),rs.getString("PostContent"),rs.getString("PostTime"),
                    rs.getString("ReplyNumber"),rs.getString("PostReadNumber"),rs.getString("PostType"),rs.getInt("PostID"));
            list.add(bbsmaintext);
            cont++;
        }
        manager.closeDB();
        return list;
    }
    //通过文章id来查找回复
    public List<reply> getreplybypostid(String PostID,int cont) throws SQLException {
        sql = "select * from reply where PostID="+PostID;
        sql2 = "update post Set PostReadNumber = PostReadNumber+1 where PostID="+PostID;
        boolean result = false;
        ArrayList<reply> list = new ArrayList<reply>();
        reply replycontent = new reply();
        manager.connectDB();
        rs = manager.executeQuery(sql, null);
        while (rs.next()) {
            replycontent = new reply(rs.getInt("ID"), rs.getInt("PostID"), rs.getInt("ReplyID"),rs.getString("ReplyAccount"),rs.getString("ReplyContent"),
                    rs.getString("Replytime"));
            list.add(replycontent);
        }if(cont%2==0){result =manager.executeUpdate(sql2, null);}
        manager.closeDB();
        return list;
    }
    //通过手机号来查找用户名字 和用户ID
    public User getphonebyname(String phone) throws SQLException {
        sql = "select * from user where PhoneNumber="+phone;
        boolean result = false;
        User username = new User();
        manager.connectDB();
        int cont =0;
        rs = manager.executeQuery(sql, null);
        while (rs.next()) {
            username = new User(rs.getString("UserName"),rs.getInt("ID"));
        }
        manager.closeDB();
        return username;
    }
    //
    public boolean insertcontent(BBSmaintext bbSmaintext,String username) throws Exception {
        String Title = bbSmaintext.getTitle();
        String PostImage = bbSmaintext.getPostImage();
        String time = bbSmaintext.getPostTime();
        int UserID = bbSmaintext.getUserID();
        String PostContent = bbSmaintext.getPostContent();
        String PostType = bbSmaintext.getPostType();
//        String code = user.setCode("asdad");
        boolean result = false;
        sql = "insert into post(Title,PostImage,UserID,PostContent,PostType,PostUserName,PostTime) values(?,?,?,?,?,?,?)";
        Object[] params = new Object[]{Title, PostImage,UserID,PostContent,PostType,username,time};
        manager.connectDB();
        result = manager.executeUpdate(sql, params);
        manager.closeDB();
//        if (result && email != null) {
//            maildic.put(email, a);
//            SendEmail.SendMail(email, a);
//        }
        return result;
    }
    public boolean insertreply(reply reply,String username,int userid) throws Exception {
        int PostID = reply.getPostID();
        String ReplyContent = reply.getReplyContent();
        String ReplyTime = reply.getReplytime();
//        String code = user.setCode("asdad");
        boolean result = false;
        boolean result2 = false;
        sql = "insert into reply(PostID,ReplyID,ReplyAccount,ReplyContent,ReplyTime) values(?,?,?,?,?)";
        sql2 = "update post Set ReplyNumber = ReplyNumber+1 where PostID ="+PostID;
        Object[] params = new Object[]{PostID,userid,username,ReplyContent,ReplyTime};
        manager.connectDB();
        result = manager.executeUpdate(sql, params);
        if(result==true){result2 = manager.executeUpdate(sql2, null);}
        manager.closeDB();
//        if (result && email != null) {
//            maildic.put(email, a);
//            SendEmail.SendMail(email, a);
//        }
        return result;
    }
}
