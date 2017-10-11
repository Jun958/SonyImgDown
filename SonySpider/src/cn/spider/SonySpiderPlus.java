package cn.spider;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/*
 * 抓取索尼壁纸带日历(需commons-io类)
 */
public class SonySpiderPlus {

	static String[] months = {"feb","jan","mar","apr","may","jun","jul","aug","sep","oct","nov","dec"};
	// 年
	static String[] years = {"2010","2011","2012","2013","2014","2015","2016","2017" };
	// "feb","jan","mar","apr","may","jun","jul","aug","sep","oct","nov","dec"
	public static void main(String[] args) throws InterruptedException {
		// 创建目录
		mkDir();
		for (int i = 0; i < years.length; i++) {
			for (int j = 0; j < months.length; j++) {
				for (int m = 1; m < 5; m++) {
					String basic = "http://www.sonystyle.com.cn/activity/wallpaper/";
					String url = basic + years[i] + "/" + months[j] + "/0" + m
							+ "_1920x1080.jpg";
					Set<String> ImgSrc = new HashSet<>();
					ImgSrc.add(url);
					Download(ImgSrc);
				}
			}
		}
	}
	// 创建文件夹
	public static void mkDir() {
		for (int i = 0; i < years.length; i++) {
			for (int j = 0; j < months.length; j++) {
				File file = new File("img/" + years[i] + "/" + months[j]);
				if (file.isDirectory()) {
					System.out.println(file.getName() + "目录已经存在");
				} else if (file.mkdirs()) {
					System.out.println("目录创建成功");
				}
			}
		}
	}
	// 下载图片
	private static void Download(Set<String> ImgSrc) {
		try {
			for (String url : ImgSrc) {
				String imageName = url.substring(url.lastIndexOf("/") + 1,
						url.length());
				URL uri = new URL(url);
				File file = null;
				for (int i = 0; i < years.length; i++) {
					for (int j = 0; j < months.length; j++) {
						file = new File("img/" +years[i]+"/"+months[j]+"/"+imageName);
						if(url.contains(years[i]) && url.contains(months[j])) {
							System.out.println(file.getAbsolutePath());
							FileUtils.copyURLToFile(uri, file);
						}
					}
				}
				System.out.println(imageName + "下载完成");
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			System.out.println("下载失败");
			e.printStackTrace();
		}
	}
}
