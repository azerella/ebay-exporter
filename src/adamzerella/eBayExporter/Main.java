package adamzerella.eBayExporter;

import com.ebay.soap.eBLBaseComponents.AccountEntrySortTypeCodeType;
import com.ebay.soap.eBLBaseComponents.AccountEntryType;
import com.ebay.soap.eBLBaseComponents.AccountHistorySelectionCodeType;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
import com.ebay.soap.eBLBaseComponents.ItemListCustomizationType;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.ListingTypeCodeType;
import com.ebay.soap.eBLBaseComponents.OrderStatusFilterCodeType;
import com.ebay.soap.eBLBaseComponents.OrderTransactionType;
import com.ebay.soap.eBLBaseComponents.PaginatedOrderTransactionArrayType;
import com.ebay.soap.eBLBaseComponents.PaginationType;
import com.ebay.soap.eBLBaseComponents.SiteCodeType;

import com.ebay.sdk.TimeFilter;

import com.ebay.sdk.call.GetAccountCall;
import com.ebay.sdk.call.GetItemCall;
import com.ebay.sdk.call.GetMyeBayBuyingCall;
import com.ebay.sdk.call.GeteBayOfficialTimeCall;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Main {
	static EbayContext context; 

	public static void main(String[] args) throws Exception {
		//eBay API context
		context = new EbayContext();

//		System.out.println(getItemTitle("322119590771")); //Vibram running shoes

//		System.out.println(getAccountInvoiceDateBetweenSpecified(
//				new GregorianCalendar(2017, 7, 26),			//Greg is indexed from 0 -> 26/08/2017
//				new GregorianCalendar(2017, 10, 26)));		//Greg is indexed from 0 -> 26/11/2017

//		System.out.println(getAccountID());

		System.out.println(getMyEbayBuyTitles());
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
		GetItemCall item = null;
		
		try {
			DetailLevelCodeType[] detail = new DetailLevelCodeType[] {
					DetailLevelCodeType.RETURN_ALL,
			};
			
			item = new GetItemCall(context.getContext());
			item.setSite(context.getSiteCode()); //Must match context SideCodeType
			item.setDetailLevel(detail);
			
			item.getItem(id); //Execute
			
			return item.getItem().getTitle();
		}
		catch(Exception ex) {
			System.err.println("EXCEPTION : 'EBAY GET ITEM TITLE' : " + ex.getMessage());
		}
		
		return "";
	}

	static String getAccountInvoiceDateBetweenSpecified(Calendar from, Calendar to) {
		GetAccountCall acc = null;
		String result = "";
		DetailLevelCodeType[] detail = null;

		try {
			detail = new DetailLevelCodeType[] {
					DetailLevelCodeType.RETURN_ALL,
			};

			acc = new GetAccountCall(context.getContext());
			acc.setSite(context.getSiteCode());
			acc.setAccountEntrySortType(AccountEntrySortTypeCodeType.ACCOUNT_ENTRY_CREATED_TIME_DESCENDING);
			acc.setViewType(AccountHistorySelectionCodeType.BETWEEN_SPECIFIED_DATES);
			acc.setViewPeriod(new TimeFilter(from, to));
			acc.setDetailLevel(detail);

			acc.getAccount(); //Execute

			System.out.println(acc.getInvoiceDate());
		}
		catch(Exception ex) {
			System.err.println("EXCEPTION : 'EBAY GET ITEM TITLES BETWEEN DATES' : " + ex.getMessage());
		}

		return result;
	}

	static String getAccountID() {
		GetAccountCall acc = null;
		String result = "";
		DetailLevelCodeType[] detail = null;

		try {
			detail = new DetailLevelCodeType[] {
					DetailLevelCodeType.RETURN_ALL,
			};

			acc = new GetAccountCall(context.getContext());
			acc.setSite(context.getSiteCode());
			acc.setAccountEntrySortType(AccountEntrySortTypeCodeType.ACCOUNT_ENTRY_CREATED_TIME_DESCENDING);
			acc.setDetailLevel(detail);

			acc.getAccount(); //Execute

			System.out.println("Account ID: " + acc.getAccountID());
		}
		catch(Exception ex) {
			System.err.println("EXCEPTION : 'EBAY GET ITEM TITLES BETWEEN DATES' : " + ex.getMessage());
		}
		return result;
	}

	static String getMyEbayBuyTitles() {
		GetMyeBayBuyingCall buy = null;
		DetailLevelCodeType[] detail = null;
		ItemListCustomizationType list = null;
		PaginationType pt = null;
		String res = "";

		try {			
			detail = new DetailLevelCodeType[] {
					DetailLevelCodeType.RETURN_ALL,
			};

			pt = new PaginationType();
			pt.setEntriesPerPage(200);

			list = new ItemListCustomizationType();
			list.setDurationInDays(60); //API Min: 0, Max: 60
			list.setIncludeNotes(true);
			list.setInclude(true);
			list.setPagination(pt);
			list.setOrderStatusFilter(OrderStatusFilterCodeType.ALL);
			list.setListingType(ListingTypeCodeType.FIXED_PRICE_ITEM); //"Buy it now" only

			buy = new GetMyeBayBuyingCall(context.getContext());
			buy.setDetailLevel(detail);
			buy.setDeletedFromWonList(list);
			buy.setWonList(list);

			buy.getMyeBayBuying(); // Execute

			PaginatedOrderTransactionArrayType pagedDelFromWonList = buy.getReturnedDeletedFromWonList();
			PaginatedOrderTransactionArrayType pagedWonList = buy.getReturnedWonList();

			//Items from "DeletedFromWonList"
			for (OrderTransactionType ott : pagedDelFromWonList.getOrderTransactionArray().getOrderTransaction()) {
				res += ott.getTransaction().getItem().getTitle() + "\n";
			}

			//Items from "WonList"
			res += "\n";
			for (OrderTransactionType ott : pagedWonList.getOrderTransactionArray().getOrderTransaction()) {
				res += ott.getTransaction().getItem().getTitle() + "\n";
			}
		}
		catch(Exception ex) {
			System.err.println("EXCEPTION : 'myEBAY BUYING SUMMARY' : " + ex.getMessage());
		}

		return res;
	}

}