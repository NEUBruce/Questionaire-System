package com.sisp.common.utils;

import java.util.UUID;

public class UUIDUtil {
    /**
     * 获取一个UUID
     * @return
     */
    public static String getOneUUID() {
        String s = UUID.randomUUID().toString();
        // 去掉 -
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 返回指定数目的UUID
     * @param num
     * @return
     */
    public static String[] getUUIDs(int num) {
        if (num < 1) {
            return null;
        }else {
            String[] ss = new String[num];
            for (int i = 0; i < num; i++) {
                ss[i] = getOneUUID();

            }
            return ss;
        }
    }

}
