package adamzerella.eBayExporter;

import java.util.Calendar;
import java.util.Date;

import com.ebay.sdk.TimeFilter;
import com.ebay.sdk.call.GetAccountCall;
import com.ebay.sdk.call.GetMyeBayBuyingCall;
import com.ebay.sdk.call.GeteBayOfficialTimeCall;
import com.ebay.soap.eBLBaseComponents.AccountEntrySortTypeCodeType;
import com.ebay.soap.eBLBaseComponents.AccountHistorySelectionCodeType;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
import com.ebay.soap.eBLBaseComponents.ItemListCustomizationType;
import com.ebay.soap.eBLBaseComponents.ListingTypeCodeType;
import com.ebay.soap.eBLBaseComponents.OrderStatusFilterCodeType;
import com.ebay.soap.eBLBaseComponents.OrderTransactionType;
import com.ebay.soap.eBLBaseComponents.PaginatedOrderTransactionArrayType;
import com.ebay.soap.eBLBaseComponents.PaginationType;

import adamzerella.eBayExporter.util.*;

public class EbayGetCall extends EbayCall {

	public EbayGetCall(Developer d) {
		super(d);
		
		new Debug().Log("Create EbayGetCall");
	}

	/**
	 * Return the current eBay server time with the developer context
	 * @return Date - eBay Server time.
	 */
	public Date getEbayOfficalTime() {
		try {
			return new GeteBayOfficialTimeCall(super.context).geteBayOfficialTime().getTime();			
		}
		catch (Exception ex) {
			new Debug().Err("GeteBayOfficialTimeCall: " + ex.getMessage());
		}
		return null;
	}

	public String getAccountInvoiceDateBetweenSpecified(Calendar from, Calendar to) {
		GetAccountCall acc = null;
		String result = "";
		DetailLevelCodeType[] detail = null;

		try {
			detail = new DetailLevelCodeType[] {
					DetailLevelCodeType.RETURN_ALL,
			};

			acc = new GetAccountCall(context);
			acc.setSite(context.getDeveloper().getAPI_SITECODE());
			acc.setAccountEntrySortType(AccountEntrySortTypeCodeType.ACCOUNT_ENTRY_CREATED_TIME_DESCENDING);
			acc.setViewType(AccountHistorySelectionCodeType.BETWEEN_SPECIFIED_DATES);
			acc.setViewPeriod(new TimeFilter(from, to));
			acc.setDetailLevel(detail);

			acc.getAccount(); //Execute

			System.out.println(acc.getInvoiceDate());
		}
		catch(Exception ex) {
			new Debug().Err("EBAY GET ITEM TITLES BETWEEN DATES" + ex.getMessage());
		}

		return result;
	}

	public String getAccountID() {
		GetAccountCall acc = null;
		String result = "";
		DetailLevelCodeType[] detail = null;

		try {
			detail = new DetailLevelCodeType[] {
					DetailLevelCodeType.RETURN_ALL,
			};

			acc = new GetAccountCall(context);
			acc.setSite(context.getDeveloper().getAPI_SITECODE());
			acc.setAccountEntrySortType(AccountEntrySortTypeCodeType.ACCOUNT_ENTRY_CREATED_TIME_DESCENDING);
			acc.setDetailLevel(detail);

			acc.getAccount(); //Execute
		}
		catch(Exception ex) {
			new Debug().Err("EBAY GET ITEM TITLES BETWEEN DATES" + ex.getMessage());
		}
		return result;
	}

	public String getMyEbayBuyTitles() {
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

			buy = new GetMyeBayBuyingCall(context);
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
			new Debug().Err("MyeBay BUYING SUMMARY" + ex.getMessage());
		}

		return res;
	}
}