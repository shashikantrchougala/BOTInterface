package view.backing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.security.auth.login.LoginException;

import oracle.iam.platform.utils.vo.OIMType;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailHeader;
import oracle.adf.view.rich.component.rich.nav.RichCommandToolbarButton;
import oracle.adf.view.rich.component.rich.output.RichSpacer;
import oracle.iam.request.vo.Request;
import oracle.iam.api.OIMService;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.request.api.RequestService;
import oracle.iam.request.vo.RequestConstants;
import oracle.iam.request.vo.RequestData;
import oracle.iam.request.vo.RequestEntity;
import oracle.iam.request.vo.RequestEntityAttribute;
//import oracle.iam.ui.platform.model.common.getOIMConnection;
import oracle.iam.vo.OperationResult;
import oracle.iam.ui.platform.utils.FacesUtils;
import oracle.iam.request.vo.RequestEntityAttribute;
import oracle.iam.ui.platform.model.common.OIMClientFactory;

public class BotUsersPage 
{
    private RichPanelHeader ph1;
    private RichSpacer s1;
    private RichShowDetailHeader sdh1;
    private RichPanelFormLayout pfl1;
    private RichInputText it1;
    private RichInputText it2;
    private RichInputText it3;
    private RichInputText it4;
    private RichInputText it5;
    private RichSpacer s2;
    private UIComponent pgl1;


    public void setPh1(RichPanelHeader ph1) {
        this.ph1 = ph1;
    }

    public RichPanelHeader getPh1() {
        return ph1;
    }

    public void setS1(RichSpacer s1) {
        this.s1 = s1;
    }

    public RichSpacer getS1() {
        return s1;
    }

    public void setSdh1(RichShowDetailHeader sdh1) {
        this.sdh1 = sdh1;
    }

    public RichShowDetailHeader getSdh1() {
        return sdh1;
    }

    public void setPfl1(RichPanelFormLayout pfl1) {
        this.pfl1 = pfl1;
    }

    public RichPanelFormLayout getPfl1() {
        return pfl1;
    }

    public void setIt1(RichInputText it1) {
        this.it1 = it1;
    }

    public RichInputText getIt1() {
        return it1;
    }

    public void setIt2(RichInputText it2) {
        this.it2 = it2;
    }

    public RichInputText getIt2() {
        return it2;
    }

    public void setIt3(RichInputText it3) {
        this.it3 = it3;
    }

    public RichInputText getIt3() {
        return it3;
    }

    public void setIt4(RichInputText it4) {
        this.it4 = it4;
    }

    public RichInputText getIt4() {
        return it4;
    }

    public void setIt5(RichInputText it5) {
        this.it5 = it5;
    }

    public RichInputText getIt5() {
        return it5;
    }
    
    public void setS2(RichSpacer s2) {
        this.s2 = s2;
    }

    public RichSpacer getS2() {
        return s2;
    }
    
    public void setPgl1(UIComponent pgl1) {
        this.pgl1 = pgl1;
    }

    public UIComponent getPgl1() {
        return pgl1;
    }
    
