package com.example.user.profileupdate;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://vamc.000webhostapp.com/profile2.php";

    private Map<String, String> params;

    public RegisterRequest(String number, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();

        params.put("number", number);

        }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
