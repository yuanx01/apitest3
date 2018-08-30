package testerListen;

import com.vimalselvam.testng.SystemInfo;
import org.testng.collections.Maps;

import java.util.Map;

public class MysystemInfo implements SystemInfo {
    @Override
    public Map<String,String> getSystemInfo(){
        Map<String,String> systemInfo = Maps.newHashMap();
        systemInfo.put("Test Env","QA");
        systemInfo.put("测试执行人","袁秀");
        return systemInfo;
    }

}
