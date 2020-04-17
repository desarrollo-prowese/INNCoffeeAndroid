package com.example.inncoffee.ui.pago.tpvvinapplibrary.data.remote.util;


import com.example.inncoffee.ui.pago.tpvvinapplibrary.TPVVConstants;

public class TPVVURLConstants {
    public static final String DEFAULT_URL_KO = "REDIR_URL_KO";
    public static final String DEFAULT_URL_OK = "REDIR_URL_OK";
    private static final String URL_WS_NUEVA_FIRMA_DESARROLLO = "https://sis-d.redsys.es:25443/sis/virtualControllerV2/generaFirmaPagoVirtual";
    private static final String URL_WS_NUEVA_FIRMA_INTEGRATION = "https://sis-i.redsys.es:25443/sis/virtualControllerV2/generaFirmaPagoVirtual";
    private static final String URL_WS_NUEVA_FIRMA_REAL = "https://sis.redsys.es/sis/virtualControllerV2/generaFirmaPagoVirtual";
    private static final String URL_WS_NUEVA_FIRMA_TEST = "https://sis-t.redsys.es:25443/sis/virtualControllerV2/generaFirmaPagoVirtual";
    private static final String URL_WS_REALIZARPAGO_DESARROLLO = "https://sis-d.redsys.es:25443/sis/realizarPago";
    private static final String URL_WS_REALIZARPAGO_INTEGRATION = "https://sis-i.redsys.es:25443/sis/realizarPago";
    private static final String URL_WS_REALIZARPAGO_REAL = "https://sis.redsys.es/sis/realizarPago";
    private static final String URL_WS_REALIZARPAGO_TEST = "https://sis-t.redsys.es:25443/sis/realizarPago";
    private static final String URL_WS_SERCLSWSENTRADA_DESARROLLO = "https://sis-d.redsys.es:25443/sis/virtualControllerV2/pagoVirtual";
    private static final String URL_WS_SERCLSWSENTRADA_INTEGRATION = "https://sis-i.redsys.es:25443/sis/virtualControllerV2/pagoVirtual";
    private static final String URL_WS_SERCLSWSENTRADA_REAL = "https://sis.redsys.es/sis/virtualControllerV2/pagoVirtual";
    private static final String URL_WS_SERCLSWSENTRADA_TEST = "https://sis-t.redsys.es:25443/sis/virtualControllerV2/pagoVirtual";

    public static String getUrlWsNuevaFirma(String str) {
        String str2 = "";
        if (str == "0") {
            str2 = URL_WS_NUEVA_FIRMA_DESARROLLO;
        }
        if (str == "1") {
            str2 = URL_WS_NUEVA_FIRMA_INTEGRATION;
        }
        if (str == TPVVConstants.ENVIRONMENT_REAL) {
            str2 = URL_WS_NUEVA_FIRMA_REAL;
        }
        return str == TPVVConstants.ENVIRONMENT_TEST ? URL_WS_NUEVA_FIRMA_TEST : str2;
    }

    public static String getUrlWsRealizarpago(String str) {
        String str2 = "";
        if (str == "0") {
            str2 = URL_WS_REALIZARPAGO_DESARROLLO;
        }
        if (str == "1") {
            str2 = URL_WS_REALIZARPAGO_INTEGRATION;
        }
        if (str == TPVVConstants.ENVIRONMENT_REAL) {
            str2 = URL_WS_REALIZARPAGO_REAL;
        }
        return str == TPVVConstants.ENVIRONMENT_TEST ? URL_WS_REALIZARPAGO_TEST : str2;
    }

    public static String getUrlWsSerclswsentrada(String str) {
        String str2 = "";
        if (str == "0") {
            str2 = URL_WS_SERCLSWSENTRADA_DESARROLLO;
        }
        if (str == "1") {
            str2 = URL_WS_SERCLSWSENTRADA_INTEGRATION;
        }
        if (str == TPVVConstants.ENVIRONMENT_REAL) {
            str2 = URL_WS_SERCLSWSENTRADA_REAL;
        }
        return str == TPVVConstants.ENVIRONMENT_TEST ? URL_WS_SERCLSWSENTRADA_TEST : str2;
    }
}
