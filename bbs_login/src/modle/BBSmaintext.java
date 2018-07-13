package modle;

public class BBSmaintext {
    int PostID ;
    int UserID ;
    String Title;
    String PostImage;
    String PostUserName;
    String PostContent;
    String PostTime;
    String PostType;
    String RepleyNumber;
    String PostReadNumber;
    String PostPop;

    public BBSmaintext(String title, String postImage, String postUserName, String postContent, String postTime, String repleyNumber, String postReadNumber,String postType,int postid) {
        Title = title;
        PostImage = postImage;
        PostUserName = postUserName;
        PostContent = postContent;
        PostTime = postTime;
        RepleyNumber = repleyNumber;
        PostReadNumber = postReadNumber;
        PostType =postType;
        PostID=postid;
    }

    public BBSmaintext() {
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int postID) {
        PostID = postID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPostImage() {
        return PostImage;
    }

    public void setPostImage(String postImage) {
        PostImage = postImage;
    }

    public String getPostUserName() {
        return PostUserName;
    }

    public void setPostUserName(String postUserName) {
        PostUserName = postUserName;
    }

    public String getPostContent() {
        return PostContent;
    }

    public void setPostContent(String postContent) {
        PostContent = postContent;
    }

    public String getPostTime() {
        return PostTime;
    }

    public void setPostTime(String postTime) {
        PostTime = postTime;
    }

    public String getPostType() {
        return PostType;
    }

    public void setPostType(String postType) {
        PostType = postType;
    }

    public String getRepleyNumber() {
        return RepleyNumber;
    }

    public void setRepleyNumber(String repleyNumber) {
        RepleyNumber = repleyNumber;
    }

    public String getPostReadNumber() {
        return PostReadNumber;
    }

    public void setPostReadNumber(String postReadNumber) {
        PostReadNumber = postReadNumber;
    }

    public String getPostPop() {
        return PostPop;
    }

    public void setPostPop(String postPop) {
        PostPop = postPop;
    }
}
