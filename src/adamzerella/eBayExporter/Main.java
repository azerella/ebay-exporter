package adamzerella.eBayExporter;

import com.ebay.soap.eBLBaseComponents.AccountEntrySortTypeCodeType;
import com.ebay.soap.eBLBaseComponents.AccountEntryType;
import com.ebay.soap.eBLBaseComponents.AccountHistorySelectionCodeType;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
import com.ebay.soap.eBLBaseComponents.ItemListCustomizationType;
import com.ebay.soap.eBLBaseComponents.ItemSortTypeCodeType;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.ListingTypeCodeType;
import com.ebay.soap.eBLBaseComponents.OrderStatusFilterCodeType;
import com.ebay.soap.eBLBaseComponents.SiteCodeType;

import com.ebay.sdk.TimeFilter;
import com.ebay.sdk.call.GetAccountCall;
import com.ebay.sdk.call.GetItemCall;
import com.ebay.sdk.call.GetMyeBayBuyingCall;
import com.ebay.sdk.call.GeteBayOfficialTimeCall;

import java.util.Calendar;
import java.util.Date;


public class Main {
	static EbayContext context; 

	public static void main(String[] args) throws Exception {
		//eBay API context
		context = new EbayContext();

		//System.out.println(getItemTitle("322119590771")); //Vibram running shoes
		
//		System.out.println(getAccountTitlesBetweenSpecified(
//				new GregorianCalendar(2017, 7, 26),			//Greg is indexed from 0 -> 26/08/2017
//				new GregorianCalendar(2017, 10, 26)));		//Greg is indexed from 0 -> 26/11/2017
		
		System.out.println(getMyebayBuySummary());
	}

	static Date getEbayOfficalTime() {
		try {
			return new GeteBayOfficialTimeCall(context.getContext()).geteBayOfficialTime().getTime();			
		}
		catch (Exception ex) {
			System.err.println("EXCEPTION : 'EBAY GET OFFICAL TIME' : " + ex.getMessage());
		}
		return null;
	}
	
	static String getItemTitle(String id) {
		ItemType item = null;

		try {
			GetItemCall gc = new GetItemCall(context.getContext());
			gc.setSite(SiteCodeType.AUSTRALIA); //Must match context SideCodeType
			DetailLevelCodeType[] detailLevels = new DetailLevelCodeType[] {
					DetailLevelCodeType.RETURN_ALL,
					DetailLevelCodeType.ITEM_RETURN_ATTRIBUTES,
					DetailLevelCodeType.ITEM_RETURN_DESCRIPTION
			};
			gc.setDetailLevel(detailLevels);

			item = gc.getItem(id);
		}
		catch(Exception ex) {
			System.err.println("EXCEPTION : 'EBAY GET ITEM TITLE' : " + ex.getMessage());
		}
		return item.getTitle();
	}

	static String getAccountTitlesBetweenSpecified(Calendar from, Calendar to) {
		GetAccountCall acc = null;
		String result = "";
		
		try {
			acc = new GetAccountCall(context.getContext());
			acc.setSite(SiteCodeType.AUSTRALIA);
			acc.setAccountEntrySortType(AccountEntrySortTypeCodeType.ACCOUNT_ENTRY_CREATED_TIME_DESCENDING);
			acc.setViewType(AccountHistorySelectionCodeType.BETWEEN_SPECIFIED_DATES);
			acc.setViewPeriod(new TimeFilter(from, to));

			for (AccountEntryType aet : acc.getAccount()) {
				result += aet.getTitle();
			}
		}
		catch(Exception ex) {
			System.err.println("EXCEPTION : 'EBAY GET ITEM TITLES BETWEEN DATES' : " + ex.getMessage());
		}
		return result;
	}
	
	static String getMyebayBuySummary() {
		GetMyeBayBuyingCall buy = null;
		ItemListCustomizationType list = null;
		
		try {			
			buy = new GetMyeBayBuyingCall(context.getContext());
			buy.setSite(SiteCodeType.AUSTRALIA);
			
			list = new ItemListCustomizationType();
			list.setListingType(ListingTypeCodeType.FIXED_PRICE_ITEM);
			list.setSort(ItemSortTypeCodeType.PRICE);
			list.setDurationInDays(100);
			list.setInclude(true);
			list.setOrderStatusFilter(OrderStatusFilterCodeType.ALL);
			
			buy.setWonList(list);
		}
		catch(Exception ex) {
			System.err.println("EXCEPTION : 'myEBAY BUYING SUMMARY' : " + ex.getMessage());
		}
		
		return buy.getResponseXml();
	}
	
}