package adamzerella.eBayExporter;

import com.ebay.sdk.call.GeteBayOfficialTimeCall;
import com.ebay.sdk.helper.Utils;
import com.ebay.sdk.helper.ui.GuiUtil;
import com.ebay.soap.eBLBaseComponents.AccountEntryType;
import com.ebay.soap.eBLBaseComponents.AccountHistorySelectionCodeType;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.PaginationType;

import javax.swing.JTextField;

import com.ebay.sdk.TimeFilter;
import com.ebay.sdk.call.GetAccountCall;
import com.ebay.sdk.call.GetItemCall;
import com.ebay.sdk.call.GeteBayDetailsCall;

public class Main {
	static EbayContext context; 

	public static void main(String[] args) throws Exception {
		//eBay API context
		context = new EbayContext();

		//Call API with context and handle result
		//System.out.println("Official eBay Time : " + 
		//		new GeteBayOfficialTimeCall(context.getContext()).geteBayOfficialTime().getTime());
		//System.out.println("[CONTEXT REQUEST] " + "\n" + new GeteBayDetailsCall(context.getContext()).getApiContext().getRequestXml() +"\n");
		//System.out.println("[CONTEXT REPONSE] " + "\n" + new GeteBayDetailsCall(context.getContext()).getApiContext().getResponseXml());

		//System.out.println(getItemTitle("322119590771")); //Vibram running shoes
		System.out.println(getAccountUsername());

	}

	static String getItemTitle(String id) {
		ItemType item = null;

		try {
			GetItemCall gc = new GetItemCall(context.getContext());
			DetailLevelCodeType[] detailLevels = new DetailLevelCodeType[] {
					DetailLevelCodeType.RETURN_ALL,
					DetailLevelCodeType.ITEM_RETURN_ATTRIBUTES,
					DetailLevelCodeType.ITEM_RETURN_DESCRIPTION
			};
			gc.setDetailLevel(detailLevels);

			item = gc.getItem(id);
		}
		catch(Exception ex) {
			System.err.println("FAILED TO GET EBAY ITEM: " + ex.getMessage());
		}

		return item.getTitle();
	}

	static String getAccountUsername() {
		GetAccountCall acc = null;
		String res = "";

		try {
			acc = new GetAccountCall(context.getContext());
			//acc.setPagination(new PaginationType().getAny(5));
			//acc.setViewType(viewType);(new TimeFilter(new , arg1));
			//acc.setDetailLevel(new DetailLevelCodeType[] { DetailLevelCodeType.RETURN_ALL});
			//acc.setViewType(AccountHistorySelectionCodeType.LAST_INVOICE);
			for (AccountEntryType aet : acc.getAccount()) {
				res += aet.getTitle();
			}

		}
		catch(Exception ex) {
			System.err.println("FAILED TO GET EBAY ACCOUNT: " + ex.getMessage());
		}


		return res;
	}
}