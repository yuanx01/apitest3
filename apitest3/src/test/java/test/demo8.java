package test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

//@Listeners({com.demo.NewReport.class})
public class demo8 {

    @DataProvider
    public Object[][] dataProvider(){
        return new Object[][]{{1},{2}};
    }

    @Test(dataProvider="dataProvider")
    public void testAssert3(int a){
        Assert.assertEquals(1, a);
    }

    @Test
    public void testAssert4(){
        Assert.assertEquals("2", "2");
        //Reporter.log(casename + map.get("result"));
        //Assert.assertEquals(result,expectResult);
    }

}







