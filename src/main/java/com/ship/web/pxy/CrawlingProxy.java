package com.ship.web.pxy;

import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ship.web.brd.Article;
import com.ship.web.brd.ArticleMapper;
import com.ship.web.enums.Path;
import com.ship.web.tx.TxMapper;

@Lazy
@Component("crawler")
public class CrawlingProxy extends Proxy {
	@Autowired Box<String> box;
	@Autowired TxMapper txMapper;
	@Autowired ArticleMapper atm;
	@Autowired Trunk<Object> map;
	
	public Box<String> choose(String url) {
		box.clear();
		switch (url) {
		case "bugs":
			return bugsCrawling(Path.CRAWLING_BUGS.toString());
		case "cgv":
			return cgvCrawling(Path.CRAWLING_CGV.toString());
		case "eng":
			return engCrawling(Path.CRAWLING_ENG.toString());
		default: break;
		}
        return null;
    }
	
    private Box<String> bugsCrawling(String url) {
        try {
            Document rawData = Jsoup.connect(url).timeout(10 * 1000).get();
            Elements rank = rawData.select("div[class=\"ranking\"] strong");
            Elements artist = rawData.select("p[class=\"artist\"] a");
            Elements title = rawData.select("p[class=\"title\"] a");
            for(int i = 0 ; i < rank.size(); i++) {
                box.add(String.format("%s위:  %s   /   %s\n", rank.get(i).text(), artist.get(i).text(), title.get(i).text()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return box;
    }
    
	@Transactional
	private Box<String> cgvCrawling(String url) {
		map.put(Arrays.asList("tabname"),Arrays.asList("cgv"));
		atm.dropArticle(map.get());
		atm.createArticle(map.get());
		String temp = "";
		try {
			Document rawData = Jsoup.connect(url).timeout(10 * 1000).get();
			Elements artist = rawData.select("a strong[class=\"title\"]");
			Elements num = rawData.select("div strong[class=\"rank\"]");
			for (int i = 0; i < num.size(); i++) {
				temp = String.format("%s위 : %s\n", num.get(i).text().replace("No.", ""), artist.get(i).text());
				box.add(temp);
				atm.insertArticle(new Article(temp,i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return box;
	}

	private Box<String> engCrawling(String url) {
		try {
			Document rawData = Jsoup.connect(url).timeout(10 * 1000).get();
			Elements artist = rawData.select("div[class=\"txt_origin\"] a");
			for (Element e : artist) {
				box.add(e.text());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return box;
	}
}