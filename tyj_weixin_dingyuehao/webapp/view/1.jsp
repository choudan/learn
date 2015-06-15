<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%String openid=request.getParameter("openid");
%>
<head>
<meta http-equiv="refresh" content="0;url=http://182.92.224.124/tyj_weixin_dingyuehao/api/get_openid?openid=<%=openid%>">
</head>
