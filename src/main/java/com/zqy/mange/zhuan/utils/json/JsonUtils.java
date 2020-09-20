package com.zqy.mange.zhuan.utils.json;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zqy.mange.zhuan.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component//实例化到spring容器中
public class JsonUtils {
    @Autowired
    private LogUtil logs;
    // 定义jackson对象
    private ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     *
     * @param data
     * @return
     */
    public String objectToJson(Object data) {
        // String string = MAPPER.writeValueAsString(data);
        String string = new Gson().toJson(data);
        return string;

    }


    /**
     * 将对象转换成json字符串
     *
     * @param data
     * @param list 过滤字段名
     * @return
     */
    public String objectToJsonFilter(Object data, List<Object> list) {

        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                //过滤掉字段名包含"id","address"的字段
                boolean equals = list.contains(f.getName());
                // System.out.print("字段："+f.getName()+"是否过滤："+equals);
                return equals;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                //过滤掉 类名包含 Bean的类
                return false;
            }
        }).create();

        String string = gson.toJson(data);
        return string;

    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public <T> T jsonToObject(String jsonData, Class<T> beanType) {
        try {
            //  T t = MAPPER.readValue(jsonData, beanType);
            T t = new Gson().fromJson(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            logs.error(beanType.getSimpleName() + "_json数据格式异常", e.getMessage(), true);
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();

            logs.error(beanType.getSimpleName() + "_json数据格式异常", e.getMessage(), true);
        }
        return null;
    }

    /**
     * 将对象装换为map
     *
     * @param object
     * @return
     */
    public <T> Map<String, Object> objectToMap(T object) {
        Map<String, Object> map = new HashMap<>();
        if (object != null) {
            BeanMap beanMap = BeanMap.create(object);
            for (Object key : beanMap.keySet()) {
                map.put(String.valueOf(key), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map    value为“”时转对象会出错
     * @param object
     * @return
     */
    public <T> T mapToObject(Map<String, Object> map, Class<T> object) {
        Map<String, Object> newMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                newMap.put(key, value);
            }
        }
        Gson gson = new Gson();
        String toJson = gson.toJson(newMap);
        T t = gson.fromJson(toJson, object);
        return t;
    }

}
