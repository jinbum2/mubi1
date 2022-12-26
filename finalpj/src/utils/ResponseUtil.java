package utils;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

public class ResponseUtil {//정상 및 에러에 대한 응답을 동일한 공통포맷을 사용하여 처리하는것을 목적으로 만들었다.
    public static String generateJSONResponse(boolean isSuccessed, Object data) {
        JSONObject result = new JSONObject();
        result.put("result", isSuccessed);

        Gson gson = new Gson();
        result.put("data", gson.toJson(data));
        return result.toString();
    }
}
