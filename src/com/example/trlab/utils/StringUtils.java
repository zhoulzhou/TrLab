package com.example.trlab.utils;

public class StringUtils{

    public static boolean isNullOrEmpty(String str) {
        return (null == str) || ("".equals(str.trim()));
    }

    public static boolean isNotEmpty(String str) {
        return (str != null && !str.trim().equals(""));
    }

    static public String formateSize(long size) {
        String preSize = "";
        String subSize = "B";
        if (size > 1024 && size < 1024 * 1024) {
            preSize = (float) size / 1024 + "";
            subSize = "KB";
        } else if (size >= 1024 * 1024) {
            preSize = (float) size / (1024 * 1024) + "";
            subSize = "M";
        } else {
            preSize = size + "";
            subSize = "B";
        }
        int index = preSize.indexOf(".");
        if (index > 0 && index < preSize.length()) {
            return preSize.subSequence(0, index) + subSize;
        } else {
            return preSize + subSize;
        }

    }

    static public String formateSizeInKByte(long size) {
        String preSize = "";
        String subSize = "B";
        if (size >= 1024) {
            preSize = (float) size / 1024 + "";
            subSize = "MB";
        } else {
            preSize = size + "";
            subSize = "KB";
        }
        int index = preSize.indexOf(".");
        if (index > 0 && index < preSize.length()) {
            return preSize.subSequence(0, index) + subSize;
        } else {
            return preSize + subSize;
        }
    }

    static public String formateDownloadCount(String countStr) {
        String result = "";
        String subfix = "";
        try {
            long count = Long.parseLong(countStr);
            if (count > 100000000) {
                result = (float) count / 100000000.0f + "";
                subfix = "亿";
            } else if (count > 10000) {
                result = (float) count / 10000.0f + "";
                subfix = "万";
            } else {
                result = count + "";
            }
            int index = result.indexOf(".");
            if (index > 0) {
                if (subfix.equals("亿")) {
                    index = index + 2;
                }
                result = result.substring(0, index);
            }
        } catch (Exception e) {
            result = "0";
        }
        return result + subfix;
    }

    static public String formatePrasiseCount(int count) {
        String result = "";
        String subfix = "";
        if (count > 100000000) {
            result = (float) count / 100000000.0f + "";
            subfix = "亿";
        } else if (count > 10000000) {
            result = (float) count / 10000000.0f + "";
            subfix = "千万";
        } else if (count > 10000) {
            result = (float) count / 10000.0f + "";
            subfix = "万";
        } else {
            result = count + "";
        }
        int index = result.indexOf(".");
        if (index > 0) {
            if (subfix.equals("亿")) {
                index = index + 2;
            }
            result = result.substring(0, index);
        }
        return result + subfix;
    }

}