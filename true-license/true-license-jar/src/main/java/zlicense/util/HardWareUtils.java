
package zlicense.util;


 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;


/*****
 *  Linux�?

command

0、check CPUID：dmidecode -t processor | grep 'ID'

1、check server type sn：dmidecode | grep 'Product Name'

2、check mother board SN：dmidecode |grep 'Serial Number'

3、check system SN：dmidecode -s system-serial-number

4、check memmory info：dmidecode -t memory

5、check OEM info：dmidecode -t 11
 * 
 *
 */
 
public class HardWareUtils {
 
    /**
     * mother board
     * 
     * @return
     */
    public static String getMotherboardSN() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
 
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";
 
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }
 
    /**
     * HardDisk
     * 
     * @param drive   C
     *             
     * @return
     */
    public static String getHardDiskSN(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
 
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\""
                    + drive
                    + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber"; // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }
 
    /**
     * CPU
     * 
     * @return
     */
    public static String getCPUSerial() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_Processor\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.ProcessorId \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";
 
            // + "    exit for  \r\n" + "Next";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
            file.delete();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        if (result.trim().length() < 1 || result == null) {
            result="no cpu id could be read";
        }
        return result.trim();
    }
 
    /**
     * mac address
     */
    public static String getMac() {
        String result = "";
        try {
 
            Process process = Runtime.getRuntime().exec("ipconfig /all");
 
            InputStreamReader ir = new InputStreamReader(
                    process.getInputStream());
 
            LineNumberReader input = new LineNumberReader(ir);
 
            String line;
 
            while ((line = input.readLine()) != null)
 
                if (line.indexOf("Physical Address") > 0) {
 
                    String MACAddr = line.substring(line.indexOf("-") - 2);
 
                    result = MACAddr;
                    System.out.println("MAC  SN:" + result);
                }
 
        } catch (java.io.IOException e) {
 
            System.err.println("IOException " + e.getMessage());
 
        }
        return result;
    }
 
    public static void main(String[] args) {
        System.out.println("CPU  SN:" + HardWareUtils.getCPUSerial());
        System.out.println("Motherboard  SN:" + HardWareUtils.getMotherboardSN());
        System.out.println("HardDisk C   SN:" + HardWareUtils.getHardDiskSN("c"));
        System.out.println("MAC  SN:" + HardWareUtils.getMac());
       
    	
    	/*try {

			InetAddress ia = InetAddress.getLocalHost();
			// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
		    byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

		    // 下面代码是把mac地址拼装成String
		    StringBuffer sb = new StringBuffer();

		    for (int i = 0; i < mac.length; i++) {
		        if (i != 0) {
		            sb.append("-");
		        }
		        // mac[i] & 0xFF 是为了把byte转化为正整数
		        String s = Integer.toHexString(mac[i] & 0xFF);
		        sb.append(s.length() == 1 ? 0 + s : s);
		    }

		    // 把字符串所有小写字母改为大写成为正规的mac地址并返回
		    System.out.println("address mac"+sb.toString().toUpperCase().replaceAll("-", ""));
		
		} catch (Exception e) {
			e.printStackTrace();
		}*/
    }
 
}