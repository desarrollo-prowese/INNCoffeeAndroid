package com.example.inncoffee.ui.pago.tpvvinapplibrary;

public class TPVVConfiguration {
    private static String accountType = null;
    private static String amount = null;
    private static String currency = null;
    private static String description = null;
    private static boolean enableRedirection = false;
    private static Boolean enableResultAlert = false;
    private static String environment = "1";
    private static String fuc = null;
    private static String group = null;
    private static String identifier = null;
    private static String language = null;
    private static String license = null;
    private static String loadingScreenBackgroundColor = null;
    private static TPVVConfiguration mInstance = null;
    private static String merchantData = null;
    private static String merchantDescriptor = null;
    private static String merchantName = null;
    private static String merchantUrl = null;
    private static final String module = "PSis_Android";
    private static String operationType;
    private static String order;
    private static String packageName;
    private static String partialPayment;
    private static String paymentMethods;
    private static String paymentModule;
    private static String prepaidCard;
    private static String progressBarColor;
    private static String resultAlertButtonColor = "#FFFFFFFF";
    private static String resultAlertTextButtonKo = "Result KO";
    private static String resultAlertTextButtonOk = "Result OK";
    private static String resultAlertTextKo = "Continue";
    private static String resultAlertTextOk = "Continue";
    private static String shippingAddressPyp;
    private static String sumTotal;
    private static String taxReference;
    private static String terminal;
    private static String titular;
    private static UIDirectPaymentConfig uiDirectPaymentConfig;
    private static String urlKO;
    private static String urlOK;

    public static String getModule() {
        return module;
    }

    private TPVVConfiguration() {
    }

    public static TPVVConfiguration getInstance() {
        if (mInstance == null) {
            return new TPVVConfiguration();
        }
        return mInstance;
    }

    public static String getLicense() {
        return license;
    }

    public static void setLicense(String str) {
        license = str;
    }

    public static String getFuc() {
        return fuc;
    }

    public static void setFuc(String str) {
        fuc = str;
    }

    public static String getTerminal() {
        return terminal;
    }

    public static void setTerminal(String str) {
        terminal = str;
    }

    public static String getPackageName() {
        return packageName;
    }

    protected static void setPackageName(String str) {
        packageName = str;
    }

    public static String getEnvironment() {
        return environment;
    }

    public static void setEnvironment(String str) {
        environment = str;
    }

    public static String getCurrency() {
        return currency;
    }

    public static void setCurrency(String str) {
        currency = str;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String str) {
        language = str;
    }

    public static String getUrlOK() {
        return urlOK;
    }

    public static void setUrlOK(String str) {
        urlOK = str;
    }

    public static String getUrlKO() {
        return urlKO;
    }

    public static void setUrlKO(String str) {
        urlKO = str;
    }

    public static String getMerchantUrl() {
        return merchantUrl;
    }

    public static void setMerchantUrl(String str) {
        merchantUrl = str;
    }

    public static String getMerchantData() {
        return merchantData;
    }

    public static void setMerchantData(String str) {
        merchantData = str;
    }

    public static TPVVConfiguration getmInstance() {
        return mInstance;
    }

    public static void setmInstance(TPVVConfiguration tPVVConfiguration) {
        mInstance = tPVVConfiguration;
    }

    public static String getOrder() {
        return order;
    }

    public static void setOrder(String str) {
        order = str;
    }

    public static String getAmount() {
        return amount;
    }

    public static void setAmount(String str) {
        amount = str;
    }

    public static String getOperationType() {
        return operationType;
    }

    public static void setOperationType(String str) {
        operationType = str;
    }

    public static String getIdentifier() {
        return identifier;
    }

    public static void setIdentifier(String str) {
        identifier = str;
    }

    public static String getGroup() {
        return group;
    }

    public static void setGroup(String str) {
        group = str;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String str) {
        description = str;
    }

    public static String getTitular() {
        return titular;
    }

    public static void setTitular(String str) {
        titular = str;
    }

    public static String getPaymentMethods() {
        return paymentMethods;
    }

    public static void setPaymentMethods(String str) {
        paymentMethods = str;
    }

    public static String getPaymentModule() {
        return paymentModule;
    }

    public static void setPaymentModule(String str) {
        paymentModule = str;
    }

    public static UIDirectPaymentConfig getUiDirectPaymentConfig() {
        return uiDirectPaymentConfig;
    }

    public static void setUiDirectPaymentConfig(UIDirectPaymentConfig uIDirectPaymentConfig) {
        uiDirectPaymentConfig = uIDirectPaymentConfig;
    }

    public static String getAccountType() {
        return accountType;
    }

    public static void setAccountType(String str) {
        accountType = str;
    }

    public static String getMerchantName() {
        return merchantName;
    }

    public static void setMerchantName(String str) {
        merchantName = str;
    }

    public static String getMerchantDescriptor() {
        return merchantDescriptor;
    }

    public static void setMerchantDescriptor(String str) {
        merchantDescriptor = str;
    }

    public static Boolean isEnableResultAlert() {
        return enableResultAlert;
    }

    public static void setEnableResultAlert(Boolean bool) {
        enableResultAlert = bool;
    }

    public static String getResultAlertTextOk() {
        return resultAlertTextOk;
    }

    public static void setResultAlertTextOk(String str) {
        resultAlertTextOk = str;
    }

    public static String getResultAlertTextKo() {
        return resultAlertTextKo;
    }

    public static void setResultAlertTextKo(String str) {
        resultAlertTextKo = str;
    }

    public static String getResultAlertTextButtonOk() {
        return resultAlertTextButtonOk;
    }

    public static void setResultAlertTextButtonOk(String str) {
        resultAlertTextButtonOk = str;
    }

    public static String getResultAlertTextButtonKo() {
        return resultAlertTextButtonKo;
    }

    public static void setResultAlertTextButtonKo(String str) {
        resultAlertTextButtonKo = str;
    }

    public static String getResultAlertButtonColor() {
        return resultAlertButtonColor;
    }

    public static void setResultAlertButtonColor(String str) {
        resultAlertButtonColor = str;
    }

    public static boolean isEnableRedirection() {
        return enableRedirection;
    }

    public static void setEnableRedirection(boolean z) {
        enableRedirection = z;
    }

    public static Boolean getEnableResultAlert() {
        return enableResultAlert;
    }

    public static String getLoadingScreenBackgroundColor() {
        return loadingScreenBackgroundColor;
    }

    public static void setLoadingScreenBackgroundColor(String str) {
        loadingScreenBackgroundColor = str;
    }

    public static String getProgressBarColor() {
        return progressBarColor;
    }

    public static void setProgressBarColor(String str) {
        progressBarColor = str;
    }

    public static String getPrepaidCard() {
        return prepaidCard;
    }

    public static void setPrepaidCard(String str) {
        prepaidCard = str;
    }

    public static String getPartialPayment() {
        return partialPayment;
    }

    public static void setPartialPayment(String str) {
        partialPayment = str;
    }

    public static String getSumTotal() {
        return sumTotal;
    }

    public static void setSumTotal(String str) {
        sumTotal = str;
    }

    public static String getShippingAddressPyp() {
        return shippingAddressPyp;
    }

    public static void setShippingAddressPyp(String str) {
        shippingAddressPyp = str;
    }

    public static String getTaxReference() {
        return taxReference;
    }

    public static void setTaxReference(String str) {
        taxReference = str;
    }
}
