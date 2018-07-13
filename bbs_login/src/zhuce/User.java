package zhuce;

public class User {
    private int id;
    private String username;
    private String userpass;
    private String nickname;
    private String email;
    private String phone;
    private int status;
    private String code;
    public User() {
    }
    public User(String username,String userpass,String phone){
        this.username =username;
        this.userpass=userpass;
        this.phone =phone;
    }
    public User(String username, String userpass, String email, String code) {
        this.username = username;
        this.userpass = userpass;
        this.email = email;
        this.code = code;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
