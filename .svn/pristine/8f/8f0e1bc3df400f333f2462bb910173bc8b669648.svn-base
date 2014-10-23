<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="com.iamnige.web.netdisk.model.LoginAndAuthModel" %>
<%@ page language="java" import="com.iamnige.web.netdisk.model.CommonSessionModel" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<%
LoginAndAuthModel authmodel = new LoginAndAuthModel();
if(!authmodel.isLogin(request)){
	response.sendRedirect("index.jsp");
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Welcome to Net Disk!</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="static/css/index.css">
	<script id="jquery_172" type="text/javascript" class="library" src="static/js/jquery-1.11.1.min.js"></script>
	<script>$(function(){
		$("input[type=file]").change(function(){$(this).parents(".uploader").find(".filename").val($(this).val());});
		$("input[type=file]").each(function(){
		if($(this).val()==""){$(this).parents(".uploader").find(".filename").val("No file selected...");}
		});
	});
	</script>
  </head>
  
  <body>
    <%
	CommonSessionModel model = new CommonSessionModel(request.getSession());
	String username = (String)model.get("username");
	%>
    <div class="menu">
	<ul>
		<li><a href="#">登出</a></li>
    	<li><a href="#"><% out.println(username); %></a></li>
    	<li style="float:left;color:#fff;">NetDisk</li>
    </ul>
	</div>
	<div class="title" style="padding-right: 17px;height:51px"> 
	<table>
    <tr>
    <td>
    <div class="uploader black">
      <form action="fileUpload.do" method="POST" enctype ="multipart/form-data">
      	<input type="text"   value="test" class="filename" style="width:218px"/> 
		<input type="file"   name="upload_file" />
		<input type="button" value="选择" class="button"/>
		<input type="submit" name="submit" value="上传..." class="button_all_radis" style="margin-left:5px; " id="sbt"/>
	  	
	  </form>
    </div>
    </td>
    <td><a style="display:inline-block; width:65px; height:28px; padding-left: 31px; line-height:26px;background: url(static/img/btn_icon.gif) 0 -416px no-repeat;">新建文件夹</a></td>
    </tr>
    </table>
    </div>
    <table style="margin-top: 10px">
    <tr>
    <td>全部文件</td>
    </tr>
    </table>
    
    
    <div class="title" style="padding-right: 17px;">        
    	<div class="item global-clearfix">
            <div  data-key="name" class="col c1" style="width: 60%">
                <span  class="chk">
                    <span class="chk-ico"></span>
                </span>
                <div class="name">
                    <span>文件名</span>
                    <span  class="asc" style="visibility: visible;"></span>
                </div>
            </div>      
            <div  data-key="size" class="col" style="width: 16%;
                ">
                大小
                <span class="asc desc" style="visibility: hidden;"></span>
            </div> 
            <div data-key="time" class="col" style="width: 23%;
                border-right: none;">
                修改日期
                <span class="asc desc" style="visibility: hidden;"></span>
            </div>   
        </div>
    </div>
    <div class="list" style="padding-right: 17px;">               
        <div  class="item global-clearfix">
            <!-- 第一列 -->
            <div class="col c1" style="width: 60%;">
                <span  class="chk">
                    <span class="chk-ico"></span>
                </span>               
                <span class="ico global-icon-16 global-icon-16-dir"></span>
                <!-- 文件名称 -->             
                <div  class="name" title="来自：iPhone">
                    
                    
                     <span  class="name-text enabled">来自：iPhone</span>
                    
                </div>
                <!-- 文件名称编辑 -->
                <div  class="edit-name">
                    <input  class="box" type="text" value="">
                    <span  class="sure"></span>
                    <span  class="cancel"></span>
                </div>
                <!-- 行内按钮 -->
                <div class="btns">                   
                    <a  data-key="share" class="btn share" href="javascript:void(0);"></a>
                    <a  data-key="download" class="btn download" href="javascript:void(0);"></a>
                    <span class="more-wrapper">
                        <span  class="btn more"></span>
                        <div  class="more-list" style="display: none;">
                            <a node-type="btn-item" data-key="move" class="more-item" href="javascript:void(0);">移动到</a>
                            <a node-type="btn-item" data-key="copy" class="more-item" href="javascript:void(0);">复制到</a>
                            <a node-type="btn-rename" class="more-item" href="javascript:void(0);">重命名</a>
                            <a node-type="btn-item" data-key="delete" class="more-item" href="javascript:void(0);">删除</a>
                        </div>
                    </span>
                 </div>
            </div>
			<!-- 其他列 -->
            <div class="col" style="width: 16%">
            </div>
            <div class="col" style="width: 23%; border-right: none;">
                2014-07-14 10:00
            </div>
            <!-- 复制 -->
         </div>
    </div>
  </body>
</html>
