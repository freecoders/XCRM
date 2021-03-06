package com.xcrm.work;

import com.xcrm.common.AbstractController;
import com.xcrm.common.model.Workflowanditemtemplate;
import com.xcrm.common.model.Workflowtemplate;
import com.xcrm.common.model.Workitemtemplate;
import com.xcrm.common.util.Constant;


public class WorkflowtemplateController extends AbstractController {

  public void save() {
    Workflowtemplate workflowtemplate = this.getModel( Workflowtemplate.class, "" , true);
    workflowtemplate.save();
    String departments =this.getParaMap().get( "departments" )[0];
    String weights = this.getParaMap().get( "weights" )[0];
    String[] departmentArray = departments.split( "," );
    String[] weightArray = weights.split( "," );
    for( int i=0;i < departmentArray.length; i ++){
      Workitemtemplate workitemtemplate = new Workitemtemplate();
      workitemtemplate.setDep( Integer.parseInt( departmentArray[i] ) );
      workitemtemplate.setWeight( Integer.parseInt( weightArray[i] ) );
      workitemtemplate.setIndex( i );
      workitemtemplate.setUserid( getCurrentUserId() );
      workitemtemplate.save();
      Workflowanditemtemplate relation = new Workflowanditemtemplate();
      relation.setWorkflowtemplate( workflowtemplate.getId() );
      relation.setWorkitemtemplate( workitemtemplate.getId() );
      relation.save();
    }
    forwardIndex();
  }


  public void remove() {
    Workflowtemplate.dao.deleteById( this.getParaToInt( 0 ) );
    forwardIndex();
  }

  @Override
  public String getModalName() {
    return "workflowtemplate";
  }

  @Override
  public String getPageHeader() {
    return "工单模板管理";
  }

  @Override
  public String getToolBarAddButtonTitle() {
    return "创建工单模板";
  }

  @Override
  public String getIndexHtml() {
    return "workflowtemplate.html";
  }
  
  @Override
  public int getCategory() {
    return Constant.CATEGORY_WORK;
  }

  @Override
  protected String searchWord() {
    return "name";
  }
  
  public void view( ){
    this.setAttribute();
    String templateid = this.getPara( "id" );
    Workflowtemplate template = Workflowtemplate.dao.findById( templateid );
    if( template == null ){
      this.forwardIndex();
    }else{
      this.setAttr( "worktemplatename", template.getName());
      this.setAttr( "workitemtemplates",  template.getWorkitemtemplateRecords() );
      this.render( "viewworkflowtemplate.html" );
    }
  }
  
  public void add( ){
    this.setAttr( "page_header", "创建新的工单模板");
    this.render( "addworkflowtemplate.html" );
  }
  
}
