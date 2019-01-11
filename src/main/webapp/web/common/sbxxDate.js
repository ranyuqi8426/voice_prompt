var s_bbzl = Ext.create('Ext.form.ComboBox', {
	width : 80,
	id : 's_bbzl',
	store : new Ext.create('Ext.data.Store', {
		model : 'ComboboxData',
		data : [ {
			value : 'M',
			content : '月报'
		}, {
			value : 'Q',
			content : '季报'
		}, {
			value : 'Y',
			content : '年报'
		} ]
	}),
	displayField : 'content',
	valueField : 'value',
	typeAhead : false,
	queryMode : 'local',
	editable : false,
	listConfig : {
		getInnerTpl : function() {
			return '{value}:{content}';
		}
	},
	blankText : '报表种类',
	value : 'M',
	listeners : {
		change : function() {
			if (s_bbzl.getValue() == 'M') {
				d_month.show();
				d_quarter.hide();
			} else if (s_bbzl.getValue() == 'Q') {
				d_month.hide();
				d_quarter.show();
			} else if (s_bbzl.getValue() == 'Y') {
				d_month.hide();
				d_quarter.hide();
			}
		}
	}
})
// 年
var curDay = Ext.util.Format.date(new Date(), 'Y-m-d')
var curYear = curDay.substring(0, 4);
var curMonth = curDay.substring(5, 7);
var curQuarter = "";
if (curMonth == '01' || curMonth == '02' || curMonth == '03')
	curQuarter = 'Q1';
if (curMonth == '04' || curMonth == '05' || curMonth == '06')
	curQuarter = 'Q1';
if (curMonth == '07' || curMonth == '08' || curMonth == '09')
	curQuarter = 'Q1';
if (curMonth == '10' || curMonth == '11' || curMonth == '12')
	curQuarter = 'Q1';
var years = [];
var yfrom = curYear - 4;
while (yfrom <= curYear * 1 + 1) {
	years.push([ yfrom, yfrom + '年' ]);
	yfrom++;
}
var d_year = Ext.create('Ext.form.ComboBox', {
	width : 80,
	id : 'd_year',
	store : new Ext.create("Ext.data.ArrayStore", {
		fields : [ 'year', 'yearC' ],
		data : years
	}),
	displayField : 'yearC',
	valueField : 'year',
	typeAhead : false,
	queryMode : 'local',
	editable : false,
	blankText : '年份',
	value : new Date().getFullYear()
})
// 月
var d_month = Ext.create('Ext.form.ComboBox', {
	width : 80,
	id : 'd_month',
	store : new Ext.create('Ext.data.Store', {
		model : 'ComboboxData',
		data : [ {
			value : '01',
			content : '1月'
		}, {
			value : '02',
			content : '2月'
		}, {
			value : '03',
			content : '3月'
		}, {
			value : '04',
			content : '4月'
		}, {
			value : '05',
			content : '5月'
		}, {
			value : '06',
			content : '6月'
		}, {
			value : '07',
			content : '7月'
		}, {
			value : '08',
			content : '8月'
		}, {
			value : '09',
			content : '9月'
		}, {
			value : '10',
			content : '10月'
		}, {
			value : '11',
			content : '11月'
		}, {
			value : '12',
			content : '12月'
		} ]
	}),
	displayField : 'content',
	valueField : 'value',
	typeAhead : false,
	queryMode : 'local',
	editable : false,
	blankText : '月份',
	value : curMonth
})
// 季
var d_quarter = Ext.create('Ext.form.ComboBox', {
	width : 80,
	id : 'd_quarter',
	hidden : true,
	store : new Ext.create('Ext.data.Store', {
		model : 'ComboboxData',
		data : [ {
			value : 'Q1',
			content : '一季度'
		}, {
			value : 'Q2',
			content : '二季度'
		}, {
			value : 'Q3',
			content : '三季度'
		}, {
			value : 'Q4',
			content : '四季度'
		} ]
	}),
	displayField : 'content',
	valueField : 'value',
	typeAhead : false,
	queryMode : 'local',
	editable : false,
	blankText : '季度',
	value : curQuarter
})

function genDate() {
	var str = "";
	if (s_bbzl.getValue() == 'M') {
		str = d_year.getValue() + d_month.getValue();
	} else if (s_bbzl.getValue() == 'Q') {
		str = d_year.getValue() + d_quarter.getValue();
	} else if (s_bbzl.getValue() == 'Y') {
		str = d_year.getValue();
	}
	return str;
}
