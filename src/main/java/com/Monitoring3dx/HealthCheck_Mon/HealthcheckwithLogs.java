package com.Monitoring3dx.HealthCheck_Mon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//import org.apache.log4j.Logger;
//import org.apache.logging.log4j.Logger;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

//import com.mkyong.core.utils.App;

public class HealthcheckwithLogs {
	
	public static String sPassport3dURL = "https://_URL_/3dpassport/healthcheck";
	public static String sComment3dURL = "https://_URL_/3dcomment/monitoring/healthcheck";
	public static String sSpace3dURL = "https://_URL_/3dspace/servlet/fcs/ping";
	public static String sDashboard3dURL = "https://_URL_/3ddashboard/test-alive";
	public static String sNotification3dURL = "https://_URL_/3dnotification/healthcheck";
	public static String sdswym3dURL = "https://_URL_/3dswym/monitoring/healthcheck";
	public static String sSearch3dURL = "https://_URL_/3dsearch/manager?query=monitoring";
	public static String s3dCompass3dURL = "https://_URL_/3dcompass/servlet/fcs/ping";
	public static String s6WTags3dURL = "https://_URL_/6wtags/servlet/fcs/ping";
	public static String sFCSStatusURL = "https://_URL_/fcs/servlet/fcs/about";

	static Logger logger = Logger.getLogger(HealthcheckwithLogs.class.getName());

	public static void main(String[] args) {

		HashMap hmConfig = null;
		try {
			//PropertyConfigurator.configure("./log4j.properties");
			File jarFile = new File(HealthcheckwithLogs.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		    String jarDir = jarFile.getParentFile().getPath();
		    String log4jPath = jarDir + File.separator + "log4j.properties";
		    PropertyConfigurator.configure(log4jPath);

			HealthcheckwithLogs objHealthCheck = new HealthcheckwithLogs();

			// logger.setUseParentHandlers(false);
			logger.info("Starting the Process" + new Date());

			hmConfig = objHealthCheck.mReadConfig();

			if (hmConfig.size() == 2) {
				String sUrl = "";
				String sOutput = "";

				logger.info("Starting the Process" + new Date());
				if (args == null || args.length == 0 || args.length == 1) {
					logger.warn("There are no proper argruments in calling please check");
					System.out.println("Error");
				} else if (args != null && args.length == 2 && args[0].equalsIgnoreCase("Check")) {
					logger.info("Process services checked = " + args[1]);
					objHealthCheck.mCheckUrls(args, hmConfig);

				}
				logger.info("Ending the Process");
			} else if (hmConfig.size() == 3) {

				logger.info("Config file is not properly filled please check" + (String) hmConfig.get("ConfigError"));
				System.out.println("Error");

			} else {
				logger.warn("Config file is not proper please check");
				System.out.println("Error");
			}
		}

		catch (Exception e) {

			logger.error("Exception in main" + e);
			System.out.println("-1");
		}
	}

	// This method is checking the status of 3dx services
	public void mCheckUrls(String[] args, HashMap hmConfig) {
		// HashMap hmConfig = null;
		String sReturnVal = "";
		try {

			String sUrl = "";
			String sOutput = "";
			if (args[1].equalsIgnoreCase("3dpassport")) {
				sUrl = sPassport3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);
			} else if (args[1].equalsIgnoreCase("3dcomment")) {
				sUrl = sComment3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

			} else if (args[1].equalsIgnoreCase("3dspace")) {
				sUrl = sSpace3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

			} else if (args[1].equalsIgnoreCase("3ddashboard")) {
				sUrl = sDashboard3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

			} else if (args[1].equalsIgnoreCase("3dnotification")) {
				sUrl = sNotification3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

			} else if (args[1].equalsIgnoreCase("3dswym")) {
				sUrl = sdswym3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

			} else if (args[1].equalsIgnoreCase("3dsearch")) {
				sUrl = sSearch3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

			} else if (args[1].equalsIgnoreCase("3dcompass")) {
				sUrl = s3dCompass3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

			} else if (args[1].equalsIgnoreCase("6Wtags")) {
				sUrl = s6WTags3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

			} else if (args[1].equalsIgnoreCase("FCSStatus")) {
				sUrl = sFCSStatusURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

			} else if (args[1].equalsIgnoreCase("All")) {
				sUrl = sPassport3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

				sUrl = sComment3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

				sUrl = sSpace3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

				sUrl = sDashboard3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

				sUrl = sNotification3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

				sUrl = sdswym3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

				sUrl = sSearch3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

				sUrl = s3dCompass3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

				sUrl = s6WTags3dURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

				sUrl = sFCSStatusURL.replace("_URL_", (String) hmConfig.get("3DxDomain"));
				sOutput = mHealthCheckStatus(sUrl, "200");
				System.out.println(sOutput);

			} else {

				System.out.println("Error");
				logger.info("Issue in getting status" + sOutput);

			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.info("Issue in getting status" + e);
			System.out.println("-1");
		}
	}

	// This method is for reading values from property file
	public HashMap mReadConfig() {
		Properties prop = new Properties();
		HashMap hm = new HashMap();
		// StringBuilder sbErrorCatch =null;
		String sReturnVal = "";

		StringBuilder sbErrorCatch = new StringBuilder();

		try {

			URL root = getClass().getProtectionDomain().getCodeSource().getLocation();
			URL propertiesFile = new URL(root, "configforHealthCheck.properties");
			prop.load(propertiesFile.openStream());

			// load a properties file from class path, inside static method

			// hm.put("HealthCheck", prop.getProperty("HealthStatusCheck"));
			hm.put("3DxOS", prop.getProperty("3DxOS"));
			hm.put("3DxDomain", prop.getProperty("3DxDomain"));
			if (prop.getProperty("3DxOS") == null || "".equals(prop.getProperty("3DxOS"))) {
				sbErrorCatch.append("3DxOS is not there in config or empty please check");
			}
			if (prop.getProperty("3DxDomain") == null || "".equals(prop.getProperty("3DxDomain"))) {
				sbErrorCatch.append("3DxDomain is not there in config or empty please check");
			}

			if (!"".equals(sbErrorCatch.toString())) {
				hm.put("ConfigError", sbErrorCatch.toString());
			}
		} catch (IOException ex) {

			logger.error("Issue in Reading Config File. Please check the file and all parameters needed" + ex);
			System.out.println("-1");
		}
		return hm;
	}

	// This method is for the creation of curl command
		public String mHealthCheckStatus(String sUrl, String sOutcome) {

			String sReturn = "";

			try {
//
//			    Process process = new ProcessBuilder("curl", "-s", "-o", "/dev/null", "-w", "%{http_code}\n", sUrl, "-k",
//						"--connect-timeout", "5").start();
//                
//				InputStream is = process.getInputStream();
//				InputStreamReader isr = new InputStreamReader(is);
//				BufferedReader br = new BufferedReader(isr);
//				String line;
//				String scOutput = "";
// 
//				// Append the buffer lines into one string
//				while ((line = br.readLine()) != null) {
//					scOutput += line + "\n";
//				}
				URL website = new URL(sUrl);
	            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
	            connection.setConnectTimeout(5000);
	            int responseCode = connection.getResponseCode();
	            
				if (String.valueOf(responseCode).startsWith(sOutcome)) {
					sReturn = "1";
				} else {
					sReturn = "0";
					logger.info("Received response" + responseCode);
				}
			} catch (IOException ex) {
				sReturn = "-1";
				logger.error("Issue in Getting Health status" + ex);
			}
			return sReturn;

		}

}