package com.open.fire.utils_package.qqutils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
 *  public transient boolean couldBeRemoved = false;   transient 的关键字可以保持gson 转换的时候不保存（比如bean 转json，不回包含这个字）
 *  @Expose(serialize = false, deserialize = false)   这个关键字需要每个变量都加
 *
 *
 *   @SerializedName("cid")
 *   public int cid;   序列化字段
 * */

/*
 *  生成json 的时候使用
 * 使用 GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls();
 *Map m = New HashMap();
 *m.put("a", null);
 *输出：new Gson().toJson(m);
 *预期结果：{a:null}
 *实际结果：{}
 *
 * */

/**
 * 客户端内部配置Gson解析器
 * <p>相比于默认初始化的Gson配置了适合战旗客户端的序列化和反序列化规则，
 * 如需针对特殊类型添加解析，请在初始化时添加Deserializer</p>
 *
 * @author linxiao
 * Create on 2018-01-04
 */
public class ApiGsonParser {

    private static final String TAG = ApiGsonParser.class.getSimpleName();

    private static Gson gson;

    public static Gson getParser() {
        if (gson == null) {
            initParser();
        }
        return gson;
    }

    private static void initParser() {
        //Integer.class  这个是Gson 解析给你的预期值。  bean 中的类型是你想要的值。
        gson = new GsonBuilder()
                .registerTypeAdapter(int.class, new IntegerDeserializer())
                .registerTypeAdapter(Integer.class, new IntegerDeserializer())
                .registerTypeAdapter(String.class, new StringDeserializer())
                .registerTypeAdapter(JSONObject.class, new JSONObjectDeserializer())
                .registerTypeAdapter(JSONArray.class, new JSONArrayDeserializer())
                .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory<Object>())
//        .registerTypeAdapter(ApiResponse.class, new ApiResponseDeserializer())
                .create();
    }

    /**
     * 将传入json string反序列化为声明类型对象
     *
     * @param json  需要反序列化的json string
     * @param clazz 需要反序列化的对象类型class
     * @param <T>   需要反序列化的对象类型声明
     * @return 反序列化类型的对象，如果失败则返回null
     */
    public static <T> T fromJSONObject(String json, Class<T> clazz) {
        try {
            return getParser().fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将传入{@link JSONObject}对象反序列化为声明类型对象
     *
     * @param json  需要反序列化的{@link JSONObject}对象
     * @param clazz 需要反序列化的对象类型class
     * @param <T>   需要反序列化的对象类型声明
     * @return 反序列化类型的对象，如果失败则返回null
     */
    public static <T> T fromJSONObject(JSONObject json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        try {
            return getParser().fromJson(json.toString(), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将传入{@link JSONArray}字符串反序列化为声明类型对象
     *
     * @param jsonArrayStr 需要反序列化的{@link JSONObject}字符串
     * @param clazz        需要反序列化的对象类型class
     * @param <T>          需要反序列化的对象类型声明
     * @return 反序列化类型的对象，如果失败则返回null
     */
    public static <T> List<T> fromJSONArray(String jsonArrayStr, Class<T> clazz) {
        List<T> retList = new ArrayList<>();
        if (TextUtils.isEmpty(jsonArrayStr)) {
            return retList;
        }
        try {
            JsonArray arr = new JsonParser().parse(jsonArrayStr).getAsJsonArray();
            for (JsonElement jsonElement : arr) {
                retList.add(getParser().fromJson(jsonElement, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return retList;
        }
        return retList;
    }

    /**
     * 将传入{@link JSONArray}对象反序列化为声明类型对象
     *
     * @param jsonArray 需要反序列化的{@link JSONObject}对象
     * @param clazz     需要反序列化的对象类型class
     * @param <T>       需要反序列化的对象类型声明
     * @return 反序列化类型的对象，如果失败则返回null
     */
    public static <T> List<T> fromJSONArray(JSONArray jsonArray, Class<T> clazz) {
        return fromJSONArray(String.valueOf(jsonArray), clazz);
    }

    /**
     * 将传入类型对象序列化为{@link JSONObject}对象
     *
     * @param data 需要序列化的对象
     * @param <T>  需要序列化的对象类型声明
     * @return {@link JSONObject}对象，如果序列化失败则返回null
     */
    public static <T> JSONObject toJSONObject(T data) {
        try {
            String jsonText = getParser().toJson(data);
            return new JSONObject(jsonText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将传入类型对象序列化为{@link JSONArray}对象
     *
     * @param data 需要序列化的对象列表
     * @param <T>  需要序列化的对象类型声明
     * @return {@link JSONArray}对象，如果序列化失败则返回null
     */
    public static <T> JSONArray toJSONArray(List<T> data) {

        try {
            String jsonText = getParser().toJson(data);
            return new JSONArray(jsonText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * int遇到浮点数时强转规则
     */
    static class IntegerDeserializer implements JsonDeserializer<Integer> {
        //解码
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {
                    //定义为long类型,如果后台返回""或者null,则返回0
                    return 0;
                }
                return json.getAsInt();
            } catch (Exception e) {
                try {
                    return (int) json.getAsDouble();
                } catch (Exception e1) {
                    throw new JsonParseException(e.getMessage());
                }
            }
        }
    }

    /**
     * 字符串强转
     */
    static class StringDeserializer implements JsonDeserializer<String> {

        @Override
        public String deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {
                    //定义为long类型,如果后台返回""或者null,则返回0
                    return "";
                }
                return json.getAsString();
            } catch (Exception e) {
                return json.toString();
            }
        }
    }

    /**
     * Gson 转 org.json.JSONObject
     */
    static class JSONObjectDeserializer implements JsonDeserializer<JSONObject> {

        @Override
        public JSONObject deserialize(JsonElement json, Type typeOfT,
                                      JsonDeserializationContext context) throws JsonParseException {
            try {
                return new JSONObject(json.toString());
            } catch (JSONException e) {
                Log.w(TAG, "err, string = " + json.toString());
            }
            return null;
        }
    }

    /**
     * Gson 转 org.json.JSONArray
     */
    static class JSONArrayDeserializer implements JsonDeserializer<JSONArray> {

        @Override
        public JSONArray deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
            try {
                return new JSONArray(json.toString());
            } catch (JSONException e) {
                Log.w(TAG, "err, string = " + json.toString());
            }
            return null;
        }
    }

    /**
     *  将null转换为""工厂适配器
     *  TypeAdapterFactory,见名知意，用于创建TypeAdapter的工厂类，
     *  通过对比Type，确定有没有对应的TypeAdapter，
     *  没有就返回null，与GsonBuilder.registerTypeAdapterFactory配合使用。
     *
     *https://blog.csdn.net/yangshare/article/details/77482156
     */
    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings({"unchecked", "hiding"})
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }


    public static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }

    }



    /* *//**
     * ApiResponse解析类
     * <p>用于统一解析ApiResponse, 将body转换为上层请求的泛型对象，统一处理异常</p>
     *//*
    static class ApiResponseDeserializer implements JsonDeserializer<ApiResponse> {
        @Override
        public ApiResponse deserialize(JsonElement json, Type typeOfT,
                                       JsonDeserializationContext context) throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            ApiResponse response = new ApiResponse();
            try {
                response.code = obj.get("code").getAsInt();
            } catch (Exception e) {
                e.printStackTrace();
                response.code = -1;
            }
            try {
                JsonElement msgObj = obj.get("message");
                if (msgObj == null) {
                    msgObj = obj.get("desc");
                }
                if (msgObj != null) {
                    response.message = msgObj.getAsString();
                }
                else {
                    response.message = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.message = "";
            }
            try {
                response.data = String.valueOf(obj.get("data"));
            } catch (Exception e) {
                e.printStackTrace();
                response.data = "";
            }
            return response;
        }
    }*/
}
