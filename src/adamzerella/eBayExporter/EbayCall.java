package adamzerella.eBayExporter;

import adamzerella.eBayExporter.util.Debug;

public abstract class EbayCall {
	private EbayContext context 		= null; 
	private Developer dev 				= null; 
	
	EbayCall(Developer dev){
		this.dev = dev;
		this.context = new EbayContext(dev);
		
		new Debug().Log("Create EbayCall");
	}	
}