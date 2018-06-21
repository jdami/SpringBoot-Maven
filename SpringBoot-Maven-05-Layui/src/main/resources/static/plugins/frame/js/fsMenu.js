/**
 * @Description: 菜单配置
 * @Copyright: 2017 www.fallsea.com Inc. All rights reserved.
 * @author: fallsea
 * @version 1.8.2
 * @License：MIT
 */
layui.define(['element',"fsConfig","fsCommon"], function(exports){

	var menuConfig = {
			dataType : "local" , //获取数据方式，local本地获取，server 服务端获取
			loadUrl : "", //加载数据地址
			method : "post",//请求类型，默认post
			rootMenuId : "0", //根目录菜单id
			defaultSelectTopMenuId : "1", //默认选中头部菜单id
			defaultSelectLeftMenuId : "111", //默认选中左边菜单id
			menuIdField : "menuId", //菜单id
			menuNameField : "menuName", //菜单名称
			menuIconField : "menuIcon" , //菜单图标，图标必须用css
			menuHrefField : "menuHref" , //菜单链接
			parentMenuIdField : "parentMenuId" ,//父菜单id
			data : [
				{"menuId":"1","menuName":"控制台",	"menuIcon":"fa-cog",	"menuHref":"","parentMenuId":"0"},
				/*{"menuId":"2","menuName":"测试",		"menuIcon":"",			"menuHref":"","parentMenuId":"0"},*/
				
				{"menuId":"90","menuName":"客户管理",	"menuIcon":"fa-table",	"menuHref":"","parentMenuId":"1"},
				{"menuId":"901","menuName":"客户信息管理","menuIcon":"fa-list","menuHref":"views/customer/baseCustomer.html","parentMenuId":"90"},
				{"menuId":"902","menuName":"客户账户管理","menuIcon":"fa-list","menuHref":"views/customer/accAccount.html","parentMenuId":"90"},
				{"menuId":"903","menuName":"客户钱包管理","menuIcon":"fa-list","menuHref":"views/customer/customerWallet.html","parentMenuId":"90"},
				{"menuId":"904","menuName":"客户账户流水管理","menuIcon":"fa-list","menuHref":"views/customer/accAccountBill.html","parentMenuId":"90"},
				{"menuId":"905","menuName":"客户账户冻结管理","menuIcon":"fa-list","menuHref":"views/customer/accAccountFreeze.html","parentMenuId":"90"},
				
				{"menuId":"80","menuName":"商户管理",	"menuIcon":"fa-table",	"menuHref":"","parentMenuId":"1"},
				{"menuId":"801","menuName":"商户管理","menuIcon":"fa-list","menuHref":"views/merchant/baseMerchant.html","parentMenuId":"80"},
				
				{"menuId":"70","menuName":"运营管理",	"menuIcon":"fa-table",	"menuHref":"","parentMenuId":"1"},
				{"menuId":"701","menuName":"商户信息管理","menuIcon":"fa-list","menuHref":"views/business/baseMerchant.html","parentMenuId":"70"},
				{"menuId":"702","menuName":"商户白名单信息管理","menuIcon":"fa-list","menuHref":"views/business/baseMerchantWhitelist.html","parentMenuId":"70"},
				{"menuId":"703","menuName":"商户合约账户管理","menuIcon":"fa-list","menuHref":"views/business/accContract.html","parentMenuId":"70"},
				{"menuId":"704","menuName":"合约账户流水管理","menuIcon":"fa-list","menuHref":"views/business/accContractBill.html","parentMenuId":"70"},
				{"menuId":"705","menuName":"补充能量流水管理","menuIcon":"fa-list","menuHref":"views/business/tradeRechargeGas.html","parentMenuId":"70"},
				{"menuId":"706","menuName":"交易请求流水管理","menuIcon":"fa-list","menuHref":"views/business/tradeRequest.html","parentMenuId":"70"},
				{"menuId":"707","menuName":"交易序列错误池管理","menuIcon":"fa-list","menuHref":"views/business/blockSequenceErrorPool.html","parentMenuId":"70"},
				{"menuId":"708","menuName":"客户端管理","menuIcon":"fa-list","menuHref":"views/business/baseMachine.html","parentMenuId":"70"},
				
				{"menuId":"40","menuName":"交易管理",	"menuIcon":"fa-table",	"menuHref":"","parentMenuId":"1"},
				{"menuId":"401","menuName":"提币流水管理","menuIcon":"fa-list","menuHref":"views/trade/tradeTokenWithdraw.html","parentMenuId":"40"},
				{"menuId":"402","menuName":"转账流水管理","menuIcon":"fa-list","menuHref":"views/trade/tradeTokenTransfer.html","parentMenuId":"40"},
				{"menuId":"403","menuName":"代币调账流水管理","menuIcon":"fa-list","menuHref":"views/trade/tradeTokenAdjust.html","parentMenuId":"40"},
				
				{"menuId":"60","menuName":"基础信息管理",	"menuIcon":"fa-table",	"menuHref":"","parentMenuId":"1"},
				{"menuId":"601","menuName":"旷工账户管理","menuIcon":"fa-list","menuHref":"views/base/accMiner.html","parentMenuId":"60"},
				{"menuId":"602","menuName":"字典管理","menuIcon":"fa-list","menuHref":"views/base/baseDictionary.html","parentMenuId":"60"},
				
				{"menuId":"50","menuName":"系统用户管理",	"menuIcon":"fa-table",	"menuHref":"","parentMenuId":"1"},
				{"menuId":"501","menuName":"用户信息管理","menuIcon":"fa-list","menuHref":"views/system/baseUser.html","parentMenuId":"50"},
				{"menuId":"502","menuName":"操作历史管理","menuIcon":"fa-list","menuHref":"views/system/logOperationHistory.html","parentMenuId":"50"}
		 ] //本地数据
	};

	var element = layui.element,
	fsCommon = layui.fsCommon,
	fsConfig = layui.fsConfig,
	statusName = $.result(fsConfig,"global.result.statusName","errorNo"),
	msgName = $.result(fsConfig,"global.result.msgName","errorInfo"),
	successNo = $.result(fsConfig,"global.result.successNo","0"),
	dataName = $.result(fsConfig,"global.result.dataName","results.data"),
	
	FsMenu = function (){

	};


	FsMenu.prototype.render = function(){
		this.loadData();
		this.showMenu();
	};

	/**
	 * 加载数据
	 */
	FsMenu.prototype.loadData = function(){
		if(menuConfig.dataType == "server"){//服务端拉取数据
			var url = menuConfig.loadUrl;
			if($.isEmpty(url)){
				fsCommon.errorMsg("未配置请求地址！");
				return;
			}
			fsCommon.invoke(url,{},function(data){
				if(data[statusName] == successNo) {
					menuConfig.data = $.result(data,dataName);
				} else {
					//提示错误消息
					fsCommon.errorMsg(data[msgName]);
				}
			},false,menuConfig.method);
		}
	}


	/**
	 * 获取图标
	 */
	FsMenu.prototype.getIcon = function(menuIcon){
		if(!$.isEmpty(menuIcon)){
			if(menuIcon.indexOf("<i") == 0){
				return menuIcon;
			}else if (menuIcon.indexOf("&#") == 0){
				return '<i class="layui-icon">'+menuIcon+'</i>';
			}else if (menuIcon.indexOf("fa-") == 0){
				return '<i class="fa '+menuIcon+'"></i>';
			}else {
				return '<i class="'+menuIcon+'"></i>';
			}
		}
		return "";
	};

	/**
	 * 清空菜单
	 */
	FsMenu.prototype.cleanMenu = function(){
		$("#fsTopMenu").html("");
		$("#fsLeftMenu").html("");
	}
	/**
	 * 显示菜单
	 */
	FsMenu.prototype.showMenu = function(){
		var thisMenu = this;
		var data = menuConfig.data;
		if(!$.isEmpty(data)){
			var _index = 0;
			//显示顶部一级菜单
			var fsTopMenuElem = $("#fsTopMenu");
			var fsLeftMenu = $("#fsLeftMenu");
			$.each(data,function(i,v){
				if(menuConfig.rootMenuId === v[menuConfig.parentMenuIdField]){

					var topStr = '<li class="layui-nav-item';
					if($.isEmpty(menuConfig.defaultSelectTopMenuId) && _index === 0){//为空默认选中第一个
						topStr += ' layui-this';
					}else if(!$.isEmpty(menuConfig.defaultSelectTopMenuId) && menuConfig.defaultSelectTopMenuId == v[menuConfig.menuIdField]){//默认选中处理
						topStr += ' layui-this';
					}
					_index ++ ;
					topStr += '" dataPid="'+v[menuConfig.menuIdField]+'"><a href="javascript:;">'+thisMenu.getIcon(v[menuConfig.menuIconField])+' <cite>'+v[menuConfig.menuNameField]+'</cite></a></li>';
					fsTopMenuElem.append(topStr);

					//显示二级菜单，循环判断是否有子栏目
					$.each(data,function(i2,v2){
						if(v[menuConfig.menuIdField] === v2[menuConfig.parentMenuIdField]){

							var menuRow = '<li class="layui-nav-item';
							if(!$.isEmpty(menuConfig.defaultSelectLeftMenuId) && menuConfig.defaultSelectLeftMenuId == v2[menuConfig.menuIdField]){//默认选中处理
								menuRow += ' layui-this';
							}
							//显示三级菜单，循环判断是否有子栏目
							var menuRow3 = "";
							$.each(data,function(i3,v3){
								if(v2[menuConfig.menuIdField] === v3[menuConfig.parentMenuIdField]){
									if($.isEmpty(menuRow3)){
										menuRow3 = '<dl class="layui-nav-child">';
									}
									menuRow3 += '<dd';
									if(!$.isEmpty(menuConfig.defaultSelectLeftMenuId) && menuConfig.defaultSelectLeftMenuId == v3[menuConfig.menuIdField]){//默认选中处理
										menuRow3 += ' class="layui-this"';
										menuRow += ' layui-nav-itemed';//默认展开二级菜单
									}
									menuRow3 += ' lay-id="'+v3[menuConfig.menuIdField]+'"><a href="javascript:;" menuId="'+v3[menuConfig.menuIdField]+'" dataUrl="'+v3[menuConfig.menuHrefField]+'">'+thisMenu.getIcon(v3[menuConfig.menuIconField])+' <cite>'+v3[menuConfig.menuNameField]+'</cite></a></dd>';
								}
							});

							menuRow += '" lay-id="'+v2[menuConfig.menuIdField]+'" dataPid="'+v2[menuConfig.parentMenuIdField]+'" style="display: none;"><a href="javascript:;" menuId="'+v2[menuConfig.menuIdField]+'" dataUrl="'+v2[menuConfig.menuHrefField]+'">'+thisMenu.getIcon(v2[menuConfig.menuIconField])+' <cite>'+v2[menuConfig.menuNameField]+'</cite></a>';
							if(!$.isEmpty(menuRow3)){
								menuRow3 += '</dl>';
								menuRow += menuRow3;
							}
							menuRow += '</li>';
							fsLeftMenu.append(menuRow);
						}
					});
				}
			});
		}
		element.render("nav");
	};

	var fsMenu = new FsMenu();
	exports("fsMenu",fsMenu);
});