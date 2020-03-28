package com.guankai.springcloud;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 自定义集合工具类
 *
 * @author: guan.kai
 * @date: 2019/8/19 23:12
 **/
public class CollectionsUitl {

    private CollectionsUitl() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CollectionsUitl.class);

    private static final String MSG = "参数不能为空！";
    private static final String COMMA = ",";


    /**
     * 判断是否为空.
     */
    public static boolean isEmpty(Collection collection) {
        return (collection == null) || collection.isEmpty();
    }

    /**
     * 判断是否为空.
     */
    public static boolean isEmpty(Map map) {
        return (map == null) || map.isEmpty();
    }

    /**
     * 抽取某个属性，返回list
     *
     * @param collection 待处理集合
     * @param propertyName 属性名
     * @return
     */
    public static List extractToList(Collection collection, String propertyName){
        if (!isEmpty(collection) && StringUtils.isNotBlank(propertyName)){
            List list = new ArrayList(collection.size());
            //将属性名字的首字母大写
            propertyName = propertyName.replaceFirst(propertyName.substring(0, 1), propertyName.substring(0, 1).toUpperCase());
            for (Object obj : collection) {
                try {
                    Object invoke = obj.getClass().getMethod("get" + propertyName).invoke(obj);
                    list.add(invoke);
                } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
                    logger.error(e.getMessage());
                }
            }
            return list;
        }
        throw new NullPointerException(MSG);
    }

    /**
     * 抽取某个属性，以分隔符拼接成字符串返回,cutFlag为null或者空字符串时，默认使用","逗号分隔
     *
     * @param collection 待处理集合
     * @param propertyName 属性名
     * @param cutFlag 分隔符
     * @return
     */
    public static String extractToString(Collection collection, String propertyName, String cutFlag){
        if (!isEmpty(collection) && StringUtils.isNotBlank(propertyName)){
            StringBuilder str = new StringBuilder();
            //将属性名字的首字母大写
            propertyName = propertyName.replaceFirst(propertyName.substring(0, 1), propertyName.substring(0, 1).toUpperCase());
            for (Object obj : collection) {
                try {
                    String invoke = (String)obj.getClass().getMethod("get" + propertyName).invoke(obj);
                    if (StringUtils.isBlank(cutFlag)){
                        cutFlag = COMMA;
                    }
                    str.append(cutFlag).append(invoke);
                } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
                    logger.error(e.getMessage());
                }
            }
            return str.toString().substring(cutFlag.length());
        }
        throw new NullPointerException(MSG);
    }

    /**
     * 抽取某个属性，返回一个 以该属性为key、对应对象为value的map集合
     *
     * @param collection 待处理集合
     * @param propertyName 属性名
     * @return
     */
    public static Map extractToMap(Collection collection, String propertyName){
        if (!isEmpty(collection) && StringUtils.isNotBlank(propertyName)){
            Map map = new HashMap(collection.size());
            //将属性名字的首字母大写
            propertyName = propertyName.replaceFirst(propertyName.substring(0, 1), propertyName.substring(0, 1).toUpperCase());
            for (Object obj : collection) {
                try {
                    Object invoke = obj.getClass().getMethod("get" + propertyName).invoke(obj);
                    map.put(invoke,obj);
                } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
                    logger.error(e.getMessage());
                }
            }
            return map;
        }
        throw new NullPointerException(MSG);
    }

    /**
     * 根据属性抽取，返回map
     *
     * @param collection 待处理集合
     * @param keyPropertyName 作为key的属性名
     * @param valuePropertyName 作为value的属性名
     * @return
     */
    public static Map extractToMap(Collection collection, String keyPropertyName, String valuePropertyName){
        if (!isEmpty(collection) && StringUtils.isNotBlank(keyPropertyName) && StringUtils.isNotBlank(valuePropertyName)){
            Map map = new HashMap(collection.size());
            //将属性名字的首字母大写
            keyPropertyName = keyPropertyName.replaceFirst(keyPropertyName.substring(0, 1), keyPropertyName.substring(0, 1).toUpperCase());
            valuePropertyName = valuePropertyName.replaceFirst(valuePropertyName.substring(0, 1), valuePropertyName.substring(0, 1).toUpperCase());
            for (Object obj : collection) {
                try {
                    Object key = obj.getClass().getMethod("get" + keyPropertyName).invoke(obj);
                    Object value = obj.getClass().getMethod("get" + valuePropertyName).invoke(obj);
                    map.put(key,value);
                } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
                    logger.error(e.getMessage());
                }
            }
            return map;
        }
        throw new NullPointerException(MSG);
    }

    /**
     * 根据指定属性值创建分组集合，JDK7下使用，JDK8下可用JDK自带方法List<T>.stream().collect(Collectors.groupingBy(T::getMethodName))
     *
     * @param collection
     * @param keyPropertyName  collection中对象的属性
     * @return
     */
    public static <T> Map<String,List<T>> extractToMapGroupList(List<T> collection, String keyPropertyName) {
        if (!isEmpty(collection)  && StringUtils.isNotBlank(keyPropertyName)) {
            Map<String,List<T>> map = new HashMap(collection.size());
            try {
                for (T obj : collection) {
                    String key = obj.getClass().getMethod("get" + keyPropertyName).invoke(obj).toString();
                    if(map.containsKey(key)){
                        map.get(key).add(obj);
                    }else{
                        List<T> list = new ArrayList<T>();
                        list.add(obj);
                        map.put(key, list);
                    }
                }

            } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
                logger.error(e.getMessage());
            }
            return map;
        }
        throw new NullPointerException(MSG);
    }

    /**
     * 截取list集合，根据截取大小返回多段list
     *
     * @param list 待处理集合
     * @param subSize 截取大小
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> subList(List<T> list, int subSize){
        if (!isEmpty(list)){
            List<List<T>> resultList = new ArrayList<>();
            int size = list.size();
            if (size < subSize) {
                resultList.add(list);
            } else {
                int count = (size + subSize - 1) / subSize;
                for (int i = 0; i < count; i++) {
                    resultList.add(list.subList(i * subSize, ((i + 1) * subSize > size ? size : subSize * (i + 1))));
                }
            }
            return resultList;
        }
        throw new NullPointerException(MSG);
    }

}
