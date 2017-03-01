package com.xcrm.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.xcrm.common.AbstractController;
import com.xcrm.common.model.Bookitem;
import com.xcrm.common.model.Order;
import com.xcrm.common.model.Orderitem;
import com.xcrm.common.model.Payment;
import com.xcrm.common.util.Constant;


public class PaymentController extends AbstractController {
  
  @Override
  protected void preSetAttribute() {
    this.setAttr( "paidsuggest", this.getSessionAttr( "price" ) );
  }

  public void submitorder() {
    saveOrder();
    this.removeSessionAttr( "ordercomments" );
    this.removeSessionAttr( "bookitems" );
    this.removeSessionAttr( "amount" );
    this.removeSessionAttr( "price" );
    this.renderNull();
  }

  public void saveOrder() {
    // save book items
    Integer customerId = getParaToInt( "customer" );
    Integer contractId = getParaToInt( "contract" );
    Integer paymenttype = getParaToInt( "paymenttype" );
    Date  deliverytime = getParaToDate( "deliverytime" );
    BigDecimal paid = new BigDecimal( Float.parseFloat( getPara( "paid" ).isEmpty()? "0.0" : getPara( "paid" ) ));
    String paymentcomments = getPara("paymentcomments");
    
    String bookitemids = this.getSessionAttr( "bookitems" );
    Db.update("update bookitem set status=1 where id in (" + bookitemids + ") ");
    String[] bookitemidarray = bookitemids.split( "," );
    for ( String bookitemid : bookitemidarray ) {
      Bookitem bi = Bookitem.dao.findById( bookitemid );
      bi.setContract( contractId );
      bi.setCustomer( customerId );
      bi.update();
    }
    // save order
    Order order = new Order();
    Date date = new Date();
    order.setDate( date );
    order.setOrderno( date.getTime() );
    order.setDeliverytime( deliverytime );
    // persist price
    String price = this.getSessionAttr( "price" );
    if ( price != null && !price.isEmpty() ) {
      order.setPrice( new BigDecimal( Float.parseFloat( price ) ) );
    }
    // persist amount
    String amount = this.getSessionAttr( "amount" );
    if ( amount != null && !amount.isEmpty() ) {
      order.setTotalprice( new BigDecimal(Float.parseFloat( amount )) );
    }
    // persist comments
    String ordercomments = this.getSessionAttr( "ordercomments" );
    if ( ordercomments != null && !ordercomments.isEmpty() ) {
      order.setComments( ordercomments );
    }
    order.save();
    
    //persist payment
    if(paid.floatValue() > 0){
      Payment payment = new Payment();
      payment.setPaymenttype( paymenttype );
      payment.setPaid( paid );
      payment.setComments( paymentcomments );
      payment.setCustomerid( customerId );
      payment.setPaymenttime( new Date() );
      payment.setOrderno( order.getOrderno() );
      payment.save();
    }
    
    // save order items
    List<Orderitem> orderitems = new ArrayList<Orderitem>();
    for ( String bookitemid : bookitemidarray ) {
      Orderitem orderitem = new Orderitem();
      orderitem.setBookitem( Integer.valueOf( bookitemid ) );
      orderitem.setDate( date );
      orderitem.setOrder( order.getId() );
      orderitems.add( orderitem );
    }
    Db.batchSave( orderitems, orderitems.size() );
  }

  @Override
  public String getModalName() {
    return "cart";
  }

  @Override
  public String getPageHeader() {
    return "订单结算页面";
  }

  @Override
  public String getToolBarAddButtonTitle() {
    return "";
  }

  @Override
  public String getIndexHtml() {
    return "payment.html";
  }

  @Override
  public int getCategory() {
    return Constant.CATEGORY_CARTLIST;
  }
}