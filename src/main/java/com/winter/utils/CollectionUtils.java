package com.winter.utils;

import static java.lang.String.valueOf;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by suye on 2017/5/19.
 */
public class CollectionUtils{
    public static final String DEFAULT_SEPARATOR = ",";

    public static boolean containsStr(String elements, String element, String separator) {
        String[] strArray = org.apache.commons.lang3.StringUtils.split(elements, separator);

        Set<String> strSet = stringArray2Set(strArray);

        return strSet.contains(element);
    }

    public static boolean containsStr(String elements, String element) {
        return containsStr(elements, element, DEFAULT_SEPARATOR);
    }

    public static Set<String> stringArray2Set(String[] strArray){
        Set<String> set = new HashSet<>(Arrays.asList(strArray));

        return set;
    }


    public static interface GetKey<T> {
        String getKey(T obj);
    }

    /**
     * 将列表转换为数组;
     *
     * @param <T>
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] listToArray(List<T> list) {
        if (list == null || list.size() == 0) {
            return null;
        }

        T[] tArr = (T[]) Array.newInstance(list.get(0).getClass(), list.size());
        return list.toArray(tArr);

    }

    /**
     * 将列表转换为数组;
     *
     * @param <T>
     * @return
     */
    public static <T> List<T> arrayToList(T[] array) {
        if (array == null) {
            return null;
        }

        return Arrays.asList(array);
    }

    /**
     * 将列表转换为数组;
     *
     * @param <T>
     * @return
     */
    public static <T> Set<T> arrayToSet(T[] array) {
        if (array == null) {
            return new HashSet<T>();
        }
        Set<T> set = new HashSet<T>();
        for (T t : array) {
            set.add(t);
        }
        return set;
    }

    public static <T> List<T> subList(List<T> list, Integer pageSize, Integer pageNO) {
        int from = (pageNO - 1) * pageSize;
        int begin = from < list.size() ? from : list.size();

        int to = from + pageSize;
        int end = to < list.size() ? to : list.size();
        return list.subList(begin, end);
    }

    public static <T> List<List<T>> splitList(List<T> srcList, int maxSize) {
        List<List<T>> splitList = new ArrayList<List<T>>();
        if (srcList == null || srcList.size() == 0) {
            return splitList;
        }

        int listSize = srcList.size();
        int splitListSize = (listSize / maxSize) + (listSize % maxSize > 0 ? 1 : 0);
        for (int i = 0; i < splitListSize; i++) {
            int beginIndex = i * maxSize;
            int endIndex = (i + 1) * maxSize;
            endIndex = endIndex > listSize ? listSize : endIndex;
            splitList.add(srcList.subList(beginIndex, endIndex));
        }
        return splitList;
    }

    /**
     * 截取直接数组
     *
     * @param srcByteArr
     * @param index
     * @param length
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] subArr(T[] srcByteArr, int index, int length) {
        // 验证参数合法性
        if (srcByteArr == null || index < 0 || length < 0 || srcByteArr.length < index + length) {
            throw new RuntimeException("参数不合法:subByteArr(T[] " + srcByteArr + ", int " + index + ", int " + length + ")");
        }

        // 构造新数据并拷贝
        T[] tArr = (T[]) Array.newInstance(srcByteArr[0].getClass(), length);
        System.arraycopy(srcByteArr, index, tArr, 0, length);
        return tArr;
    }

    /**
     * 截取直接数组
     *
     * @param srcByteArr
     * @param index
     * @param length
     * @return
     */
    public static byte[] subArr(byte[] srcByteArr, int index, int length) {
        // 验证参数合法性
        if (srcByteArr == null || index < 0 || length < 0 || srcByteArr.length < index + length) {
            throw new RuntimeException("参数不合法:subByteArr(T[] " + srcByteArr + ", int " + index + ", int " + length + ")");
        }

        // 构造新数据并拷贝
        byte[] tArr = new byte[length];
        System.arraycopy(srcByteArr, index, tArr, 0, length);
        return tArr;
    }

