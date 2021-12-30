import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.Scanner;

public class Client {

    public static void main(String argv[]) throws Exception {

        Scanner keyboard = new Scanner(System.in);
        String url = keyboard.nextLine();

        System.out.println(getWebContentByGet(url, "iso-8859-1", 5000));
        System.out.println(getWebContentByPost(url, "2020078622/11","UTF-8", 5000));

    }

    public static String getWebContentByGet(String urlString, final String charset, int timeout) throws IOException {
        if (urlString == null || urlString.length() == 0) {
            return null;
        }
        urlString = (urlString.startsWith("http://") || urlString.startsWith("http://")) ? urlString
                : ("http://" + urlString).intern();
        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        conn.setRequestProperty("User-Agent", "2020078622/YEONGMINKANG/WebClient/ComNet");

        conn.setRequestProperty("Accept", "text/html");
        conn.setConnectTimeout(timeout);

        try {
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        InputStream input = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while((line = reader.readLine()) != null) {
            sb.append(line).append("\r\n");
        }
        if (reader != null) {
            reader.close();
        }
        if (conn != null) {
            conn.disconnect();
        }
        return sb.toString();
    }

    public static String getWebContentByPost(String urlString, String data, final String charset, int timeout) throws IOException {
        if (urlString == null || urlString.length() == 0) {
            return null;
        }
        urlString = (urlString.startsWith("http://") || urlString.startsWith("http://")) ? urlString : ("http://" + urlString).intern();
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");

        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);

        connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

        connection.setRequestProperty("User-Agent", "2020078622/YEONGMINKANG/WebClient/ComNet");

        connection.setRequestProperty("Accept", "text/xml");
        connection.setConnectTimeout(timeout);
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());


        byte[] content = data.getBytes("UTF-8");

        out.write(content);
        out.flush();
        out.close();

        try {
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        InputStream input = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while((line = reader.readLine()) != null) {
            sb.append(line).append("\r\n");
        }
        if (reader != null) {
            reader.close();
        }
        if (connection != null) {
            connection.disconnect();
        }
        return sb.toString();
    }




}
