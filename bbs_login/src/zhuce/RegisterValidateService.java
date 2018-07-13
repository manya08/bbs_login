//package zhuce;
//
//
//        import java.text.ParseException;
//        import java.util.Date;
//
//        import dao.Main;
//        import dao.UUIDtool;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.stereotype.Service;
//        import com.dao.UserDao;
//        import com.tools.MD5Tool;
//        import com.tools.MD5Util;
//        import com.tools.SendEmail;
//        import com.tools.ServiceException;
//        import com.code.model.UserModel;
//
//
//public class RegisterValidateService {
//
//
//
//    /**
//     * 处理注册
//     */
//    public void processregister(String email){
//        UserModel user=new UserModel();
//        Long as=5480l;
//        user.setId(as);
//        user.setName("xiaoming");
//        user.setPassword("324545");
//        user.setEmail(email);
//        user.setRegisterTime(new Date());
//        user.setStatus(0);
//        ///如果处于安全，可以将激活码处理的更复杂点，这里我稍做简单处理
//        //user.setValidateCode(MD5Tool.MD5Encrypt(email));
//        user.setValidateCode(UUIDtool.getUUID());
//
//
//        ///邮件的内容
//        StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
//        sb.append("<a href=\"http://localhost:8080/springmvc/user/register?action=activate&email=");
//        sb.append(email);
//        sb.append("&validateCode=");
//        sb.append(user.getValidateCode());
//        sb.append("\">http://localhost:8080/springmvc/user/register?action=activate&email=");
//        sb.append(email);
//        sb.append("&validateCode=");
//        sb.append(user.getValidateCode());
//        sb.append("</a>");
//
//        //发送邮件
//        Main.SendMail();
//        System.out.println("发送邮件");
//
//    }
//
//    /**
//     * 处理激活
//     * @throws ParseException
//     */
//    ///传递激活码和email过来
//    public void processActivate(String email , String validateCode)throws ServiceException, ParseException{
//        //数据访问层，通过email获取用户信息
//        UserModel user=userDao.find(email);
//        //验证用户是否存在
//        if(user!=null) {
//            //验证用户激活状态
//            if(user.getStatus()==0) {
//                ///没激活
//                Date currentTime = new Date();//获取当前时间
//                //验证链接是否过期
//                currentTime.before(user.getRegisterTime());
//                if(currentTime.before(user.getLastActivateTime())) {
//                    //验证激活码是否正确
//                    if(validateCode.equals(user.getValidateCode())) {
//                        //激活成功， //并更新用户的激活状态，为已激活
//                        System.out.println("==sq==="+user.getStatus());
//                        user.setStatus(1);//把状态改为激活
//                        System.out.println("==sh==="+user.getStatus());
//                        userDao.update(user);
//                    } else {
//                        throw new ServiceException("激活码不正确");
//                    }
//                } else { throw new ServiceException("激活码已过期！");
//                }
//            } else {
//                throw new ServiceException("邮箱已激活，请登录！");
//            }
//        } else {
//            throw new ServiceException("该邮箱未注册（邮箱地址不存在）！");
//        }
//
//    }
//}