    /**
     * 转换数组:String[] -> Long[]
     *
     * @param strArr
     * @return
     */
    public static Long[] convertArray(String[] strArr) {
        if (strArr == null) {
            return null;
        }

        Long[] longArr = new Long[strArr.length];
        for (int i = 0; i < longArr.length; i++) {
            if (!NumberUtils.isDigits(strArr[i])) {
                throw new RuntimeException("执行字符串数组到长整型数组转换过程中,发现字符串数组存在非长整型字符串[索引:" + i + " 值:" + strArr[i] + "]");
            }

            longArr[i] = Long.parseLong(strArr[i]);
        }
        return longArr;
    }

    /**
     * 将数组转换为逗号分隔的字符串
     *
     * @param objArr
     * @return
     */
    public static String arrayToString(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            return null;
        }

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < objArr.length; i++) {
            buf.append((i > 0 ? "," : "") + objArr[i]);
        }
        return buf.toString();
    }

    /**
     * 将逗号分隔的字符串转换为数组
     *
     * @param str
     * @return
     */
    public static String[] stringToArray(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        String[] split = str.split(",");
        return split;
    }

    /**
     * 将一个字符串使用分隔符分割后转换为数组
     *
     * @param str
     * @param splitChar
     * @return
     */
    public static List<String> splitStringToList(String str, String splitChar) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        return arrayToList(str.split(splitChar));
    }

    /**
     * 将一个字符串使用分隔符分割后转换为数组
     *
     * @param str
     * @param splitChar
     * @return
     */
    public static List<Integer> splitStringToIntegerList(String str, String splitChar) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        String[] split = str.split(splitChar);
        List<Integer> list = new ArrayList<Integer>();
        for (String string : split) {
            list.add(Integer.valueOf(string));
        }
        return list;
    }


    /**
     * 判断一个对象列表是否为空
     *
     * @param objects
     * @return
     */
    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }


    /**
     * 将Map<String,String[]>转换为Map<String,String>
     *
     * @param srcMap
     * @return
     */
    public static Map<String, String> convertMap(Map<String, String[]> srcMap) {
        Map<String, String> aimMap = new HashMap<String, String>();
        for (Entry<String, String[]> entry : srcMap.entrySet()) {
            String[] srcValue = entry.getValue();
            String aimValue = srcValue != null && srcValue.length > 0 ? srcValue[0] : null;
            aimMap.put(entry.getKey(), aimValue);
        }
        return aimMap;
    }

    /**
     * 转换properties为map
     *
     * @param properties
     * @return
     */
    public static Map<String, String> convertProperties2Map(Properties properties) {
        if (properties == null) {
            return null;
        }

        Map<String, String> map = new LinkedHashMap<String, String>();
        Set<Entry<Object, Object>> entrySet = properties.entrySet();
        for (Entry<Object, Object> entry : entrySet) {
            map.put(valueOf(entry.getKey()), valueOf(entry.getValue()));
        }

        return map;
    }

    /**
     * 判断两个集合是否包含重复项
     *
     * @param list1
     * @param list2
     * @return
     */
    public static boolean isOverlap(Collection<?> list1, Collection<?> list2) {
        if (list1 == list2) {
            return true;
        }

        if (list1 == null || list2 == null) {
            return false;
        }

        for (Object element : list1) {
            if (list2.contains(element)) {
                return true;
            }
        }

        return false;
    }

    public static String map2String(Map<?, ?> map) {
        return map2String(map, ",");
    }

    public static String map2String(Map<?, ?> map, String splitChar) {
        if (map == null) return null;
        StringBuffer buf = new StringBuffer();
        for (Entry<?, ?> entry : map.entrySet()) {
            if (buf.length() > 0) buf.append(splitChar);
            buf.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return buf.toString();
    }

    /**
     * 将字符串列表内容都改为小写
     *
     * @param srcList
     * @return
     */
    public static List<String> toLowerCase(List<String> srcList) {
        if (srcList == null) {
            return null;
        }

        List<String> aimList = new ArrayList<String>();
        for (String string : srcList) {
            aimList.add(org.apache.commons.lang3.StringUtils.lowerCase(string));
        }
        return aimList;
    }

    /**
     * 将字符串列表内容都改为小写
     *
     * @param srcList
     * @return
     */
    public static Set<String> toLowerCase(Set<String> srcList) {
        if (srcList == null) {
            return null;
        }

        Set<String> aimList = new HashSet<String>();
        for (String string : srcList) {
            aimList.add(org.apache.commons.lang3.StringUtils.lowerCase(string));
        }
        return aimList;
    }

    /**
     * 将字符串列表内容都改为大写
     *
     * @param srcList
     * @return
     */
    public static List<String> toUpperCase(List<String> srcList) {
        if (srcList == null) {
            return null;
        }

        List<String> aimList = new ArrayList<String>();
        for (String string : srcList) {
            aimList.add(org.apache.commons.lang3.StringUtils.upperCase(string));
        }
        return aimList;
    }

    /**
     * 将字符串列表内容都改为大写
     *
     * @param srcList
     * @return
     */
    public static Set<String> toUpperCase(Set<String> srcList) {
        if (srcList == null) {
            return null;
        }

        Set<String> aimList = new HashSet<String>();
        for (String string : srcList) {
            aimList.add(org.apache.commons.lang3.StringUtils.upperCase(string));
        }
        return aimList;
    }




    public static <T> Map<String, List<T>> convert2Map(List<T> valueList, GetKey<T> getKey) {
        Map<String, List<T>> map = new HashMap<String, List<T>>();
        for (T value : valueList) {
            String key = getKey.getKey(value);
            List<T> mapedValueList = map.get(key);
            if (mapedValueList == null) {
                mapedValueList = new ArrayList<T>();
                map.put(key, mapedValueList);
            }
            mapedValueList.add(value);
        }
        return map;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map makeMap(Object... v) {
        if (v.length % 2 != 0) throw new RuntimeException("值长度[" + v.length + "]不是2的倍数,不能映射成Map!");

        Map map = new LinkedHashMap<Object, Object>();
        for (int i = 0; i + 1 < v.length; i += 2) {
            map.put(v[i], v[i + 1]);
        }
        return map;
    }

    @SuppressWarnings("rawtypes")
	public static String map2string(Map<?, ?> map) {
        if(map != null && !map.isEmpty()) {
            StringBuffer buf = new StringBuffer();
            Iterator var2 = map.entrySet().iterator();
            while(var2.hasNext()) {
                Map.Entry entry = (Map.Entry)var2.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                if(buf.length() > 0) {
                    buf.append("\n");
                }
                buf.append(key != null?key.toString():"");
                buf.append("=");
                buf.append(value != null?value.toString():"");
            }
            return buf.toString();
        } else {
            return "";
        }
    }

    public static Map<String, String> makeStringMap(Object... v) {
        if (v.length % 2 != 0) throw new RuntimeException("值长度[" + v.length + "]不是2的倍数,不能映射成Map!");

        Map<String, String> map = new LinkedHashMap<String, String>();
        for (int i = 0; i + 1 < v.length; i += 2) {
            map.put(valueOf(v[i]), valueOf(v[i + 1]));
        }
        return map;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K, V> Map<K, V> filterMap(Map<K, V> params) {
        if (params == null || params.isEmpty()) return params;
        Map<K, V> result = new HashMap();
        for (K key : params.keySet()) {
            if (key == null || key.equals("")) continue;
            V value = params.get(key);
            if (value == null || value.equals("")) continue;
            result.put(key, value);
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 131; i++) {
            list.add(i);
        }

        List<List<Integer>> splitList = splitList(list, 7);
        int i = 1;
        for (List<?> list2 : splitList) {
            System.out.print(i + ":\t");
            for (Object object : list2) {
                System.out.print(object + "\t");
            }
            System.out.println();
            i++;
        }
    }

}
