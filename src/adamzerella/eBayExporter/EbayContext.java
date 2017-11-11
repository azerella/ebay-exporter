package adamzerella.eBayExporter;

import com.ebay.sdk.ApiAccount;
import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.soap.eBLBaseComponents.SiteCodeType;

class EbayContext {
	private ApiContext 		context;			//eBay API context
	private ApiAccount		account;			//eBay API account
	private ApiCredential 	cred;				//eBay API credentials
	private String 			AUTH_TOKEN 		= 	//Auth token
			"AgAAAA**AQAAAA**aAAAAA**FZUGWg**nY+sHZ2PrBmdj6"
			+ "wVnY+sEZ2PrA2dj6AFlYWjC5aBqQydj6x9nY+seQ**If4DAA**AAMAAA**swBRw"
			+ "ylF3aSamGNAVHuP2ARLJCl0rgfXV00l9T+SbCectLf+vluiNRdK/0oGvZWOzMER"
			+ "yd4Evi5+0OG6jd5Vh/3rUhcElbnDNM1SigE37+QnBPIhBziZniHzmUbJHe+cUHu"
			+ "jjUi79trfmcEZiahIODrGHKaqGgQlYn9Tt42C8MvDSIAUTANz/Y0pFMdUxA6aWE"
			+ "BMTMRqrvzUgzDIFJ3zmYgpaRWJMmBTvAHDLmrtEIHF4Tl4cGpmYdOTjCpv+7ahW"
			+ "JWjhONwZiLbfgXIPHGEYrNjo4JrtHdZ96bdQRLV9C2hk8vQCcFHnr5YdnQtXqrv"
			+ "f6oej/t14C+v/9AsvUbgPQUVa0U4dXvkDFEqPfb2cqGK5pSZW8+k72Bl3+pEboG"
			+ "5Roifd4OsYLLmXM/vGJlnCaKyvHIPd1QQ7plrTop+zCJCByQhlcTQ+cnQdRyChJ"
			+ "kVxSrt45jM0xiJskY7fuXdrK5VfXjRBa0DDR9jFISnk1PgMJ3LWNL6D36kpibnN"
			+ "GC43PRrHpXILnM3aD4+aSZHve400/8qEbja+ZU3phtueFnT6ddu9LnCG6i3t1l8"
			+ "qY1CAGmnioZc84ZRvv97TMb74urZQvhW00aWwvnIWcDd0UwPlZZiIesOzdmaGcr"
			+ "HFtrAFu+Pa5XKxtGITzZeWoSevSZQIpMQrFAPNCvQPzXfyWHJiY8S76NgtpyOJi"
			+ "oF0Id88nZz97+kaBAzdSMXbNwVRLGEOmuOKp97S8UrB44eIGEZG3K3zSzdVkB+8"
			+ "bWBxXYe";
	private String			API_APP_ID		= "AdamZere-Purchase-PRD-45d74fc8f"
			+ "-58f1db53";
	private String			API_DEV_ID		= "f91dd48f-bb28-4ce5-b3bb-abc375e"
			+ "a2a20";
	private String			API_CERT_ID		= "PRD-5d74fc8f1abd-2407-42d8-b6b6"
			+ "-a054";
	private String			API_SERVER_URL 	= "https://api.ebay.com/wsapi";
	private SiteCodeType	API_SITECODE	= SiteCodeType.AUSTRALIA;
	private String			API_WSDLVer		= "1039";

	/**
	 * Invokes the eBay API context session.
	 */
	public EbayContext() throws Exception{
		try {
			account = new ApiAccount();
			cred = new ApiCredential();
			context = new ApiContext();

			account.setDeveloper(API_DEV_ID);
			account.setApplication(API_APP_ID);
			account.setCertificate(API_CERT_ID);

			context.setApiServerUrl(API_SERVER_URL);
			context.setWSDLVersion(API_WSDLVer);
			context.setSite(API_SITECODE);

			cred = context.getApiCredential();
			cred.seteBayToken(AUTH_TOKEN);		
		}
		catch(Exception e) {
			System.err.println("FAILED TO ACCESS EBAY CONTEXT: " + e.getMessage());
		}
	}

	/**
	 * Return current eBay context session.
	 */
	public ApiContext getContext() {
		return this.context;
	}
}