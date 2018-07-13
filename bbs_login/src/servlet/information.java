package servlet;

import dao.UserDAO;
import modle.User;
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

public class information extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int method = Integer.parseInt(request.getParameter("method"));
        response.setContentType("text/json;charset=UTF-8");
        switch (method) {
            case 1:
//                getUsersbyid(request,response);
                break;
            case 2:
//                updateinfo(request,response);
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

    private User parseJson(String jsonString) {
        User users = new User();
        JSONObject reqObject;
        try {
            reqObject = new JSONObject(jsonString);
            if (reqObject.getInt("ReqCode") == 1) {
                JSONArray userArray = reqObject.getJSONArray("info");
//            JSONArray userArray = new JSONArray(jsonString);
                if (userArray.length() > 0) {
                    for (int i = 0; i < userArray.length(); i++) {
                        String verification = null;
                        String UserName = null;
                        String Password = null;
                        String phone = null;
                        JSONObject userObject = userArray.getJSONObject(i);
                        if(!userObject.isNull("UserName") ){
                            UserName = userObject.getString("UserName");}
                        if(!userObject.isNull("Password") ){
                            Password = userObject.getString("Password");}
                        if(!userObject.isNull("verification") ){
                            verification =userObject.getString("verification");}
                        if(!userObject.isNull("phone") ){
                            phone =userObject.getString("phone");}
                        User user = new User();
                        users.setUsername(UserName);
                        users.setUserpass(Password);
                        users.setPhone(phone);
                        users.setCode(verification);
                    }
                }
            }
        } catch (
                JSONException e)

        {
            e.printStackTrace();
        }
        return users;
    }
//    private void getUsersbyid(HttpServletRequest request,HttpServletResponse response) throws IOException {
//        String setid=request.getParameter("id");
//        System.out.println(setid);
//        JSONObject jObject = new JSONObject();
//        UserDAO userDAO;
//        try {
//            userDAO = new UserDAO();
//            User users = userDAO.getUsersbyId(setid);
//            JSONObject retObject = new JSONObject();
//            JSONArray userlist = new JSONArray();
//            JSONObject userObject = new JSONObject();
//            userObject.put("UserName", users.getUsername());
//            userObject.put("Sex", users.getSex());
//            userObject.put("HeadImageAddress", users.getHeadImageAddress());
//            userObject.put("SumPost", users.getSumPost());
//            userObject.put("Attention", users.getAttention());
//            userObject.put("Collect", users.getCollect());
//            userlist.put(userObject);
//            retObject.put("info", userlist);
//            jObject.put("retCode", 0);
//            jObject.put("msg", "ok");
//            jObject.put("data", retObject);
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                jObject.put("ret", 1);
//                jObject.put("msg", e.getMessage());
//                jObject.put("data", "");
//            } catch (JSONException ex) {
//                ex.printStackTrace();
//            }
//        }
//        PrintWriter out = response.getWriter();
//        out.println(jObject);
//        //清除缓存区 释放
//        out.flush();
//        out.close();
//    }
    private void updateinfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String inputString = streamToString(request.getInputStream());
        User users = parseJson(inputString);
        JSONObject retJSON = new JSONObject();
        boolean result = false;
        if (users !=null) {
            try {
                UserDAO dao;
                dao = new UserDAO();
                if(dao.updateinfo(users)){
                    result=true;
                }
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
        out.flush();
        out.close();
    }
}
