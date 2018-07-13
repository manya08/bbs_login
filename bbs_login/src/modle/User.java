package modle;

public class User {
    int id;
    private String username;
    private String userpass;
    private String realname;
    private String sex;
    private String code;
    private String phone;
    private String HeadImageAddress;
    private String SumPost;
    private String Level;
    private String Attention;
    private String Collect;
    private String Information;

    public User(String username,int id) {
        this.username = username;
        this.id = id;
    }

    public User(int id) {
        this.id = id;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        this.Information = information;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadImageAddress() {
        return HeadImageAddress;
    }

    public void setHeadImageAddress(String headImageAddress) {
        HeadImageAddress = headImageAddress;
    }

    public String getSumPost() {
        return SumPost;
    }

    public void setSumPost(String sumPost) {
        SumPost = sumPost;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getAttention() {
        return Attention;
    }

    public void setAttention(String attention) {
        Attention = attention;
    }

    public String getCollect() {
        return Collect;
    }

    public void setCollect(String collect) {
        Collect = collect;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User() {
    }

    public User(String username, String sex, String headImageAddress, String sumPost, String attention, String collect,String information) {
        this.username = username;
        this.sex = sex;
        this.HeadImageAddress = headImageAddress;
        this.SumPost = sumPost;
        this.Attention = attention;
        this.Collect = collect;
        Information =information;
    }

    public User(String username, String userpass, String phone, String code) {
        this.username = username;
        this.userpass = userpass;
        this.code = code;
        this.phone = phone;
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


    public String getCode() {
        return code;
    }

    public void setCode(String code) { this.code = code; }


}
