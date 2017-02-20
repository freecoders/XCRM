package com.xcrm.order;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.xcrm.common.AbstractController;
import com.xcrm.common.Pager;
import com.xcrm.common.util.Constant;

public class OrderController extends AbstractController {

	public void list() {
	    //price是原价  deal price是成交价  
		String sql = "select GROUP_CONCAT(p.name) name,o.orderno orderno,sum(bi.price*bi.num) price,o.price dealprice,sum(pay.paid) paid,sum(bi.num) num,oi.date date,contract.name contractname,contract.id contractid,round(o.price-sum(pay.paid), 2) due";
		String sqlExcept = " from orderitem oi "
		    + "left join bookitem bi on oi.bookitem=bi.id "
		    + "left join `order` o on o.id=oi.order "
		    + "left join product p on bi.product=p.id "
		    + "left join contract contract on bi.contract=contract.id "
		    + "left join payment pay on pay.orderno=o.orderno "
		    + "where bi.user=? group by o.orderno order by o.orderno desc";
		int pagenumber = Integer.parseInt(this.getPara("pageNumber"));
		int pagesize = Integer.parseInt(this.getPara("pageSize"));
		Page<Record> page = Db.paginate(pagenumber, pagesize, sql, sqlExcept, getCurrentUserId());
		Pager pager = new Pager(page.getTotalRow(), page.getList());
		this.renderJson(pager);

	}

	@Override
	public String getModalName() {
		return "order";
	}

	@Override
	public String getPageHeader() {
		return "订单管理";
	}

	@Override
	public String getToolBarAddButtonTitle() {
		return "我的订单";
	}

	@Override
	public String getIndexHtml() {
		return "order.html";
	}

	@Override
	public int getCategory() {
		return Constant.CATEGORY_ORDER;
	}
}
