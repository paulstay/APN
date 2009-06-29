<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.growcharity.toolbox.ClatTool" %>
<jsp:useBean id='clat' scope='session' class='com.growcharity.toolbox.ClatTool'/>
<jsp:useBean id='userBean' scope='session' class='com.growcharity.bean.UserBean'/>
<jsp:setProperty name='userBean' property='firstName' value='Clark'/>
<jsp:setProperty name='userBean' property='middleName' value='H.'/>
<jsp:setProperty name='userBean' property='lastName' value='Jones'/>
<jsp:setProperty name='userBean' property='spouse' value='Barbara'/>
<jsp:setProperty name='userBean' property='address1' value='42 Willow Lane'/>
<jsp:setProperty name='userBean' property='city' value='Walnut Creek'/>
<jsp:setProperty name='userBean' property='state' value='CA'/>
<jsp:setProperty name='userBean' property='zipcode' value='92015'/>
<jsp:setProperty name='clat' property='irsRate' value='.032'/>
<jsp:setProperty name='clat' property='fmv' value='1000000'/>
<jsp:setProperty name='clat' property='assetGrowth' value='.05'/>
<jsp:setProperty name='clat' property='assetIncome' value='.05'/>
<jsp:setProperty name='clat' property='calculationType' value='Z'/>
<jsp:setProperty name='clat' property='annuityIncrease' value='0'/>
<jsp:setProperty name='clat' property='taxRate' value='.433'/>
<jsp:setProperty name='clat' property='remainderInterest' value='0'/>
<jsp:setProperty name='clat' property='annuityPayment' value='0'/>
<jsp:setProperty name='clat' property='annuityRate' value='0'/>
<jsp:setProperty name='clat' property='optimalRate' value='0'/>
<jsp:setProperty name='clat' property='annuityFactor' value='0'/>
<jsp:setProperty name='clat' property='freq' value='1'/>
<jsp:setProperty name='clat' property='endBegin' value='0'/>
<jsp:setProperty name='clat' property='estateTaxRate' value='0'/>
<jsp:setProperty name='clat' property='finalDeath' value='25'/>
<jsp:forward page='user.jsp'/>






