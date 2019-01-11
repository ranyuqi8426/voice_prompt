package com.wsct.util.code;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuiwen on 2016/4/18.
 */
public class CodeMappingUtil {

    /**
     * 代码转名称：错误码
     * @param errorCode
     * @return
     */
    public static String showErrorMag(String errorCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("0", "未上报");
        map.put("1", "空文件");
        map.put("2", "警告");
        map.put("3", "格式错误");
        map.put("4", "逻辑错误");
        map.put("5", "警告+逻辑错误");
        map.put("6", "正确");

        return map.get(errorCode);
    }

    /**
     * 代码转名称：报表类型
     * @param kindCode
     * @return
     */
    public static String showReportKind(String kindCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("D", "日报");
        map.put("T", "旬报");
        map.put("M", "月报");

        return map.get(kindCode);
    }
}
