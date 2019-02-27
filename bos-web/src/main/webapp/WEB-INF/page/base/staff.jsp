<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function doAdd(){
		//alert("增加...");
		$('#addStaffWindow').window("open");
	}
	
	function doView(){
		alert("查看...");
	}
	
	function doDelete(){
		var selects = $("#grid").datagrid("getSelections");
		
		if(selects.length == 0){
			$.messager.alert('警告','请至少选择一条记录'); 
			}else{
				$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
				    if (r){    
				    	var arrar = new Array();
						for(var i = 0 ;i < selects.length;i++){
							arrar.push(selects[i]["id"]);
							}
						var ids = arrar.join(",");
						$.ajax({

							url:"staffAction_deleteStaff.action",
							type:"post",
							data:{"ids":ids},
							success:function(data){

								
								if(data["success"]){
									$.messager.alert('提示','删除记录成功！');
									}else{
										$.messager.alert('提示','删除记录失败！');
										}
								// 重新加载数据
								$("#grid").datagrid("reload");
								},
								dataType:"json"
							}); 
				    }    
				});  
				
			
				}
		
	}
	
	function doRestore(){
		alert("将取派员还原...");
	}
	//工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView  // 绑定单击事件
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '作废',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-save',
		handler : doRestore
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center'
	}, {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "有";
			}else{
				return "无";
			}
		}
	}, {
		field : 'deltag',
		title : '是否作废',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "正常使用"
			}else{
				return "已作废";
			}
		}
	}, {
		field : 'standard',
		title : '取派标准',
		width : 120,
		align : 'center'
	}, {
		field : 'station',
		title : '所谓单位',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		  $('#editStaffWindow').window('close');
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 取派员信息表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [10,20,50],
			pagination : true,
			toolbar : toolbar,
			url : "staffAction_findStaff.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加取派员窗口
		$('#addStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });

		$('#editStaffWindow').window({
	        title: '修改取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
	});

	function doDblClickRow(rowIndex, rowData){

		//修改窗口数据获取
		$("#staffFormedit").form("load",
				{
			id:rowData["id"],
			name:rowData["name"],
			telephone:rowData["telephone"],
			deltag:rowData["deltag"],
			haspda:rowData["haspda"],
			station:rowData["station"],
			standard:rowData["standard"]

			}

				);
		$("#editStaffWindow").window("open");
	}
    $(function(){
    	<!--修改菜单提交表单-->
    	$("#edit").click(function(){
        
        	var e = $("#staffFormedit").form("validate");
        
        	if(e){
        		$("#staffFormedit").form("submit",{
                		success:function(data){

                			var data = eval('(' + data + ')');  // change the JSON string to javascript object 
                			if(data.success){

                			       $.messager.alert("修改取派员","修改成功");
                    			}else{$.messager.alert("修改取派员","修改失败");}
                			$("#grid").datagrid("reload");
                			$("#editStaffWindow").window("close");
                    		}
        		     
        		});

            	}
        	});

    	<!--手机号检验 -->
        var reg = /^1[3|4|5|7|8|9][0-9]{9}$/;
        $.extend($.fn.validatebox.defaults.rules, {    
        mobilephone: {    
            validator: function(value,param){    
                return reg.test(value);
            },    
            message: '手机号输入错误，请重新输入'   
        }    
    });  
        });



	
	
    $(function(){
    	<!--提交表单-->
    	$("#save").click(function(){
        
        	var e = $("#staffForm").form("validate");
        
        	if(e){
        		$("#staffForm").submit();  

            	}
        	});

    	<!--手机号检验 -->
        var reg = /^1[3|4|5|7|8|9][0-9]{9}$/;
        $.extend($.fn.validatebox.defaults.rules, {    
        mobilephone: {    
            validator: function(value,param){    
                return reg.test(value);
            },    
            message: '手机号输入错误，请重新输入'   
        }    
    });  
        });
	
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="对收派员进行添加或者修改" id="addStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="javascript:void(0);" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="staffForm" action="staffAction_saveStaff.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
				
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" data-options="validType:'mobilephone'" name="telephone" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>是否报废：</td>
						<td> 否<input type="radio" name="deltag" class="easyui-validatebox"  value="1"/>  
						       是<input type="radio" name="deltag" class="easyui-validatebox"  value="0">
						
						</td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
		<div class="easyui-window" title="收派员修改" id="editStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
	
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="javascript:void(0);" class="easyui-linkbutton" plain="true" >修改</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="staffFormedit" action="staffAction_editStaff.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
				       <input type="hidden" name="id"/>
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" data-options="validType:'mobilephone'" name="telephone" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>是否报废：</td>
						<td> 否<input type="radio" name="deltag" class="easyui-validatebox"  value="1"/>  
						       是<input type="radio" name="deltag" class="easyui-validatebox"  value="0">
						
						</td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
</body>
</html>	