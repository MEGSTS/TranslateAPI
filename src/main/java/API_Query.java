import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;



public  class API_Query {

    String API_KEY = "trnsl.1.1.20190622T185300Z.4bc53ebe293481ed.85eac8bae6a9cc5a8c1e6feb2e2ee6862636231f";
    String lang = "ru-en";
    String request = "https://translate.yandex.net/api/v1.5/tr/translate?lang=" + lang + "&key=" + API_KEY;
    HttpsURLConnection connection = null;
    String sb = "";


    public String PostQuery(String text) {

        String urlParameters = "text=" + text;
        try {

            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            URL url = new URL(request);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            connection.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(postData);



            if (HttpsURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    sb += line;
                    sb += ("\n");
                }
            } else {
                System.out.println("fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return sb;
    }



    public String ParsersXML(String text)  {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(text)));
            NodeList nodeList = document.getElementsByTagName("Translation");
            Element element = (Element) nodeList.item(0);
            text = element.getElementsByTagName("text").item(0).getChildNodes().item(0).getNodeValue();
        }catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return text;
    }


}
