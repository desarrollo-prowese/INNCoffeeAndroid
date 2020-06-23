package com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.mapping;

import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.TPVVConfiguration;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.model.SignedPetitionDto;
import com.rakshasa.inncoffee.ui.pago.tpvvinapplibrary.data.remote.util.EncriptionUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import org.json.JSONException;
import org.json.JSONObject;

public class TPVVMapper<T> {
    final Class<T> typeParameterClass;

    public TPVVMapper(Class<T> cls) {
        this.typeParameterClass = cls;
    }

    public JSONObject toJSONObject(T t) throws JSONException {
        Gson create = new GsonBuilder().create();
        new JSONObject();
        return new JSONObject(create.toJson((Object) t, (Type) this.typeParameterClass));
    }

    public T toModel(JSONObject jSONObject) {
        return new GsonBuilder().create().fromJson(jSONObject.toString(), this.typeParameterClass);
    }

    private static SignedPetitionDto toSignedPetitionDto(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return new SignedPetitionDto(EncriptionUtils.getHash256(str + TPVVConfiguration.getLicense()), str);
    }

    public JSONObject toJsonSignedPetition(T t) throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException {
        return new TPVVMapper(SignedPetitionDto.class).toJSONObject(toSignedPetitionDto(new TPVVMapper(this.typeParameterClass).toJSONObject(t).toString()));
    }
}
