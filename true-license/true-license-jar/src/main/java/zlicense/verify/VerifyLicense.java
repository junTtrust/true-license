package zlicense.verify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.prefs.Preferences;

import zlicense.de.schlichtherle.license.CipherParam;
import zlicense.de.schlichtherle.license.DefaultCipherParam;
import zlicense.de.schlichtherle.license.DefaultKeyStoreParam;
import zlicense.de.schlichtherle.license.DefaultLicenseParam;
import zlicense.de.schlichtherle.license.KeyStoreParam;
import zlicense.de.schlichtherle.license.LicenseManager;
import zlicense.de.schlichtherle.license.LicenseParam;

public class VerifyLicense {
	//common param
	private static String PUBLICALIAS = "";
	private static String STOREPWD = "";
	private static String SUBJECT = "";
	private static String licPath = "";
	private static String pubPath = "";
	
	/*public VerifyLicense() {
		System.out.println("======================项目唯一标识========================"+SUBJECT);
	}*/


	public void setParam(String propertiesPath) throws IOException {
		// 
		Properties prop = new Properties();
		System.out.println("propertiesPath="+propertiesPath);
		//InputStream in = getClass().getResourceAsStream(propertiesPath);		
//		InputStream in = new FileInputStream(propertiesPath);	
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(propertiesPath);   
		prop.load(in);		
		PUBLICALIAS = prop.getProperty("PUBLICALIAS");
		STOREPWD = prop.getProperty("STOREPWD");
		SUBJECT = prop.getProperty("SUBJECT");
		
		String property = System.getProperty("user.dir");//项目路径
		property=property.replaceAll("\"", "/");
		licPath = property+prop.getProperty("licPath");
		pubPath = property+prop.getProperty("pubPath");
	}

	public void setParam1(String propertiesPath,String fileName) throws IOException {
		// 
		Properties prop = new Properties();
		System.out.println("propertiesPath="+propertiesPath);
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(propertiesPath);   
		prop.load(in);		
		PUBLICALIAS = prop.getProperty("PUBLICALIAS");
		STOREPWD = prop.getProperty("STOREPWD");
		SUBJECT = prop.getProperty("SUBJECT");
//		SUBJECT=subject;
		
		String property = System.getProperty("user.dir");//项目路径
		property=property.replaceAll("\"", "/");
		
		licPath = property+prop.getProperty("licPath")+fileName;
		pubPath = property+prop.getProperty("pubPath");
	}
	
	public boolean verify(String localIp,String localMac) throws Exception  {		
		//每次校验生成新的licenseManager
		LicenseManager licenseManager = new LicenseManager(initLicenseParams());
		
		//全局licenseManager,第二次如果输入错误信息，，不校验
//		LicenseManager licenseManager = LicenseManagerHolder
//				.getLicenseManager(initLicenseParams());
		// install license file
		try {
			System.out.println("------------------------验证文件路径--------------"+licPath);
			licenseManager.install(new File(licPath),localIp,localMac);
			System.out.println("License file instal successfully!");				
		} catch (Exception e) {
			//e.printStackTrace();			
			String moreInfo ="License file instal failure";
			System.out.println(moreInfo);
			throw e;
		}
		// verify license file
		try {
//			licenseManager.verify();
			licenseManager.verify(localIp,localMac);
			System.out.println("License file verify successfully!");
		} catch (Exception e) {
			//e.printStackTrace();			
			String moreInfo ="License file verify failure";			
			System.out.println(moreInfo); 
			throw e;
		}
		return true;
	}

	//
	private static LicenseParam initLicenseParams() {
		System.out.println("=====================initLicense================="+SUBJECT);
		Preferences preference = Preferences
				.userNodeForPackage(VerifyLicense.class);
		CipherParam cipherParam = new DefaultCipherParam(STOREPWD);

		KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(
				VerifyLicense.class, pubPath, PUBLICALIAS, STOREPWD, null);
		LicenseParam licenseParams = new DefaultLicenseParam(SUBJECT,
				preference, privateStoreParam, cipherParam);
		return licenseParams;
	}
}