package adamzerella.eBayExporter;

import com.ebay.sdk.ApiAccount;
import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import adamzerella.eBayExporter.util.*;

class EbayContext extends ApiContext{
	private ApiAccount		APIaccount 	= null;		//eBay API account
	private ApiCredential 	APIcred		= null;		//eBay API credentials
	private Developer 		dev			= null;

	/**
	 * Invokes the eBay API context session.
	 * @param d - eBay API context parameters
	 */
	public EbayContext(Developer d){
		super();
		
		try {
			this.dev = d;

			this.APIaccount = new ApiAccount();
			this.APIcred = new ApiCredential();

			this.APIaccount.setDeveloper(d.getAPI_DEV_ID());
			this.APIaccount.setApplication(d.getAPI_APP_ID());
			this.APIaccount.setCertificate(d.getAPI_CERT_ID());

			this.setApiServerUrl(d.getAPI_SERVER_URL());
			this.setWSDLVersion(d.getAPI_WSDLVer());
			this.setSite(d.getAPI_SITECODE());
			this.setRouting("new");

			this.APIcred = this.getApiCredential();
			this.APIcred.seteBayToken(d.getAUTH_TOKEN());

			new Debug().Log("CREATED EBAY CONTEXT");
		}
		catch(Exception e) {
			new Debug().Err("EBAY CONTEXT" + e.getMessage());
		}
	}
	
	public Developer getDeveloper() {
		return this.dev;
	}
}