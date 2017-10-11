package cn.spider;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstTest {

	// 地址
	// 编码
	private static final String ECODING = "UTF-8";
	// 获取img标签正则
	private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
	// 获取src路径的正则
	private static final String IMGSRC_REG = "[a-zA-z]+://[^\\s]*";

	public static void main(String[] args) {
		try {
			FirstTest cm = new FirstTest();
			String baseURL = "http://www.tooopen.com/view/";
			String mark = "";
			for (Integer i = 1439719; i < 1439720; i++) {
				mark = baseURL+i.toString()+".html";
				// 获得html文本内容
				String HTML = cm.getHtml(mark);
				// 获取图片标签
				Set<String> imgUrl = cm.getImageUrl(HTML);
				// 获取图片src地址
				Set<String> imgSrc = cm.getImageSrc(imgUrl);
				// 下载图片
				cm.Download(imgSrc);
			}
			String URL = "http://www.tooopen.com/view/"+mark+".html";
			

		} catch (Exception e) {
			System.out.println("发生错误");
		}

	}

	// 获取HTML内容
	private String getHtml(String url) throws Exception {
		URL url1 = new URL(url);
		URLConnection connection = url1.openConnection();
		InputStream in = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line, 0, line.length());
			sb.append('\n');
		}
		br.close();
		isr.close();
		in.close();
		return sb.toString();
	}

	// 获取ImageUrl地址
	private Set<String> getImageUrl(String html) {
		Matcher matcher = Pattern.compile(IMGURL_REG).matcher(html);
		Set<String> listimgurl = new HashSet<String>();
		while (matcher.find()) {
			listimgurl.add(matcher.group());
		}
		return listimgurl;
	}

	// 获取ImageSrc地址
	private Set<String> getImageSrc(Set<String> listimageurl) {
		Set<String> listImageSrc = new HashSet<String>();
		for (String image : listimageurl) {
			Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
			while (matcher.find()) {
				listImageSrc.add(matcher.group().substring(0,
						matcher.group().length() - 1));
			}
		}
		return listImageSrc;
	}

	// 下载图片
	private void Download(Set<String> listImgSrc) {
		try {
			// 开始时间
			Date begindate = new Date();
			for (String url : listImgSrc) {
				String imageName = url.substring(url.lastIndexOf("/") + 1,
						url.length());
				if (imageName.startsWith("tooopen_sy")) {
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
			}
		} catch (Exception e) {
			System.out.println("下载失败");
		}
	}
}