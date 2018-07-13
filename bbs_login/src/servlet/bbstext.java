package servlet;

import dao.BBSDao;
import dao.UserDAO;
import modle.BBSmaintext;
import modle.User;
import modle.reply;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.UserDAO.phonedic;

public class bbstext extends HttpServlet {
    private static final long serialVersionUID = -6534417657358062448L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int method = Integer.parseInt(request.getParameter("method"));
        response.setContentType("text/json;charset=UTF-8");
        switch (method) {
            case 1:
                getUsersbyid(request,response);
                break;
            case 2:
                try {
                    insertcontent(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                getreplybypostid(request,response);
                break;
            case 4:
                try {
                    insertreply(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            case 5:
                try {
                    getmypost(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    private String streamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
    private BBSmaintext parseJson(String jsonString) {
        BBSmaintext bbSmaintext = new BBSmaintext();
        JSONObject reqObject;
        try {
            reqObject = new JSONObject(jsonString);
            if (reqObject.getInt("ReqCode") == 1) {
                JSONArray textArray = reqObject.getJSONArray("info");
//            JSONArray userArray = new JSONArray(jsonString);
                if (textArray.length() > 0) {
                    String Title = null;
                    String PostImage = null;
                    String PostContent = null;
                    String PostType = null;
                    String PostTime = null;
                    JSONObject textObject = textArray.getJSONObject(0);
                    if(!textObject.isNull("Title") ){
                        Title = textObject.getString("Title");}
                    if(!textObject.isNull("PostImage") ){
                        PostImage = textObject.getString("PostImage");}
                    if(!textObject.isNull("PostContent") ){
                        PostContent =textObject.getString("PostContent");}
                    if(!textObject.isNull("PostType") ){
                        PostType =textObject.getString("PostType");}
                    if(!textObject.isNull("PostTime") ){
                        PostTime =textObject.getString("PostTime");}
                    bbSmaintext.setTitle(Title);
                    bbSmaintext.setPostImage(PostImage);
                    bbSmaintext.setPostContent(PostContent);
                    bbSmaintext.setPostType(PostType);
                    bbSmaintext.setPostTime(PostTime);
                }
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return bbSmaintext;
    }
    private reply parseJsonreply(String jsonString) {
        reply reply = new reply();
        JSONObject reqObject;
        try {
            reqObject = new JSONObject(jsonString);
            if (reqObject.getInt("ReqCode") == 1) {
                JSONArray textArray = reqObject.getJSONArray("info");
//            JSONArray userArray = new JSONArray(jsonString);
                if (textArray.length() > 0) {
                    int PostID = 0;
                    String ReplyContent = null;
                    String ReplyTime = null;
                    JSONObject textObject = textArray.getJSONObject(0);
                    if(!textObject.isNull("PostID") ){
                        PostID = textObject.getInt("PostID");}
                    if(!textObject.isNull("ReplyContent") ){
                        ReplyContent =textObject.getString("ReplyContent");}
                    if(!textObject.isNull("ReplyTime") ){
                        ReplyTime =textObject.getString("ReplyTime");}
                    reply.setPostID(PostID);
                    reply.setReplyContent(ReplyContent);
                    reply.setReplytime(ReplyTime);
                }
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return reply;
    }
    private void insertcontent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException {
        String phone = request.getParameter("phone");
        BBSDao bbsdao =new BBSDao();
        User bbsusername = bbsdao.getphonebyname(phone);
        String username =bbsusername.getUsername();
        String inputString = streamToString(request.getInputStream());
        BBSmaintext bbSmaintext = parseJson(inputString);
        JSONObject retJSON = new JSONObject();
        boolean result = false;
        if (bbSmaintext!= null) {
            try {
                BBSDao dao;
                dao = new BBSDao();
//                if(bbSmaintext.getUserID()!=0){
                    if (dao.insertcontent(bbSmaintext,username)) {
                        result = true;
                    }
//                }
                if (result) {
                    retJSON.put("ret", 0);
                    retJSON.put("msg", "ok");
                } else {
                    retJSON.put("ret", 1);
                    retJSON.put("msg", "fail");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        PrintWriter out = response.getWriter();
        out.println(retJSON);
        out.flush();
        out.close();
    }
    private void insertreply(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException {
        String phone = request.getParameter("phone");
        BBSDao bbsdao =new BBSDao();
        User bbsuser = bbsdao.getphonebyname(phone);
        String username =phone;
        int userid =bbsuser.getId();
        String inputString = streamToString(request.getInputStream());
        reply replycont = parseJsonreply(inputString);
        JSONObject retJSON = new JSONObject();
        boolean result = false;
        System.out.println(username+phone+userid);
        if (replycont!= null) {
            try {
                BBSDao dao;
                dao = new BBSDao();
//                if(bbSmaintext.getUserID()!=0){
                if (dao.insertreply(replycont,username,userid)) {
                    result = true;
                }
//                }
                if (result) {
                    retJSON.put("ret", 0);
                    retJSON.put("msg", "ok");
                } else {
                    retJSON.put("ret", 1);
                    retJSON.put("msg", "fail");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        PrintWriter out = response.getWriter();
        out.println(retJSON);
        out.flush();
        out.close();
    }
        private void getUsersbyid(HttpServletRequest request,HttpServletResponse response) throws IOException {

        JSONObject jObject = new JSONObject();
        BBSDao bbsdao;
        try {
            bbsdao = new BBSDao();
            List<BBSmaintext> bbsdaos = bbsdao.getUsersbyId();
            JSONArray userlist = new JSONArray();
            JSONObject retObject = new JSONObject();
            for (int i = 0; i < bbsdaos.size(); i++) {
                JSONObject userObject = new JSONObject();
                BBSmaintext bbsmaintext = bbsdaos.get(i);
                userObject.put("Title", bbsmaintext.getTitle());
                userObject.put("PostID", bbsmaintext.getPostID());
                userObject.put("PostImage", bbsmaintext.getPostImage());
                userObject.put("PostUserName", bbsmaintext.getPostUserName());
                userObject.put("PostContent", bbsmaintext.getPostContent());
                userObject.put("PostTime", bbsmaintext.getPostTime());
                userObject.put("ReplyNumber", bbsmaintext.getRepleyNumber());
                userObject.put("PostReadNumber", bbsmaintext.getPostReadNumber());
                userObject.put("PostType", bbsmaintext.getPostType());
                userlist.put(userObject);
            }
//            System.out.println(userlist);
//            retObject.put("info", userlist);
            jObject.put("retCode", 0);
            jObject.put("msg", "ok");
            jObject.put("info", userlist);
            jObject.put("num", bbsdaos.size());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                jObject.put("ret", 1);
                jObject.put("msg", e.getMessage());
                jObject.put("data", "");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        PrintWriter out = response.getWriter();
        out.println(jObject);
        //清除缓存区 释放
        out.flush();
        out.close();
    }
    private void getmypost(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException {
        String phone = request.getParameter("phone");
        BBSDao bbsdao =new BBSDao();
        JSONObject jObject = new JSONObject();
        try {
            bbsdao = new BBSDao();
            List<BBSmaintext> bbsdaos = bbsdao.getUsersbymyId(phone);
            JSONArray userlist = new JSONArray();
            JSONObject retObject = new JSONObject();
            for (int i = 0; i < bbsdaos.size(); i++) {
                JSONObject userObject = new JSONObject();
                BBSmaintext bbsmaintext = bbsdaos.get(i);
                userObject.put("Title", bbsmaintext.getTitle());
                userObject.put("PostID", bbsmaintext.getPostID());
                userObject.put("PostImage", bbsmaintext.getPostImage());
                userObject.put("PostUserName", bbsmaintext.getPostUserName());
                userObject.put("PostContent", bbsmaintext.getPostContent());
                userObject.put("PostTime", bbsmaintext.getPostTime());
                userObject.put("ReplyNumber", bbsmaintext.getRepleyNumber());
                userObject.put("PostReadNumber", bbsmaintext.getPostReadNumber());
                userObject.put("PostType", bbsmaintext.getPostType());
                userlist.put(userObject);
            }
//            System.out.println(userlist);
//            retObject.put("info", userlist);
            jObject.put("retCode", 0);
            jObject.put("msg", "ok");
            jObject.put("info", userlist);
            jObject.put("num", bbsdaos.size());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                jObject.put("ret", 1);
                jObject.put("msg", e.getMessage());
                jObject.put("data", "");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        PrintWriter out = response.getWriter();
        out.println(jObject);
        //清除缓存区 释放
        out.flush();
        out.close();
    }
    private void getreplybypostid(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String PostID = request.getParameter("PostID");
        int cont = Integer.parseInt(request.getParameter("cont"));
        JSONObject jObject = new JSONObject();
        BBSDao bbsdao;
        try {
            bbsdao = new BBSDao();
            List<reply> replyconts = bbsdao.getreplybypostid(PostID,cont);
            JSONArray userlist = new JSONArray();
            JSONObject retObject = new JSONObject();
            for (int i = 0; i < replyconts.size(); i++) {
                JSONObject userObject = new JSONObject();
                reply replycont = replyconts.get(i);
                userObject.put("ID", replycont.getID());
                userObject.put("PostID", replycont.getPostID());
                userObject.put("ReplyID", replycont.getReplyID());
                userObject.put("ReplyAccount", replycont.getReplyAccount());
                userObject.put("ReplyContent", replycont.getReplyContent());
                userObject.put("Replytime", replycont.getReplytime());
                userlist.put(userObject);
                System.out.println(userlist);
            }
//            System.out.println(userlist);
//            retObject.put("info", userlist);
            jObject.put("retCode", 0);
            jObject.put("msg", "ok");
            jObject.put("info", userlist);
            jObject.put("num", replyconts.size());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                jObject.put("ret", 1);
                jObject.put("msg", e.getMessage());
                jObject.put("data", "");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        PrintWriter out = response.getWriter();
        out.println(jObject);
        //清除缓存区 释放
        out.flush();
        out.close();
    }
}
