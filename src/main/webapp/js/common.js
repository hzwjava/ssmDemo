/**
 * 将日期的毫秒数val格式化成yyyy-MM-dd的日期格式并返回
 * @param String 毫秒数
 * @return String
 */
function converDate(val){
	if(!isNaN(val)){
		var newDate = new Date(parseInt(val));
		return newDate.format("yyyy-MM-dd");
	}else{
		return "";
	}
}

/**
 * 将日期的毫秒数val格式化成yyyy-MM-dd hh:mm:ss的日期格式并返回
 * @param String 毫秒数
 * @return String
 */
function converTime(val){
	if(!isNaN(val)){
		var newDate = new Date(parseInt(val));
		return newDate.format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "";
	}
}

Date.prototype.format = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
	} 
	
	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 
	
	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format; 
}