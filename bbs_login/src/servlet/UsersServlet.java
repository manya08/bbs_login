package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.UserDAO;
import modle.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static dao.UserDAO.phonedic;
import static dao.UserDAO.phonedicps;


/**
 * @author xyz
 * @date Feb 19, 2012 http://localhost:8080/JSONDemo2ServerV2/user
 */
public class UsersServlet extends HttpServlet {
    private static final long serialVersionUID = -6534417657358062448L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int method = Integer.parseInt(request.getParameter("method"));
        response.setContentType("text/json;charset=UTF-8");
        switch (method) {
            case 1:
                getUserById(request,response);
                    break;
//                try {
//                    testphone(request,response);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//                getUsers(response);
            case 2:
                Sendverification(request, response);
                break;
            case 3:
                insertUsersphone(request, response);
//                getUserById(request, response);
                break;
            case 4:
                signuser(request,response);
                break;
            case 5:
                Sendverificationpswd(request, response);
                break;
            case 6:
                updateps(request, response);
                break;
            default:
                break;
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    private void getUserById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String phone = request.getParameter("phone");
        JSONObject jObject = new JSONObject();
        UserDAO userDAO;
        String result ="false";
        try {
            userDAO = new UserDAO();
            if(userDAO.getUsersbyId(phone)){
                result ="true";
            }
            JSONObject retObject = new JSONObject();
            retObject.put("totalNum", 1);
            JSONArray userlist = new JSONArray();
            JSONObject userObject = new JSONObject();
            userlist.put(userObject);
            retObject.put("info", phone);
            jObject.put("checkphone",result);
            jObject.put("retCode", 0);
            jObject.put("msg", "ok");
            jObject.put("data", retObject);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                jObject.put("ret", 1);
                jObject.put("checkphone",result);
                jObject.put("msg", e.getMessage());
                jObject.put("data", "");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        PrintWriter out = response.getWriter();
        out.println(jObject);
        out.flush();
        out.close();
    }
    private void updateps(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String inputString = streamToString(request.getInputStream());
        User user = parseJson(inputString);
        JSONObject retJSON = new JSONObject();
        phonedicps.put("18757708958","123456");
        phonedicps.put("123","123");
        boolean result = false;
        if (user!= null) {
            try {
                UserDAO dao;
                dao = new UserDAO();
                if(user.getPhone()!=null&&phonedicps.get(user.getPhone())!=null){
                    if (phonedicps.get(user.getPhone()).equals(user.getCode())) {
                        if (dao.getUsersbyIdupdate(user)) {
                            result = true;
                        }
                    }
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
        out.println(retJSON);
        out.flush();
        out.close();
    }
    private void insertUsersphone(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String inputString = streamToString(request.getInputStream());
        User user = parseJson(inputString);
        JSONObject retJSON = new JSONObject();
        phonedic.put("18757708958","123456");
        phonedic.put("123","123");
        boolean result = false;
        if (user!= null) {
            try {
                UserDAO dao;
                dao = new UserDAO();
                if(user.getPhone()!=null){
                    if (phonedic.get(user.getPhone()).equals(user.getCode())) {
                        if (dao.insertphone(user)) {
                            result = true;
                        }
                    }
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
        out.println(retJSON);
        out.flush();
        out.close();
    }
    private void Sendverification(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String inputString = streamToString(request.getInputStream());
        User user = parseJson(inputString);
        JSONObject retJSON = new JSONObject();
        boolean result = false;
        if (user!= null) {
            try {
                UserDAO dao;
                dao = new UserDAO();
                if(user.getPhone()!=null&&!dao.activationephonecode(user.getPhone())){
                    result=true;
                    dao.SendMsg(user.getPhone());
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
        out.println(retJSON);
        out.flush();
        out.close();
    }
    private void Sendverificationpswd(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String inputString = streamToString(request.getInputStream());
        User user = parseJson(inputString);
        JSONObject retJSON = new JSONObject();
        boolean result = false;
        if (user!= null) {
            try {
                UserDAO dao;
                dao = new UserDAO();
                if(user.getPhone()!=null&&dao.updatephonecode(user.getPhone())){
                    result=true;
                    dao.SendMsgpswd(user.getPhone());
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
        out.println(retJSON);
        out.flush();
        out.close();
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
        User user = new User();
        JSONObject reqObject;
        try {
            reqObject = new JSONObject(jsonString);
            if (reqObject.getInt("ReqCode") == 1) {
                JSONArray userArray = reqObject.getJSONArray("info");
//            JSONArray userArray = new JSONArray(jsonString);
                if (userArray.length() > 0) {
                        String verification = null;
                        String UserName = null;
                        String Password = null;
                        String phone = null;
                        JSONObject userObject = userArray.getJSONObject(0);
                        if(!userObject.isNull("UserName") ){
                            UserName = userObject.getString("UserName");}
                        if(!userObject.isNull("Password") ){
                             Password = userObject.getString("Password");}
                        if(!userObject.isNull("verification") ){
                             verification =userObject.getString("verification");}
                        if(!userObject.isNull("phone") ){
                             phone =userObject.getString("phone");}
                        user.setUsername(UserName);
                        user.setUserpass(Password);
                        user.setPhone(phone);
                        user.setCode(verification);
                }
            }
        } catch (
                JSONException e)

        {
            e.printStackTrace();
        }
        return user;
    }

//    private void getUserById(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String id = request.getParameter("id");
//        JSONObject jObject = new JSONObject();
//        UserDAO userDAO;
//        try {
//            userDAO = new UserDAO();
//            dao.User user = userDAO.getUserById(id);
//            JSONObject retObject = new JSONObject();
//            retObject.put("totalNum", 1);
//            JSONArray userlist = new JSONArray();
//            JSONObject userObject = new JSONObject();
//            userObject.put("_id", user.getUID());
//            userObject.put("uname", user.getUsername());
//            userObject.put("password", user.getPassword());
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
//        out.flush();
//        out.close();
//    }

//    private void getUserById1(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        String inputString = streamToString(request.getInputStream());
//
//        System.out.println("inputString:" + inputString);
//
//        JSONObject retJSON = new JSONObject();
//        JSONObject reqObject;
//        try {
//            reqObject = new JSONObject(inputString);
//            if (reqObject.getInt("reqCode") == 3) {
//                JSONObject infoObject = reqObject.getJSONObject("info");
//                int id = infoObject.getInt("id");
//
//                UserDAO userDAO;
//                try {
//                    userDAO = new UserDAO();
//                    User user = userDAO.getUserById(String.valueOf(id));
//                    JSONObject retObject = new JSONObject();
//                    retObject.put("totalNum", 1);
//                    JSONArray userlist = new JSONArray();
//
//                    JSONObject userObject = new JSONObject();
//                    userObject.put("_id", user.getUID());
//                    userObject.put("uname", user.getUsername());
//                    userObject.put("password", user.getPassword());
//                    userlist.put(userObject);
//
//                    retObject.put("info", userlist);
//                    retJSON.put("retCode", 0);
//                    retJSON.put("msg", "ok");
//                    retJSON.put("data", retObject);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    try {
//                        retJSON.put("ret", 1);
//                        retJSON.put("msg", e.getMessage());
//                        retJSON.put("data", "");
//                    } catch (JSONException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        PrintWriter out = response.getWriter();
//        out.println(retJSON);
//        out.flush();
//        out.close();
//    }

//    private void getUsers(HttpServletRequest request,HttpServletResponse response) throws IOException {
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
//            userObject.put("Information", users.getCollect());
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

//    private void insertUsersmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        String inputString = streamToString(request.getInputStream());
//        List<User> users = parseJson(inputString);
//        JSONObject retJSON = new JSONObject();
//        boolean result = true;
//        if (users.size() != 0) {
//            try {
//                UserDAO dao;
//                dao = new UserDAO();
//                for (int i = 0; i < users.size(); i++) {
//                    User user = users.get(i);
//                    if(user.getEmail()!=null){
//                        if (!dao.insertmail(user)) {
//                            result = true;
//                            break;
//                        }
//                    }
//                }
//                if (result) {
//                    retJSON.put("ret", 0);
//                    retJSON.put("msg", "ok");
//                } else {
//                    retJSON.put("ret", 1);
//                    retJSON.put("msg", "fail");
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        PrintWriter out = response.getWriter();
//        out.println(retJSON);
//        out.flush();
//        out.close();
//    }
        private void signuser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String inputString = streamToString(request.getInputStream());
        User user = parseJson(inputString);
        JSONObject retJSON = new JSONObject();
        boolean result = false;
        UserDAO dao;
        try{
        dao =new UserDAO();
        if(dao.validate(user)){
            result=true;
        }
        if (result) {
            retJSON.put("ret", 0);
            retJSON.put("msg", "ok");
        } else {
            retJSON.put("ret", 1);
            retJSON.put("msg", "fail");
        }
        }catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.println(retJSON);
        out.flush();
        out.close();
    }
}
