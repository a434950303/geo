var title = "提示";
var btnMessage = "关闭";
var okBtnMsg = "确定";
var cancelBtnMsg = "取消";
/* 普通提示框无什么操作按钮，无法自动关闭 */
/**
 * 普通提示框无什么操作按钮，无法自动关闭
 * 
 * @param title
 *            弹出框title
 * @param message
 *            弹出框提示内容
 * @param btnMessage
 *            按钮提示内容
 */
function showDialog(param) {
	var dlg = null;
	try {
		dlg = new this.parent.window.BootstrapDialog.show({
			type : BootstrapDialog.TYPE_PRIMARY,
			size : BootstrapDialog.SIZE_SMALL,
			title : param.title == undefined ? title : param.title,
			closable : false,
			message : param.message,
		});
	} catch (e) {
		dlg = new BootstrapDialog.show({
			type : BootstrapDialog.TYPE_PRIMARY,
			size : BootstrapDialog.SIZE_SMALL,
			title : param.title == undefined ? title : param.title,
			closable : false,
			message : param.message,
		});
	}
	return dlg;
}
/**
 * 普通提示框无什么操作按钮，无法自动关闭
 * 
 * @param title
 *            弹出框title
 * @param message
 *            弹出框提示内容
 * @param callFn
 *            函数
 */
function showDialogOnShow(param) {
	var flag = "";
	dlg = new this.parent.window.BootstrapDialog.show({
		type : BootstrapDialog.TYPE_PRIMARY,
		size : BootstrapDialog.SIZE_SMALL,
		title : param.title == undefined ? title : param.title,
		closable : false,
		message : param.message,
		onshown : function(dialog) {
			if (param.saveParamFun && typeof param.saveParamFun == "function") {
				flag = param.saveParamFun();
				dialog.close();
			}
		},
		onhidden : function(dialog) {
			if (param.nextCallFun && typeof param.nextCallFun == "function") {
				param.nextCallFun(flag);
			}
		}
	});
	return dlg;
}

function showCurrDialog(param) {
	var dlg = new BootstrapDialog.show({
		type : BootstrapDialog.TYPE_PRIMARY,
		size : BootstrapDialog.SIZE_SMALL,
		title : param.title == undefined ? title : param.title,
		closable : false,
		cssClass : 'modal-top',
		message : param.message,
	});
	return dlg;
}
/* 普通提示框，比如：保存成功，保存失败 */
/**
 * 普通提示框，如：保存成功，删除失败
 * 
 * @param title
 *            弹出框title
 * @param message
 *            弹出框提示内容
 * @param btnMessage
 *            按钮提示内容
 * @param callFn
 *            完成后的回调函数
 */
function showSampleDialog(param) {
	var dlg = null;
	try {
		dlg = this.parent.window.BootstrapDialog.show({
			type : BootstrapDialog.TYPE_PRIMARY,
			size : BootstrapDialog.SIZE_SMALL,
			title : param.title == undefined ? title : param.title,
			message : param.message,
			buttons : [ {
				label : param.btnMessage == undefined ? btnMessage
						: param.btnMessage,
				cssClass : 'btn-primary',
				action : function(dialogItself) {
					dialogItself.close();
					if (param.callFn && typeof param.callFn == "function") {
						param.callFn();
					}
				}
			} ]
		});
	} catch (e) {
		dlg = BootstrapDialog.show({
			type : BootstrapDialog.TYPE_PRIMARY,
			size : BootstrapDialog.SIZE_SMALL,
			title : param.title == undefined ? title : param.title,
			message : param.message,
			buttons : [ {
				label : param.btnMessage == undefined ? btnMessage
						: param.btnMessage,
				cssClass : 'btn-primary',
				action : function(dialogItself) {
					dialogItself.close();
					if (param.callFn && typeof param.callFn == "function") {
						param.callFn();
					}
				}
			} ]
		});
	}
	return dlg;
}

/**
 * 操作前提示框，如：删除前提示是否删除
 * 
 * @param title
 *            弹出框title
 * @param message
 *            弹出框提示内容
 * @param okFn
 *            确认之后的函数
 * @param cancelFn
 *            取消之后的函数
 */
function showIfDelDialog(param) {
	try {
		this.parent.window.BootstrapDialog.show({
			type : BootstrapDialog.TYPE_PRIMARY,
			size : BootstrapDialog.SIZE_SMALL,
			title : param.title == undefined ? title : param.title,
			message : param.message,
			buttons : [
					{
						label : param.okBtnMsg == undefined ? okBtnMsg
								: param.okBtnMsg,
						cssClass : 'btn-primary',
						action : function(dialogItself) {
							dialogItself.close();
							if (param.okFn && typeof param.okFn == "function") {
								param.okFn();
							}
						}
					},
					{
						label : param.cancelBtnMsg == undefined ? cancelBtnMsg
								: param.cancelBtnMsg,
						cssClass : 'btn',
						action : function(dialogItself) {
							dialogItself.close();
							if (param.cancelFn
									&& typeof param.cancelFn == "function") {
								param.cancelFn();
							}
						}
					} ]
		});
	} catch (e) {
		BootstrapDialog.show({
			type : BootstrapDialog.TYPE_PRIMARY,
			size : BootstrapDialog.SIZE_SMALL,
			title : param.title == undefined ? title : param.title,
			message : param.message,
			buttons : [
					{
						label : param.okBtnMsg == undefined ? okBtnMsg
								: param.okBtnMsg,
						cssClass : 'btn-primary',
						action : function(dialogItself) {
							dialogItself.close();
							if (param.okFn && typeof param.okFn == "function") {
								param.okFn();
							}
						}
					},
					{
						label : param.cancelBtnMsg == undefined ? cancelBtnMsg
								: param.cancelBtnMsg,
						cssClass : 'btn',
						action : function(dialogItself) {
							dialogItself.close();
							if (param.cancelFn
									&& typeof param.cancelFn == "function") {
								param.cancelFn();
							}
						}
					} ]
		});
	}
}

function showTipDialog(param) {
	var dlg = BootstrapDialog.show({
		type : BootstrapDialog.TYPE_PRIMARY,
		size : BootstrapDialog.SIZE_SMALL,
		title : param.title == undefined ? title : param.title,
		message : param.message,
		buttons : [ {
			label : param.btnMessage == undefined ? btnMessage
					: param.btnMessage,
			cssClass : 'btn-primary',
			action : function(dialogItself) {
				dialogItself.close();
				if (param.callFn && typeof param.callFn == "function") {
					param.callFn();
				}
			}
		} ]
	});
	return dlg;
}