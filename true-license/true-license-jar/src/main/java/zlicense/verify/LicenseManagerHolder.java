package zlicense.verify;
import zlicense.de.schlichtherle.license.LicenseManager;
import zlicense.de.schlichtherle.license.LicenseParam;


public class LicenseManagerHolder {
	
	private static LicenseManager licenseManager;
 
	public static synchronized LicenseManager getLicenseManager(LicenseParam licenseParams) {
    	if (licenseManager == null) {
    		licenseManager = new LicenseManager(licenseParams);
    	}
    	return licenseManager;
    }
}