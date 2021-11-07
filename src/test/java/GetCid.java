import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetCid {
    private static final String regex = "\"cids\":\\{\"1\":\\d+}";
    private static final String replaceRegex = "\"cids\":\\{\"1\":";

    @Deprecated
    public String getConnection(String webPath) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(webPath).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            if (connection.getResponseCode() == 200) {
                InputStream xml = connection.getInputStream();
                byte[] shushuwoa = read(xml);
                return new String(shushuwoa);
            } else {
                System.out.println("爬取源码失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "陈睿nmsl";
    }

    @Deprecated
    private byte[] read(@NotNull InputStream xml) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bits = new byte[1024];
        int len = 0;
        while ((len = xml.read(bits)) != -1) {
            outputStream.write(bits,0,len);
        }
        xml.close();
        return outputStream.toByteArray();
    }

    public String getCid(String BVorAVid) throws IOException {
        String result = "";
        Document document = Jsoup.connect("https://www.bilibili.com/video/" + BVorAVid).get();
        Pattern pattern = Pattern.compile(regex);
        String temp = document.outerHtml();
        Matcher matcher = pattern.matcher(temp);

        while (matcher.find()) {
            result = temp.substring(matcher.start(),matcher.end());
        }

        result = result.replaceAll(replaceRegex,"");
        result = result.replaceAll("}","");
        return result;
    }

    public String getName(String BVorAVid) throws IOException {
        Document document = Jsoup.connect("https://www.bilibili.com/video/" + BVorAVid).get();
        return document.title() + "弹幕列表";
    }
}
