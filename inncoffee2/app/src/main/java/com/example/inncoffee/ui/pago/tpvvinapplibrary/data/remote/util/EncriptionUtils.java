package com.example.inncoffee.ui.pago.tpvvinapplibrary.data.remote.util;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncriptionUtils {
    private static final char[] hexArray = "0123456789abcdef".toCharArray();

    public static String getHash256(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.SHA256_INSTANCE);
            instance.update(str.getBytes("iso-8859-1"), 0, str.length());
            return bytesToHex(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private static String bytesToHex(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            int b = (bArr[i] & 255);
            int i2 = i * 2;
            cArr[i2] = hexArray[b >>> 4];
            cArr[i2 + 1] = hexArray[b & 15];
        }
        return new String(cArr);
    }
}
