package Common;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {
    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;
    private static XSSFRow Row;
    public static int colFlag = 0;
    public static int rowFlag = 0;
    public static int usedRowFlag = 1;
    public static String[] colName = null;
    public static XSSFSheet hssfSheet;

    //读取Excel
    public static String[][] read_excel(String path,String sheetname){
        String[][] returnArray1 = new String[rowFlag][colFlag];
        String[][] returnArray;
        POIFSFileSystem poifsFileSystem;
        FileInputStream intputStream;
        File file = new File(path);
        //path = file.getAbsolutePath(path);
        //String path1 = path;
        Map<Object, String> map = new HashMap<>();
        List<String> list1 = new ArrayList<>();
        //path="C:\\Users\\good\\Desktop\\接口测试用例.xlsx";
        try {
            InputStream is = new FileInputStream(path);
            //打开  EXCEL工作薄
            XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
            //获取 sheet
            //hssfSheet = hssfWorkbook.getSheet("2007测试表");
            hssfSheet = hssfWorkbook.getSheet(sheetname);
            //获取一行对象
            if (hssfSheet != null) {
                XSSFRow hssfRow = hssfSheet.getRow(0);
                //获取有多少列
                colFlag = hssfRow.getPhysicalNumberOfCells();
                int rows = hssfSheet.getLastRowNum() + 1;
                colName = new String[colFlag];
                //获取有多少行
                returnArray = new String[rows][colFlag];
                for (int r = 1; r <rows; r++) {
                    //returnArray[r][0] = String.valueOf(r);
                    for (int c = 0;c < colFlag; c++){
                        XSSFCell cell = hssfSheet.getRow(r).getCell(c);
                        try{
                            if (cell != null){
                                list1.add(getCellValue(cell));
                                returnArray[r][c] = getCellValue(cell);
                            }else {
                                list1.add("");
                            }

                        }catch (NullPointerException e){
                            list1.add("");
                            returnArray[r][c] = "";
                        }
                    }
                    rowFlag++;
                    int m = returnArray.length - 1;
                    int n = returnArray[1].length;
                    returnArray1 = new String[m][n];
                    for (int i=0;i<m;i++){
                        for (int j=0;j<n;j++){
                            returnArray1[i][j]= returnArray[i+1][j];
                            //System.out.println(returnArray1[i][j]);
                        }
                    }
                }
            }
        } catch(IOException e){
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
        //System.out.println(list1);
        return returnArray1;
    }

    //规范读取到的数据
    public static String getCellValue(XSSFCell cell) {
        String cellValue = "";
        DecimalFormat df = new DecimalFormat("#");
        switch (cell.getCellTypeEnum()) {
            case STRING:
                cellValue = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    SimpleDateFormat sdf = null;
                    sdf = new SimpleDateFormat("HH:mm");
                    cellValue = sdf.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    SimpleDateFormat sdf1 = null;
                    sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf1.format(cell.getDateCellValue());
                } else {
                    String sdf2 = null;
                    sdf2 = new String();
                    cellValue = sdf2.valueOf(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                Boolean sdf3 = null;
                cellValue = sdf3.toString(cell.getBooleanCellValue());
                break;
            case BLANK:
                cellValue = "";
                //break;
            default:
                cellValue = cell.toString();
                break;
        }
        return cellValue;
    }
    public static void main(String[] args){
        String[][] testdata = read_excel("C:\\Users\\good\\Desktop\\接口测试用例.xlsx","2007测试表");
        System.out.println(testdata);

    }

}

