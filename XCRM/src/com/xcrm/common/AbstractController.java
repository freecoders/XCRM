/**
 * 
 */
package com.xcrm.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.xcrm.common.model.Attribute;
import com.xcrm.common.model.Attributevalue;
import com.xcrm.common.util.Constant;
import com.xcrm.common.util.PropUtil;

/**
 * @author jzhang12
 *
 */
public abstract class AbstractController extends Controller {
  
    public static final int[] USED_FOR_PRICE_FROM_PRODUCT = { AttributeID.PRD_MATERIAL.getId(), AttributeID.PRD_COLOR.getId(),  AttributeID.PRD_SIZE.getId()  };

	public AbstractController() {
		super();
	}

	public void index() {
		setAttribute();
		render(getIndexHtml());
	}

	protected void setAttribute() {
		setAttr("model", getModalName());
		setAttr("page_header", getPageHeader());
		setAttr("toolbar_create", getToolBarAddButtonTitle());
		if(getModalName().equalsIgnoreCase( "price" )){
		  List<Attribute> attributes = AttributeFinder.getInstance().getAllAttributeList(Constant.CATEGORY_PRODUCT, Constant.CATEGORY_PRICE);
		  Iterator<Attribute> iter = attributes.iterator();
          for ( ; iter.hasNext(); ) {
            Attribute attribute = iter.next();
            if ( attribute.getCategory() == Constant.CATEGORY_PRODUCT ) {
              if ( Arrays.binarySearch( USED_FOR_PRICE_FROM_PRODUCT, attribute.getAttributeid().intValue() ) < 0 ) {
                iter.remove();
              }
            }
          }
		  setAttr("attriutes", attributes);
		}else{
		  setAttr("attriutes", AttributeFinder.getInstance().getAllAttributeList(getCategory()));
		}
		setAttr("imgMaxCount", PropUtil.getPrdImgMaxSize());
	}

	public abstract String getModalName();

	public Model getModel() {
		return null;
	}

	public abstract String getPageHeader();

	public abstract String getToolBarAddButtonTitle();

	public abstract String getIndexHtml();

	public abstract int getCategory();

	public void list() {
		Pager pager = new Pager();
		if (this.getPara("pageNumber") != null) {
			int pagenumber = Integer.parseInt(this.getPara("pageNumber"));
			int pagesize = Integer.parseInt(this.getPara("pageSize"));
			Page<Record> page = Db.paginate(pagenumber, pagesize, "select * ", "from " + getModalName() + "");
			pager = new Pager(page.getTotalRow(), page.getList());
			List<Attribute> attributes = AttributeFinder.getInstance().getAllAttributeList(getCategory());
			String searchword = this.getPara("searchword");
			Iterator<Record> iter = pager.getRows().iterator();
			for (; iter.hasNext();) {
				Record record = iter.next();
				if (record.getStr("name") != null && searchword != null
						&& !record.getStr("name").contains(searchword)) {
					iter.remove();
					continue;
				}
				for (Attribute attribute : attributes) {
					Attributevalue av = Attributevalue.dao.findFirst(
							"select * from attributevalue where attributeid=? and objectid=? and category=?",
							attribute.getAttributeid(), record.getInt("id"), getCategory());
					if (av == null)
						continue;
					record.set("attribute-" + av.getAttributeid(), av.getValue());
				}
				preRenderJsonForList(record);
			}
			this.renderJson(pager);
		} else {
			List<Record> records = Db.find("select * from " + getModalName());
			pager = new Pager(records.size(), records);
			List<Attribute> attributes = AttributeFinder.getInstance().getAllAttributeList(getCategory());
			for (Record record : pager.getRows()) {
				for (Attribute attribute : attributes) {
					Attributevalue av = Attributevalue.dao.findFirst(
							"select * from attributevalue where attributeid=? and objectid=? and category=?",
							attribute.getAttributeid(), record.getInt("id"), getCategory());
					if (av == null)
						continue;
					record.set("attribute-" + av.getAttributeid(), av.getValue());
				}
				preRenderJsonForList(record);
			}
			this.renderJson(records);
		}
	}
	
	
	protected void preRenderJsonForList(Record record){}
	
	public void save() {
		this.renderHtml("not implementation");
	}

	public void update() {
		this.renderHtml("not implementation");
	}

	public void remove() {
		this.renderHtml("not implementation");
	}

	public void forwardIndex() {
		this.forwardAction("/" + getModalName() + "/index");
	}

	public void forwardIndex(Model<?> model) {
		this.setSessionAttr(Constant.CUR_OBJ, model);
		this.forwardAction("/" + getModalName() + "/index");
	}

	@SuppressWarnings("rawtypes")
	public int getCurrentUserId() {
		Object user = getSessionAttr(Constant.CUR_USER);
		int userId = (Integer) ((HashMap) user).get("id");
		return userId;
	}

	@SuppressWarnings("rawtypes")
	public int getCurrentStoreId() {
		Object user = getSessionAttr(Constant.CUR_USER);
		int storeid = (Integer) ((HashMap) user).get("storeid");
		return storeid;
	}

	public String getRealPath() {
		return this.getRequest().getServletContext().getRealPath("/") + Constant.SLASH;
	}

	public String getTempImgPath() {
		return this.getRealPath() + Constant.TEMP_IMG + Constant.SLASH + getCurrentUserId() + Constant.SLASH;
	}

	public String getContractTemplatePath() {
		return this.getRealPath() + PropUtil.getContractTemplatePath() + Constant.SLASH + Constant.SLASH;
	}

	public String getPrdImgPath(String prdid) {
		return this.getRealPath() + PropUtil.getPrdImgPath() + Constant.SLASH + prdid + Constant.SLASH;
	}

	public String getPrdQr2Path() {
		return this.getRealPath() + PropUtil.getPrdQr2Path() + Constant.SLASH;
	}

	public String getPrdImgBaseUrl() {
		return getImgUrl(PropUtil.getPrdImgPath());
	}

	public String getPrdQr2BaseUrl() {
		return getImgUrl(PropUtil.getPrdQr2Path());
	}

	public String getImgUrl(String url) {
		url = url + Constant.SLASH;
		if (PropUtil.isDevMode()) {
			url = Constant.SLASH + url;
		} else {
			url = PropUtil.getImgDomain() + Constant.SLASH + url;
		}
		return url;
	}

}
