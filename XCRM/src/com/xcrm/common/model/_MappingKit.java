package com.xcrm.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("attribute", "id", Attribute.class);
		arp.addMapping("attributeid", "id", Attributeid.class);
		arp.addMapping("attributevalue", "id", Attributevalue.class);
		arp.addMapping("customer", "id", Customer.class);
		arp.addMapping("product", "id", Product.class);
		arp.addMapping("productcategory", "id", Productcategory.class);
		arp.addMapping("productpic", "id", Productpic.class);
		arp.addMapping("store", "id", Store.class);
		arp.addMapping("user", "id", User.class);
	}
}

