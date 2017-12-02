package adamzerella.eBayExporter;

import adamzerella.eBayExporter.util.Debug;

public abstract class EbayCall {
	
	EbayContext context 		= null; 
	Developer dev 				= null; 
	
	EbayCall(Developer dev){
		this.dev = dev;
		this.context = new EbayContext(dev);
		
		new Debug().Log("Create EbayCall");
	}	
}