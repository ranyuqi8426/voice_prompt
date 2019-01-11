package com.wsct.util.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsct.mqsave.model.flightData;

public class JsonUtil {
	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 把对象转成json串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toStr(Object obj) {
		String json_str = "";
		try {
			json_str = objectMapper.writer().writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json_str;
	}

	/**
	 * 把字符串转成List，统一保存时使用
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<LinkedHashMap<String, Object>> toList(String str) {
		List<LinkedHashMap<String, Object>> list = null;
		try {
			list = objectMapper.readValue(str, List.class);// 把list对象转成LinkedhashMap,然后根据HashMap来取值
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 字符串返回一个对象，{\"username\":\"张三\"}返回User对象
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object toObject(Class clazz, String str) {
		Object object = null;
		try {
			object = objectMapper.readValue(str, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 生成操作后的json串，{success:false,msgText:'删除失败'}
	 * 
	 * @param flag
	 * @param msg
	 * @return
	 */
	public static String createOperaStr(boolean flag, String msg) {
		return "{\"success\":" + flag + ",\"msgText\":\"" + msg + "\"}";
	}

	/**
	 * 生成操作后的json串
	 * 
	 * @param flag 结果类型 
	 * @param data 结果数据
	 * @param msg 错误提示
	 * @return
	 */
	public static String createOperTypeStr(Integer flag,String result,String msg) {
		return "{flag:" + flag + ",data:"+result+",msgText:'" + msg + "'}";
	}
	
	/**
	 * 创建Extjs分页json
	 * 
	 * @param rows
	 * @param data
	 * @return
	 */
	public static String createExtjsPageJson(long rows, String data) {
		String json = "{\"total\":\"" + rows + "\",\"rows\":" + data + "}";
		return json;
	}

	/**
	 * 创建Extjs分页json
	 * 
	 * @param rows
	 * @param data
	 * @return
	 */
	public static String createExtjsPageJson(long rows, Object obj) {
		String json = "{\"total\":\"" + rows + "\",\"root\":" + toStr(obj) + "}";
		return json;
	}

	public static String createEasyUIPageJson(long rows, String data) {
		String json = "{\"total\":\"" + rows + "\",\"rows\":" + data + "}";
		return json;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		String s = "{\"id\":\"0007dff007ff497493735136b40832ea\",\"billNumber\":\"A02448808\",\"billType\":\"0\",\"isValid\":true,\"version\":\"0\",\"billKind\":null,\"generatedDate\":\"2018-10-12\",\"flseq\":\"bd1fbc112cd34554aa293ff290e63933\",\"flop\":\"20181012\",\"flno\":\"CA 4504\",\"adid\":\"D\",\"regnumber\":\"B5978\",\"placecode\":\"91\",\"ac3c\":\"333\",\"acname\":\"\",\"org3\":\"PVG\",\"des3\":\"CTU\",\"al2c\":\"CA\",\"alcname\":\"中国国际航空公司\",\"flti\":\"D\",\"stopOver\":null,\"tenant\":\"OIL_PVG\",\"taskId\":\"51140323\",\"taskStartTime\":\"2018-10-12 11:13:00\",\"taskEndTime\":\"2018-10-12 11:28:00\",\"oll\":null,\"assaySheetNumber\":\"2018-656\",\"oilTemperature\":\"22.8\",\"oilDensity\":\"0.7887\",\"volume\":\"25228\",\"weight\":\"19897\",\"vehicleType\":\"VEHOILPIPE\",\"vehicleNumber\":\"609\",\"wellNumber\":\"91.1\",\"fuleMan1\":\"劳安彬\",\"fuleMan2\":null,\"fuleMan3\":null,\"fuleMan4\":null,\"fuleManCount\":\"1\",\"isConfirm\":false,\"confirmPerson\":null,\"confirmTime\":null,\"isAudited\":false,\"auditPerson\":null,\"auditTime\":null,\"isDelete\":false,\"isUploaded\":false,\"taxType\":null,\"remark\":null,\"modifyPerson\":null,\"modifyTime\":null,\"createPerson\":\"20163\",\"createTime\":\"2018-10-12 11:32:51\",\"gasorpumptype\":\"JY\"}";
		JSONObject jsStr = JSONObject.parseObject(s);
		flightData stud = (flightData) JSONObject.parseObject(s, flightData.class);
		System.out.println();
	}

}
