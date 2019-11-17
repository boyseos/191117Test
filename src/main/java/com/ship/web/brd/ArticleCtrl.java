package com.ship.web.brd;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ship.web.pxy.PageProxy;
import com.ship.web.pxy.Trunk;
import com.ship.web.pxy.Box;

@RestController
@RequestMapping("/{artname}")
public class ArticleCtrl {
	private static final Logger logger = LoggerFactory.getLogger(ArticleCtrl.class);
	@Autowired ArticleMapper artMapper;
	@Autowired Box<Article> box;
	@Autowired Trunk<Object> trunk;
	@Autowired PageProxy pager;
	
	@GetMapping("/page/{pageno}/size/{pageSize}")
	public Map<?,?>  list(@PathVariable String artname, @PathVariable String pageno,
			@PathVariable String pageSize){
		System.out.println("넘어온 페이지 넘버: "+pageno);
		pager.setArtname(artname);
		pager.setPageNum(pager.integer(pageno));
		pager.setPageSize(pager.integer(pageSize));
		pager.paging();
		trunk.put(Arrays.asList("pxy"), Arrays.asList(pager));
		Supplier<List<?>> s = () -> artMapper.selectList(trunk.get());
		trunk.put(Arrays.asList(artname, "pxy"), Arrays.asList(s.get(),pager));
		return trunk.get();
	}
}
