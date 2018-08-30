package test;

public class demo9 {
    public static void main(String[] args){
        String useName= "aworkspacessh_photoWebContentuploadFile1444783552338pic.jpg";

        int begin=useName.indexOf("File");
        System.out.println(begin);
        int last=useName.length();

        System.out.println(useName.substring(0,begin));
    }


}
