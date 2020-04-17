package com.example.inncoffee.ui.pago.tpvvinapplibrary.data.remote.ssl;

import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class TPVVSSLFactory extends SSLSocketFactory {
    private static SSLContext sslContext;

    public TPVVSSLFactory() throws NoSuchAlgorithmException, KeyManagementException, IOException {
        init();
    }

    public String[] getDefaultCipherSuites() {
        return sslContext.getSocketFactory().getDefaultCipherSuites();
    }

    public String[] getSupportedCipherSuites() {
        return sslContext.getSocketFactory().getSupportedCipherSuites();
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        return sslContext.getSocketFactory().createSocket(socket, str, i, z);
    }

    public Socket createSocket(String str, int i) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(str, i);
    }

    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(str, i, inetAddress, i2);
    }

    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return sslContext.getSocketFactory().createSocket(inetAddress, i);
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return sslContext.getSocketFactory().createSocket(inetAddress, i, inetAddress2, i2);
    }



    private static void init() throws NoSuchAlgorithmException, KeyManagementException, IOException {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            CertificateFactory instance = CertificateFactory.getInstance("X.509");
            KeyStore instance2 = KeyStore.getInstance(KeyStore.getDefaultType());
            instance2.load((InputStream) null, (char[]) null);
            for (String decode : Certificates.CAs) {
                byteArrayInputStream = new ByteArrayInputStream(Base64.decode(decode, 0));
                Certificate generateCertificate = instance.generateCertificate(byteArrayInputStream);
                System.out.println("ca=" + ((X509Certificate) generateCertificate).getSubjectDN());
                byteArrayInputStream.close();
                instance2.setCertificateEntry(((X509Certificate) generateCertificate).getSubjectDN().toString(), generateCertificate);
            }
            TrustManagerFactory instance3 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance3.init(instance2);
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init((KeyManager[]) null, instance3.getTrustManagers(), (SecureRandom) null);
        } catch (CertificateException e) {
            throw new KeyManagementException(e.getMessage());
        } catch (KeyStoreException e2) {
            throw new KeyManagementException(e2.getMessage());
        } catch (IOException e3) {
            throw new KeyManagementException(e3.getMessage());
        } catch (Throwable th) {
            byteArrayInputStream.close();
            throw th;
        }
    }
}
