<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css">

<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body class="easyui-layout">   
    <div data-options="region:'north',title:'头部区域',split:true" style="height:100px;"></div>   
    <div data-options="region:'south',title:'底部区域',split:true" style="height:100px;"></div>   
    <div data-options="region:'east',iconCls:'icon-reload',title:'右边区域',split:true" style="width:100px;"></div>   
    <div data-options="region:'west',iconCls:'icon-save',title:'左边区域',split:true" style="width:100px;">
       <div id="aa" class="easyui-accordion" style="width: 100%">
           <div title="one" style="width: 100%" >
               <a id="but1" class="easyui-linkbutton">添加选项卡</a>
               <script type="text/javascript">
                  $(function(){
                	//页面加载完成后，为上面的按钮绑定事件
                  	$("#but1").click(function(){
                      	var e = $("#mytabs").tabs('exists','系统管理');
                      	if(e){
                      		$("#mytabs").tabs('select','系统管理');
                          	}else{
                          		 $("#mytabs").tabs("add",{
                                     title:'系统管理',
                                     iconCls:'icon-edit',
                                     closable:true,
                                     content:'<iframe frameborder="0" height="100%" width="100%" src="https://www.baidu.com"></iframe>'

                                     });
                              	}  
                      	});    	
                      });

               </script>
               
           </div>
           <div title="two" >
               <!-- 展示ztree效果 :使用标准json数据构造ztree-->
               <ul id="ztree1" class="ztree"></ul>
               <script type="text/javascript">
                   $(function(){
                	 //页面加载完成后，执行这段代码----动态创建ztree
                  	 var setting = {};
                  	 var zNodes = [
                      	 {"name":"jiedian1"},
                      	 {"name":"jiedian2"},
                      	 {"name":"jiedian3"}
                      	 ];
                  	 //调用API初始化ztree
                     $.fn.zTree.init($("#ztree1"), setting, zNodes);
                       });
                
               </script>
               
               
           </div>
           <div title="three" >
               <ul id="ztree2" class="ztree"></ul>
               <script type="text/javascript">
                  $(function(){
                	//页面加载完成后，执行这段代码----动态创建ztree
                  	var setting2 = {

                			data:{
                    			simpleData:{
                        			enable:true
                        			}
        			             }
                          	};
                  	var zNodes2 = [

                      	{"id":"1","pid":"0","name":"节点一"},
                      	{"id":"2","pid":"1","name":"节点2"},
                      	{"id":"3","pid":"1","name":"节点3"},
                      	{"id":"4","pid":"1","name":"节点4"}
                      	];
                  	$.fn.zTree.init($("#ztree2"),setting2,zNodes2);
                      });
               </script>
           </div>
           <div title="fore">
               <ul id="ztree3" class="ztree">
                
               </ul>
               <script type="text/javascript">
                  $(function(){
                	  var setting3 = {
								data: {
									simpleData: {
										enable: true//使用简单json数据构造ztree节点
									}
								},
								callback:{
									onClick:function(event,treeId,treeNode){
										

										if(treeNode.page != undefined){
											
											var e = $("#mytabs").tabs("exists",treeNode.name);
											
											if(e){
												$("#mytabs").tabs("select",treeNode.name);
												}else{
													
													$("#mytabs").tabs("add",{
														title:treeNode.name,
														closable:true,
														content:'<iframe frameborder="0" height="100%" width="100%" src="'+treeNode.page+'"></iframe>'
														});
													}
											}
									        
										}
								}
						};

                	  
                	//发送ajax请求，获取json数据
						//jQuery提供 的ajax方法：ajax、post、get、load、getJSON、getScript
              	  $.ajax({
                      	  url:"${pageContext.request.contextPath}/json/menu.json",
                      	
                      	 type:"POST",
                      	  success:function(data){
                          	
                      		$.fn.zTree.init($("#ztree3"),setting3,data);
                          	  },
                          	dataType:"json" 
                              	  
                      })
                  });
               </script>
           </div>
       </div>
    
    </div>   
    <div data-options="region:'center',title:'中心'" style="padding:5px;background:#eee;">
       <div id="mytabs" class="easyui-tabs" data-options="fit:true"></div>
    
    </div>   
</body>  
</html>  