    public void submitActionListener(ActionEvent actionEvent)
    {
        
        System.out.println("Inside BotUsersPage submitActionListener v0.2");
  
        String firstName =(String)getIt2().getValue();
        String lastName = (String)getIt1().getValue();
        String mgrID =  (String)getIt3().getValue();
        String processID =  (String)getIt4().getValue();
        String botID =  (String)getIt5().getValue();
        
        
        
        System.out.println("[BOT First Name] "+firstName+" [BOT Last Name] "+lastName+" [Supervisor's Corp ID / LANID] "+mgrID+" [ProcessID] "+processID+" [BOTID] "+botID);
        
        try
        {
//            getOIMConnection
            UserManager findManager = OIMClientFactory.getUserManager();
            SearchCriteria mgrCriteria = new SearchCriteria(UserManagerConstants.AttributeName.USER_LOGIN.getId(), mgrID, SearchCriteria.Operator.EQUAL);
            Set<String> mgrRetAttrs = new HashSet<String>();
            mgrRetAttrs.add(UserManagerConstants.AttributeName.MANAGER_KEY.getId());
            List<User> mgrs = findManager.search(mgrCriteria, mgrRetAttrs, null);
            int mgrsSize=mgrs.size();
            boolean validManager = true;
            
            if(mgrsSize == 0)
            {
                validManager = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Manager Login "+mgrID, "Invalid Manager Login "+mgrID));  
            }
            else
            {
                mgrID = mgrs.get(0).getEntityId();
            }
            if(validManager)
            {
                    String userLogin = "pAlge"+processID+botID;
                    if(userLogin.length() < 12)
                    {
                        boolean trailing = true;
                        String filledVal = "0";
                        for (int i = 0; i < (12 - userLogin.length()); i++) 
                        {
                                if (trailing) 
                                {
                                      trailing = false;
                                } 
                                else 
                                {
                                      filledVal = "0" + filledVal;
                                }
                        }
                        userLogin = userLogin+filledVal;
                    }
                    System.out.println("User Login "+userLogin);
                    
                    
                    UserManager userManger = OIMClientFactory.getUserManager();
                    SearchCriteria criteria = new SearchCriteria(UserManagerConstants.AttributeName.USER_LOGIN.getId(), userLogin, SearchCriteria.Operator.EQUAL);
                    Set<String> retAttrs = new HashSet<String>();
                    retAttrs.add(UserManagerConstants.AttributeName.USER_LOGIN.getId());
                    List<User> users = userManger.search(criteria, retAttrs, null);
                    int size=users.size();
                    if(size == 0)
                    {
        
                        List<RequestEntityAttribute> reqModAttrs = new ArrayList<RequestEntityAttribute>();
                                   
                        RequestEntityAttribute modAttr1 = new RequestEntityAttribute(UserManagerConstants.AttributeName.USER_LOGIN.getId(),userLogin, RequestEntityAttribute.TYPE.String);
                        RequestEntityAttribute modAttr2 = new RequestEntityAttribute("Organization","1", RequestEntityAttribute.TYPE.Long);
                        RequestEntityAttribute modAttr3 = new RequestEntityAttribute("Role","Full-Time",RequestEntityAttribute.TYPE.String);
        //                RequestEntityAttribute modAttr4 = new RequestEntityAttribute("Personnel Numbr",processID+botID,RequestEntityAttribute.TYPE.String);
                        if(!("".equalsIgnoreCase(firstName) && firstName == null))
                        {
                            RequestEntityAttribute modAttr5 = new RequestEntityAttribute(UserManagerConstants.AttributeName.FIRSTNAME.getId(), firstName, RequestEntityAttribute.TYPE.String);
                            reqModAttrs.add(modAttr5);
                        }                
                        else
                        {
                            RequestEntityAttribute modAttr5 = new RequestEntityAttribute(UserManagerConstants.AttributeName.FIRSTNAME.getId(), "pAlge", RequestEntityAttribute.TYPE.String);
                            reqModAttrs.add(modAttr5);
                        }
                        if(!("".equalsIgnoreCase(lastName) && lastName == null))
                        {
                            RequestEntityAttribute modAttr6 = new RequestEntityAttribute(UserManagerConstants.AttributeName.LASTNAME.getId(), lastName, RequestEntityAttribute.TYPE.String);
                            reqModAttrs.add(modAttr6);
                        }                
                        else
                        {
                            RequestEntityAttribute modAttr6 = new RequestEntityAttribute(UserManagerConstants.AttributeName.LASTNAME.getId(), processID+botID, RequestEntityAttribute.TYPE.String);
                            reqModAttrs.add(modAttr6);
                        }
                        RequestEntityAttribute modAttr7 = new RequestEntityAttribute("User Manager",mgrID,RequestEntityAttribute.TYPE.Long);
                    
                        reqModAttrs.add(modAttr1);
                        reqModAttrs.add(modAttr2);
                        reqModAttrs.add(modAttr3);
        //                reqModAttrs.add(modAttr4);
                        reqModAttrs.add(modAttr7);
                        
                        RequestEntity reqEntity = new RequestEntity();
                        reqEntity.setEntityData(reqModAttrs);
                        reqEntity.setRequestEntityType(OIMType.User);
                        reqEntity.setOperation(RequestConstants.MODEL_CREATE_OPERATION); // Specify DISABLE operation to perform
                        
                        List<RequestEntity>  entities = new ArrayList<RequestEntity>();
                        entities.add(reqEntity);
                        
                        RequestData reqData = new RequestData();
                        reqData.setTargetEntities(entities);
        
                        OIMService oimService = OIMClientFactory.getOIMService();
                        OperationResult operationsResult = oimService.doOperation(reqData, OIMService.Intent.REQUEST);
                        String RequestID = operationsResult.getRequestID();
                        
                        RequestService reqsrvc = OIMClientFactory.getRequestService();
                        Request request = reqsrvc.getBasicRequestData(RequestID);
                        String requestStatus = request.getRequestStatus();
                        System.out.println("RequestID "+RequestID);
        
                        FacesUtils.addFacesInformationMessage("Request ID - "+RequestID+". Request Status - "+requestStatus);
                     
                        String outcome = "Home";  
                        Class[] parms = new Class[] { ActionEvent.class };
                        FacesContext fctx1 = FacesContext.getCurrentInstance();
                        ELContext elctx = fctx1.getELContext();
                        Application app = fctx1.getApplication();
                        ExpressionFactory exprFactory = app.getExpressionFactory();
                        javax.el.MethodExpression expr =exprFactory.createMethodExpression(elctx, outcome,Object.class, parms);
                        ((RichCommandToolbarButton)actionEvent.getSource()).setActionExpression(expr);
                    
        
                        System.out.println("DONE!!..");           
                        System.out.println("User is created in OIM");
                    }
                    else
                    {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User Login "+userLogin+" already exist. Please use different Process / BOT ID", "User Login "+userLogin+" already exist. Please use different Process / BOT ID"));  
                    }
                
                    System.out.println("Disabled Header Component.!!..");            
            }
            
        }
        catch(Exception e)
        {
            System.out.println("ERROR MESSAGE: "+e.getMessage());
        }        
    }



}
