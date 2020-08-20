package com.example.batchoperation.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供些常用的字符串相关的工具方法
 */
public final class StringUtil {
	public static boolean isAllNumberic(String str,int length){
		  Pattern pattern =  Pattern.compile("[0-9]{"+length+"}");
		  return pattern.matcher(str).matches();
	}
	
	public static boolean isInStringArray(String[] arr, String targetValue) {
		if(ValidUtil.valid(arr)){
		  for (String s : arr) {
	            if (s.equals(targetValue))
	                return true;
	        }
		}
        return false;
    }
	
	public static boolean isAllInStringArray(String[] arr, String[] target) {
		Boolean flag=false;
		if(ValidUtil.valid(arr) && ValidUtil.valid(target)){
		   for (String s : target) {
	        	flag=false;
	        	for(String s1:arr){
	        		if(s.equals(s1)){
	        			flag=true;
	        			break;
	        		}
	        	}
	        	if(!flag){
	        		return false;
	        	}
	        }
		}
        return flag;
    }

    /**
     * 判断是否是空字符串 null和"" 都返回 true
     *
     * @param str 判断的字符串
     * @return 是否有效
     */
    public  static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }
    
    /**
     * 判断是否是空字符串 null、""和"     " 都返回 true
     *
     * @param str 判断的字符串
     * @return 是否有效
     */
    public static boolean isBlank(String str)
    {
        int strLen;
        if  (str == null || (strLen = str.length()) == 0)
        {
            return true;
        }
        for  (int i = 0; i < strLen; i++)
        {
            if  ((Character.isWhitespace(str.charAt(i)) == false))
            {
                return false;
            }
        }
        return true;
    }


    /**
     * 判定第一个字符串是否等于的第二个字符串中的某一个值
     *
     * @param str1 测试的字符串
     * @param str2 字符串数组(用, 分割)
     * @return 是否包含
     */
    public  static boolean requals(String str1,  String str2) {
        if  (str1 != null && str2 != null) {
            str2 = str2.replaceAll("\\s*",  "");
            String[] arr = str2.split(", ");
            for  (String t : arr) {
                if  (t.equals(str1.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判定第一个字符串是否等于的第二个字符串中的某一个值
     *
     * @param str1  测试的字符串
     * @param str2  字符串数组
     * @param split str2字符串的分隔符
     * @return 是否包含
     */
    public  static boolean requals(String str1,  String str2,  String split) {
        if  (str1 != null && str2 != null) {
            str2 = str2.replaceAll("\\s*",  "");
            String[] arr = str2.split(split);
            for  (String t : arr) {
                if  (t.equals(str1.trim())) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 字符串省略截取
     * 字符串截取到指定长度size+...的形式
     *
     * @param subject 需要处理的字符串
     * @param size    截取的长度
     * @return 处理后的字符串
     */
    public  static String subStringOmit(String subject,  int size) {
        if  (subject != null && subject.length() > size) {
            subject = subject.substring(0,  size) + "...";
        }
        return subject;
    }


    /**
     * 截取字符串　超出的字符用symbol代替
     *
     * @param str    需要处理的字符串
     * @param len    字符串长度
     * @param symbol 最后拼接的字符串
     * @return 测试后的字符串
     */
    public  static String subStringSymbol(String str,  int len,  String symbol) {
        String temp = "";
        if  (str != null && str.length() > len) {
            temp = str.substring(0,  len) + symbol;
        }
        return temp;
    }


    /**
     * 隐藏邮件地址前缀。
     *
     * @param email - EMail邮箱地址 例如: ssss@koubei.com 等等...
     * @return 返回已隐藏前缀邮件地址,  如 *********@koubei.com.
     */
    public  static String getHideEmailPrefix(String email) {
        if  (null != email) {
            int index = email.lastIndexOf('@');
            if  (index > 0) {
                email = repeat("*",  index).concat(email.substring(index));
            }
        }
        return email;
    }

    /**
     * repeat - 通过源字符串重复生成N次组成新的字符串。
     *
     * @param src - 源字符串 例如: 空格(" "),  星号("*"),  "浙江" 等等...
     * @param num - 重复生成次数
     * @return 返回已生成的重复字符串
     */
    public  static String repeat(String src,  int num) {
        StringBuilder s = new StringBuilder();
        for  (int i = 0; i < num; i++)
            s.append(src);
        return s.toString();
    }

    /**
     * 截取字符串左侧的Num位截取到末尾
     *
     * @param str1 处理的字符串
     * @param num  开始位置
     * @return 截取后的字符串
     */
    public  static String ltrim(String str1,  int num) {
        String tt = "";
        if  (!isEmpty(str1) && str1.length() >= num) {
            tt = str1.substring(num,  str1.length());
        }
        return tt;

    }

    /**
     * 截取字符串右侧第0位到第Num位
     *
     * @param str 处理的字符串
     * @param num 截取的位置
     * @return 截取后的字符串
     */
    public  static String rtrim(String str,  int num) {
        if  (!isEmpty(str) && str.length() > num) {
            str = str.substring(0,  str.length() - num);
        }
        return str;
    }


    /**
     * 格式化一个float
     *
     * @param format 要格式化成的格式 such as #.00,  #.#
     * @return 格式化后的字符串
     */
    public  static String formatDouble(double f,  String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(f);
    }


    /**
     * 截取字符串左侧指定长度的字符串
     *
     * @param input 输入字符串
     * @param count 截取长度
     * @return 截取字符串
     */
    public  static String left(String input,  int count) {
        if  (isEmpty(input)) {
            return "";
        }
        count = (count > input.length()) ? input.length() : count;
        return input.substring(0,  count);
    }

    /**
     * 截取字符串右侧指定长度的字符串
     *
     * @param input 输入字符串
     * @param count 截取长度
     * @return 截取字符串
     * Summary 其他编码的有待测试
     */
    public  static String right(String input,  int count) {
        if  (isEmpty(input)) {
            return "";
        }
        count = (count > input.length()) ? input.length() : count;
        return input.substring(input.length() - count,  input.length());
    }

    /**
     * 页面中去除字符串中的空格、回车、换行符、制表符
     *
     * @param str 需要处理的字符串
     */
    public  static String replaceBlank(String str) {
        if  (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }


    /**
     * 字符串转换unicode.实现native2ascii.exe类似的功能
     *
     * @param string 需要处理的字符串
     */
    public  static String string2Unicode(String string) {
        StringBuilder uni = new StringBuilder();
        for  (int i = 0; i < string.length(); i++) {
            String temp ="\\u"+String.valueOf(Integer.toHexString(string.charAt(i)));
            uni.append(temp);
        }
        return uni.toString();
    }

    /**
     * 转字符串 实现native2ascii.exe类似的功能
     *
     * @param unicode 需要处理的字符串
     */
    public  static String unicode2String(String unicode) {
        StringBuilder str = new StringBuilder();
        String[]     hex    = unicode.split("\\\\u");
        for  (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i],  16);
            str.append((char) data);
        }
        return str.toString();
    }


    /**
     * 删除所有的标点符号
     *
     * @param str 处理的字符串
     */
    public  static String trimPunct(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.replaceAll("[\\pP\\p{Punct}]",  "");
    }

    /**
     * 获取字符串str在String中出现的次数
     *
     * @param string 处理的字符串
     * @param str 子字符串
     */
    public  static int countSubStr(String string,  String str) {
        if  ((str == null) || (str.length() == 0) || (string == null) || (string.length() == 0)) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = string.indexOf(str,  index)) != -1) {
            count++;
            index += str.length();
        }
        return count;
    }


    /**
     * 替换一个出现的子串
     *
     * @param s    source string
     * @param sub  substring to replace
     * @param with substring to replace with
     */
    public  static String replaceFirst(String s,  String sub,  String with) {
        int i = s.indexOf(sub);
        if  (i == -1) {
            return s;
        }
        return s.substring(0,  i) + with + s.substring(i + sub.length());
    }


    /**
     * 替换最后一次出现的字串
     * Replaces the very last occurrence of a substring with supplied string.
     *
     * @param s    source string
     * @param sub  substring to replace
     * @param with substring to replace with
     */
    public  static String replaceLast(String s,  String sub,  String with) {
        int i = s.lastIndexOf(sub);
        if  (i == -1) {
            return s;
        }
        return s.substring(0,  i) + with + s.substring(i + sub.length());
    }


    /**
     * 删除所有的子串
     * Removes all substring occurrences from the string.
     *
     * @param s   source string
     * @param sub substring to remove
     */
    public  static String remove(String s,  String sub) {
        int c      = 0;
        int sublen = sub.length();
        if  (sublen == 0) {
            return s;
        }
        int i = s.indexOf(sub,  c);
        if  (i == -1) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length());
        do {
            sb.append(s.substring(c,  i));
            c = i + sublen;
        } while ((i = s.indexOf(sub,  c)) != -1);
        if  (c < s.length()) {
            sb.append(s.substring(c,  s.length()));
        }
        return sb.toString();
    }

    /**
     * 将字符串首字母转大写
     * @param str 需要处理的字符串
     */
    public  static String upperFirstChar(String str) {
        if (isEmpty(str)) {
            return "";
        }
        char[] cs=str.toCharArray();
        if ((cs[0] >= 'a') && (cs[0] <= 'z')) {
            cs[0] -= (char) 0x20;
        }
        return String.valueOf(cs);
    }

    /**
     * 将字符串首字母转小写
     * @param str
     * @return
     */
    public  static String lowerFirstChar(String str) {
        if (isEmpty(str)) {
            return "";
        }
        char[] cs=str.toCharArray();
        if ((cs[0] >= 'A') && (cs[0] <= 'Z')) {
            cs[0] += (char) 0x20;
        }
        return String.valueOf(cs);
    }

    /**
     * 判读俩个字符串右侧的length个字符串是否一样
     * @param str1
     * @param str2
     * @param length
     * @return
     */
    public  static boolean rigthEquals(String str1, String str2, int length) {
        return right(str1, length).equals(right(str2, length));
    }

    /**
     * 判读俩个字符串左侧的length个字符串是否一样
     * @param str1
     * @param str2
     * @param length
     * @return
     */
    public  static boolean leftEquals(String str1, String str2, int length) {
        return left(str1, length).equals(left(str2, length));
    }
    
	/**
	 * 在文件夹的末尾加【/】
	 * @param inPath 传入的文件夹
	 * @return 加【/】后的文件夹
	 */
	public static String attachDirectoryMark(String inPath) {
		if  (inPath.lastIndexOf("/")!= inPath.length() - 1) {
			return inPath + "/";
		}
		return inPath;
	}

	/**
	 * 在文件夹的末尾加【/】
	 * @param inPath 传入的文件夹
	 * @return 加【/】后的文件夹
	 */
	public static StringBuilder attachDirectoryMark(StringBuilder inPath) {
		if  (inPath.lastIndexOf("/")!= inPath.length() - 1) {
			return inPath.append("/");
		}
		return inPath;
	}
	/**
	 * @Description: 返回对象的toString值，对象为null时返回0.主要使用预期对象是数字类型。
	 * @param value
	 * @return
	 */
	public static String checkObjForNum(Object value) {
		if (value == null || value.toString().trim().equals("")) {
			value = "0";
		}
		return value.toString().trim();
	}
	/**
	 * @Description: 返回对象的toString值，对象为null时返回"".主要使用预期对象是字符串类型。
	 * @param value
	 * @return
	 */
	public static String checkObjForStr(Object value) {
		if(value == null) return "";
		if (value instanceof Integer) {
			Integer val = (Integer) value;
			return Integer.toString(val);
		}
		if (value == null || value.toString().trim().equals("")) {
			value = "";
		}
		return value.toString().trim();
	}
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * @Description:返回对象的toString值，对象为null时返回当前日期.主要使用预期对象是日期格式yyyy-MM-dd HH:mm:ss的字符串类型。
	 * @param value
	 * @return
	 */
	public static String checkObjForDate(Object value) {
		Date date = null;
		if (value == null || value.toString().trim().equals("")) {
			date = new Date();
		}else {
			try {
				date = sdf.parse(value.toString().trim());
			} catch (ParseException e) {
				date = new Date();
			}
		}
		return sdf.format(date);
	}
	/**
	 * @Description: 返回对象的toString值，对象为null时返回当前日期.主要使用预期对象是日期格式yyyy-MM-dd的字符串类型。
	 * @param value
	 * @return
	 */
	public static String checkObjForDay(Object value) {
		Date date = null;
		if (value == null || value.toString().trim().equals("")) {
			date = new Date();
		}else {
			try {
				date = sdf.parse(value.toString().trim()+" 00:00:00");
			} catch (ParseException e) {
				date = new Date();
			}
		}
		return sdf.format(date).substring(0,10);
	}
	/**
	 * 用指定的字符集解码URL
	 * 
	 * @param url
	 *            要编码的URL
	 * @param charset
	 *            字符�?
	 * @return 编码后的URL
	 */
	public static String decodeURL(String url, String charset) {
		if (url != null && url.length() > 0) {
			try {
				
				return URLDecoder.decode(url, charset);
			} catch (UnsupportedEncodingException ex) {
				return url;
			}
		}
		return url;
	}
	public static String encodeURL(String url, String charset) {
		if (url != null && url.length() > 0) {
			try {
				
				return URLEncoder.encode(url, charset);
			} catch (UnsupportedEncodingException ex) {
				return url;
			}
		}
		return url;
	}
	
	/**
	 * 切除字符串右侧的指定字符，一直到最右侧不再是该字符为止
	 * @param str
	 * @param c
	 * @return
	 */
	public static String cutRightChar(String str,String c){
		StringBuffer sb = new StringBuffer(str);
		for(int i=0;i<sb.length();i++){
			if(str.lastIndexOf(c)==str.length()-1){ 
				str = str.substring(0, str.length()-1);
			}
			else break;
		}
		return str;
	}
}
