package zlicense.verify;

public class licenseVerifyTest {
	public static void main(String[] args){
		VerifyLicense vLicense = new VerifyLicense();
		try{
			
//			vLicense.setParam("D:/workspace/GitWorkSpace/licensejar/src/main/resources/verifyparam.properties");
			vLicense.setParam("verifyparam.properties");
			vLicense.verify();
		}
		catch(Exception er){
			er.printStackTrace();
		}

	}
}
