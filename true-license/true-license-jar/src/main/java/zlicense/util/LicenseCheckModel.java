
package zlicense.util;

import java.util.Set;

public class LicenseCheckModel {
	
	private String ipAddress;    
	//private String ipMacAddress;
	private Set<String> ipMacAddress;
	private String CPUSerial;
	private String motherboardSN;
	private String hardDiskSN;
	
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
//	public String getIpMacAddress() {
//		return ipMacAddress;
//	}
//	public void setIpMacAddress(String ipMacAddress) {
//		this.ipMacAddress = ipMacAddress;
//	}
	
	public String getCPUSerial() {
		return CPUSerial;
	}
	public Set<String> getIpMacAddress() {
		return ipMacAddress;
	}
	public void setIpMacAddress(Set<String> ipMacAddress) {
		this.ipMacAddress = ipMacAddress;
	}
	public void setCPUSerial(String cPUSerial) {
		CPUSerial = cPUSerial;
	}
	public String getMotherboardSN() {
		return motherboardSN;
	}
	public void setMotherboardSN(String motherboardSN) {
		this.motherboardSN = motherboardSN;
	}
	public String getHardDiskSN() {
		return hardDiskSN;
	}
	public void setHardDiskSN(String hardDiskSN) {
		this.hardDiskSN = hardDiskSN;
	}

}
