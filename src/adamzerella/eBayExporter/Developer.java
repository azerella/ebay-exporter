package adamzerella.eBayExporter;

import com.ebay.soap.eBLBaseComponents.SiteCodeType;

public class Developer {
	private String 			AUTH_TOKEN 		= "";
	private String			API_APP_ID		= "";
	private String			API_DEV_ID		= "";
	private String			API_CERT_ID		= "";
	private String			API_SERVER_URL 	= "";
	private SiteCodeType	API_SITECODE	= null;
	private final String	API_WSDLVer		= "1039";

	Developer(){}

	Developer(String auth, String appId, String devId, String certID, String server){
		this.AUTH_TOKEN = auth;
		this.API_APP_ID = appId;
		this.API_DEV_ID = devId;
		this.API_CERT_ID = certID; 
		this.API_SERVER_URL = server;
		this.API_SITECODE = SiteCodeType.AUSTRALIA;
	}

	Developer(String auth, String appId, String devId, String certID, String server, SiteCodeType siteCode){
		this.AUTH_TOKEN = auth;
		this.API_APP_ID = appId;
		this.API_DEV_ID = devId;
		this.API_CERT_ID = certID; 
		this.API_SERVER_URL = server;
		this.API_SITECODE = siteCode;
	}

	public String getAUTH_TOKEN() {
		return AUTH_TOKEN;
	}

	public void setAUTH_TOKEN(String aUTH_TOKEN) {
		AUTH_TOKEN = aUTH_TOKEN;
	}

	public String getAPI_APP_ID() {
		return API_APP_ID;
	}

	public void setAPI_APP_ID(String aPI_APP_ID) {
		API_APP_ID = aPI_APP_ID;
	}

	public String getAPI_DEV_ID() {
		return API_DEV_ID;
	}

	public void setAPI_DEV_ID(String aPI_DEV_ID) {
		API_DEV_ID = aPI_DEV_ID;
	}

	public String getAPI_CERT_ID() {
		return API_CERT_ID;
	}

	public void setAPI_CERT_ID(String aPI_CERT_ID) {
		API_CERT_ID = aPI_CERT_ID;
	}

	public String getAPI_SERVER_URL() {
		return API_SERVER_URL;
	}

	public void setAPI_SERVER_URL(String aPI_SERVER_URL) {
		API_SERVER_URL = aPI_SERVER_URL;
	}

	public SiteCodeType getAPI_SITECODE() {
		return API_SITECODE;
	}

	public void setAPI_SITECODE(SiteCodeType aPI_SITECODE) {
		API_SITECODE = aPI_SITECODE;
	}

	public String getAPI_WSDLVer() {
		return API_WSDLVer;
	}

	@Override
	public String toString() {
		return "{ AUTH_TOKEN: " + AUTH_TOKEN + "\n"
				+ "APP_ID: " + API_APP_ID	+ "\n"
				+ "API_DEV_ID: " + API_DEV_ID	+ "\n"
				+ "API_CERT_ID: " + API_CERT_ID+ "\n"
				+ "API_SERVER_URL: " + API_SERVER_URL + "\n"
				+ "API_SITECODE: " + API_SITECODE
				+ " }";
	}
}