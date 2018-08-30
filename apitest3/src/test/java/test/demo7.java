package test;
import Common.ExcelReader;
import Common.Testmethod;

import com.aventstack.extentreports.ExtentReporter;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class demo7 {
    @DataProvider
    public Object[][] TestData(){
        return ExcelReader.read_excel("C:\\Users\\good\\Desktop\\接口测试用例.xlsx","2007测试表");
    }

    @Test(dataProvider = "TestData")
    public void testRegister(String casename,String url,String method,String params,String expectResult,String realityResult){
        Map<String,String> map = new HashMap<String,String>();
        map = Testmethod.testcase(method,url,params);
        String actural = map.get("msg");
        String status_code = map.get("statuscode");
        String result = map.get("result");
        //Assert.assertTrue(testResult(expectResult,result),"原因：实际结果与预期结果不符，" + "实际结果：" + status_code + "," +result +"," + "预期结果:" + expectResult + ",");
        //记录http响应结果
        Reporter.log("status_code:" + status_code + ","+ result);
        //判断测试的预期结果与实际结果是否一致
        Assert.assertEquals(actural,expectResult);

    }

    //判断实际结果是否包含预期结果
    public static Boolean testResult(String expect,String actural){
        int result1 = actural.indexOf(expect);
        if (result1 != -1){
            return true;
        }else
            return false;
    }
}

