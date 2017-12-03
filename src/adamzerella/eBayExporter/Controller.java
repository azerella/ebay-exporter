package adamzerella.eBayExporter;

import com.ebay.soap.eBLBaseComponents.SiteCodeType;

import adamzerella.eBayExporter.util.*;

public class Controller {
	private Developer dev = null;
	
	Controller(){
		this.dev = new Developer();
		
		new Debug().Log("CREATED CONTROLLER DOMAIN");
	}
			
	public void ExportQueryToCSV() { }
	
	public String getEbayOfficalTime() {
		return new EbayGetCall(this.dev).getEbayOfficalTime().toString();
	}
	
	public String getMyeBayBoughtTitles() {
		return new EbayGetCall(this.dev).getMyEbayBuyTitles();
	}
	
	public void setDeveloper(Developer d) {
		this.dev = d;
	}

	public Developer getDev() {
		return dev;
	}
	
	public SiteCodeType[] getAllSiteCodes() {
		return SiteCodeType.values();
	}
	
	public String[] getAllServiceEndpoints() {
		String[] result = new String[4];
		result[0] = "https://api.ebay.com/wsapi"; 				//SOAP API Production Gateway URI
		result[1] = "https://api.ebay.com/ws/api.dll"; 			//XML API Production Gateway URI
		result[2] = "https://api.sandbox.ebay.com/wsapi"; 		//SOAP API Sandbox Gateway URI
		result[3] = "https://api.sandbox.ebay.com/ws/api.dll"; 	//XML API Sandbox Gateway URI
		return result;
	}
}