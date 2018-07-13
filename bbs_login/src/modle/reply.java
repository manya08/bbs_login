package modle;

public class reply {
    int ID;
    int PostID;
    int ReplyID;
    String ReplyAccount;
    String ReplyContent;
    String Replytime;

    public reply() {
    }

    public reply(int ID, int postID, int replyID, String replyAccount, String replyContent, String replytime) {
        this.ID = ID;
        PostID = postID;
        ReplyID = replyID;
        ReplyAccount = replyAccount;
        ReplyContent = replyContent;
        Replytime = replytime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int postID) {
        PostID = postID;
    }

    public int getReplyID() {
        return ReplyID;
    }

    public void setReplyID(int replyID) {
        ReplyID = replyID;
    }

    public String getReplyAccount() {
        return ReplyAccount;
    }

    public void setReplyAccount(String replyAccount) {
        ReplyAccount = replyAccount;
    }

    public String getReplyContent() {
        return ReplyContent;
    }

    public void setReplyContent(String replyContent) {
        ReplyContent = replyContent;
    }

    public String getReplytime() {
        return Replytime;
    }

    public void setReplytime(String replytime) {
        Replytime = replytime;
    }
}
