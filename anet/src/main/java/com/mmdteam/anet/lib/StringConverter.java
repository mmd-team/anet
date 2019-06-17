package com.mmdteam.anet.lib;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/** https://segmentfault.com/q/1010000002748900 关于Gson解析json数据时如果属性值为null则会报空的问题 */
class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {
  @Override
  public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
    if (src == null) {
      return new JsonPrimitive("");
    } else {
      return new JsonPrimitive(src);
    }
  }

  @Override
  public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    return json.getAsJsonPrimitive().getAsString();
  }
}
