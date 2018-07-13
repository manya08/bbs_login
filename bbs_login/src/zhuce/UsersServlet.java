//package zhuce;
//
//
//import dao.Main;
//import dao.UUIDtool;
//import org.apache.struts2.ServletActionContext;
//
//public class UsersServlet {
//    private User user;
//
//    private User userService;
//
//    public String save() throws Exception {
//        user.setCode(UUIDtool.getUUID());
//        System.out.println(user);
//        userService.save(user);
//        Main.SendMail(user.getEmail(), user.getCode());
//
//        return "ok";
//    }
//    public String active(){
//        String code = ServletActionContext.getRequest().getParameter("code");
//        User findUser = userService.findUser(code);
//        if(findUser!=null){
//            findUser.setCode(null);
//            findUser.setStatus(1);
//            userService.update(findUser);
//        }
//        return "ok";
//    }
//
//
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public User getUserService() {
//        return userService;
//    }
//
//    public void setUserService(User userService) {
//        this.userService = userService;
//    }
//
//}
