package com.wsct.util.global;

/**
 * 单位类型
 *  900809	局机关
	900808	合资公司
	900811	专业公司
	900812	直属站
	900804	车务段
	900813	货运中心
	900801	机务段
	900803	工务段
	900806	电务段
	900802	车辆段
	900805	动车段
	900815	供电段
	900816	客运段
	900817	工务机械段
	900818	通信段
	900819	工电大修段
	900820    机车检修段
	900821    动车检修段
	900807	公寓段
	900810	其他
	900890	总公司
 * @author 孔垂云
 * @date 2016年12月15日
 */
public enum EnumGgDwlx {
	JJG("局机关", "900809"),
	HZGS("合资公司", "900808"), 
	ZYGS("专业公司", "900811"), 
	ZSZ("直属站", "900812"), 
	CWD("车务段","900804"), 
	HYZX("货运中心", "900813"),
	JWD("机务段", "900801"), 
	GWD("工务段", "900803"),
	DWD("电务段","900806"),
	CLD("车辆段", "900802"),
	KCD("客车段","900822"),
	HCD("货车段", "900814"),
	DCD("动车段", "900805"),
	GDD("供电段","900815"),
	KYD("客运段", "900816"), 
	GWJXD("工务机械段", "900817"), 
	GDDXD("工电大修段", "900819"), 
	JCJXD("机车检修段","900820"),
	DCJXD("动车检修段","900821"),
	TXD("通信段","900818"),
	GYD("公寓段", "900807"), 
	QT("其他", "900810"), 
	ZGS("总公司", "900890"), 

	;
	// 成员变量
	private String name;// 名称
	private String code;// 代码

	// 构造方法
	private EnumGgDwlx(String name, String code) {
		this.name = name;
		this.code = code;
	}

	// 覆盖方法
	@Override
	public String toString() {
		return this.code + "_" + this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
