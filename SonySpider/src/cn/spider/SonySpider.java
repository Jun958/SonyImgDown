package cn.spider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SonySpider {

	public static void main(String[] args) {
		for (int i = 1; i < 5; i++) {
			String url = "http://www.sonystyle.com.cn/activity/wallpaper/2017/oct/0"+i+"_1920x1080.jpg";
			Set<String> ImgSrc = new HashSet<>();
			ImgSrc.add(url);
			Download(ImgSrc);			
		}
	}
	// 下载图片
	private static void Download(Set<String> ImgSrc) {
		try {
			// 开始时间
			Date begindate = new Date();
			for (String url : ImgSrc) {
				String imageName = url.substring(url.lastIndexOf("/") + 1,
						url.length());
					URL uri = new URL(url);
					InputStream in = uri.openStream();

					FileOutputStream fo = new FileOutputStream(new File("res/"
							+ imageName));
					byte[] buf = new byte[1024];
					int length = 0;
					System.out.println("开始下载:" + url);
					while ((length = in.read(buf, 0, buf.length)) != -1) {
						fo.write(buf, 0, length);
					}
					in.close();
					fo.close();
					System.out.println(imageName + "下载完成");
					Date overdate = new Date();
					double time = overdate.getTime() - begindate.getTime();
					System.out.println("总耗时：" + time / 1000 + "s");
				}
		} catch (Exception e) {
			System.out.println("下载失败");
		}
	}
}
