package Common;

import java.util.HashMap;
import java.util.Map;

public class common {
    //将http响应结果转换为map
    public static Map<String,String> str_to_map(String jsonstr){
        String middle_str = jsonstr.replace("{", "");
        String middle_str1 = middle_str.replace("}", "");
        String middle_str2 = middle_str1.replaceAll("\"","");
        String[] strs = middle_str2.split(",");
        Map<String, String> m = new HashMap<String, String>();
        for(String s:strs){
            String[] ms = s.split(":");
            m.put(ms[0], ms[1]);
        }
        return m;
    }
    public static void main(String[] args){
        String str = "{\"code\":-1,\"msg\":\"短信验证不通过\",\"data\":null}";
        Map<String,String> m1,m2 = new HashMap<>();
        m1 = str_to_map(str);
        m2.put("msg","成功");

    }
}
