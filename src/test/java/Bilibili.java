import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Bilibili {
    public static void main (String[] args) {
        System.out.println("""
                  ==============================================
                ||KKoishi的自动爬取睿站弹幕爬虫~
                ||制作时间 2021/11/7
                ||作者个人网站 http://kkoishi-514.top
                ||作者的GitHub https//github.com/Koishi-Satori
                ||请勿乱爬 违反法律后果由使用者自行承担
                ||基于Jsoup库&欢迎rua秋
                  =============================================
                """);
        System.out.println("请输入视频av号的数字部分|如果查找单个视频请输入两个相同数字");
        Scanner scanner = new Scanner(System.in);
        System.out.print("开始数字");
        int start = scanner.nextInt();
        System.out.print("终止数字");
        int end = scanner.nextInt();
        scanner.close();
        for (int i = start;i<end;i++) {
            try {
                GetCid cid = new GetCid();
                bilibili(cid.getCid("av" + i),cid.getName("av" + i));
                System.out.println("爬取视频弹幕成功:av" + i);
                System.out.println("---------------------分割线----------------------");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("爬取失败！可能视频[av" + i + "]不存在/已消失");
            }
        }
        System.out.println("爬取结束");
    }

    public static void bilibili(String cid,String name) {
            try {
                Document document = Jsoup.connect("http://comment.bilibili.com/"+ cid +".xml").get();
                Elements element = document.getElementsByTag("d");
                File file = new File("C:\\Users\\DELL\\Desktop\\spider\\" + name + ".txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream outputStream = new FileOutputStream(file);
                for (Element e : element) {
                    outputStream.write((e.text() + "\r\n").getBytes());
                }
                outputStream.close();
            } catch (IOException e) {

            }
    }
}
