package co.edu.escuelaing.AppDistribuidaSegura;

import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SecureURLReader {

    public static void main(String[] args) {
    	try {

            // Create a file and a password representation
            File trustStoreFile = new File("keystores/...");
            char[] trustStorePassword = "123456".toCharArray();

            // Load the trust store, the default type is "pkcs12", the alternative is "jks"
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

            // Get the singleton instance of the TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            
            // Init the TrustManagerFactory using the truststore object
            tmf.init(trustStore);
            
            //Print the trustManagers returned by the TMF
            //only for debugging
            for(TrustManager t: tmf.getTrustManagers()){
                System.out.println(t);
            }
            
            //Set the default global SSLContext so all the connections will use it
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);
            
            // We can now read this URL
            readURL("https://localhost:5000/hello");

            // This one can't be read because the Java default truststore has been 
            // changed.
            readURL("https://www.google.com");         
        
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException
                | KeyManagementException ex) {
            Logger.getLogger(SecureURLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String readURL(String sitetoread) {
        try {
            // Crea el objeto que representa una URL
            URL siteURL = new URL(sitetoread);
            // Crea el objeto que URLConnection
            URLConnection urlConnection = siteURL.openConnection();
            // Obtiene los campos del encabezado y los almacena en un estructura Map
            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            // Obtiene una vista del mapa como conjunto de pares <K,V>
            // para poder navegarlo
            Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
            // Recorre la lista de campos e imprime los valores
            for (Map.Entry<String, List<String>> entry : entrySet) {
                String headerName = entry.getKey();

                //Si el nombre es nulo, significa que es la linea de estado
                if (headerName != null) {
                    System.out.print(headerName + ":");
                }
                List<String> headerValues = entry.getValue();
                for (String value : headerValues) {
                    System.out.print(value);
                }
                System.out.println("");
            }

            System.out.println("-------message-body------");
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine = null;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                response.append(inputLine);
            }
            reader.close();
            return reader.toString();
        } catch (IOException x) {
            System.err.println(x);
            return "Se ha presentado un error";
        }
    }

    public static String putURL(String sitetopost, String parameters) {
        try {
            URL siteURL = new URL(sitetopost);
            HttpURLConnection con = (HttpURLConnection) siteURL.openConnection();

            // Se añaden los encabezados de la solicitud
            con.setRequestMethod("PUT");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Enviar solicitud
            con.setDoOutput(true);
            DataOutputStream write = new DataOutputStream(con.getOutputStream());
            write.writeBytes(parameters);
            write.flush();
            write.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + sitetopost);
            System.out.println("Post parameters : " + parameters);
            System.out.println("Response Code : " + responseCode);

            System.out.println("-------message-body------");
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine = null;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                response.append(inputLine);
            }
            reader.close();
            return response.toString();
        } catch (IOException x) {
            System.err.println(x);
            return "Se ha presentado un error";
        }
    }
}