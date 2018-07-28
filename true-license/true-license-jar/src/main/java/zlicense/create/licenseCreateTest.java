package zlicense.create;

public class licenseCreateTest {
	public static void main(String[] args){
		CreateLicense cLicense = new CreateLicense();
		
//		cLicense.setParam("D:/workspace/GitWorkSpace/licensejar/src/main/resources/createparam.properties");
		cLicense.setParam1("createparam.properties", "2018-03-09", "2018-03-09", "2019-03-20", "192.168.3.55", "9C-4E-36-D0-92-B8", "user", "1");
		//
		cLicense.create();
	}
}
