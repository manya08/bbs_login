package dao;

import java.util.UUID;

public class UUIDtool {
    public static String getUUID(){
        String s = UUID.randomUUID().toString().toString().replace("-", "")+UUID.randomUUID().toString().toString().replace("-", "");
        return s;
    }
}
