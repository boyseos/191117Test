package com.ship.web.tx;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ship.web.pxy.Box;
import com.ship.web.pxy.CrawlingProxy;
import com.ship.web.pxy.PageProxy;
import com.ship.web.pxy.Trunk;

@RestController
@Transactional
@RequestMapping("/tx")
public class TxController {
	@Autowired
	Trunk<Object> trunk;
	@Autowired
	Box<?> box;
	@Autowired
	CrawlingProxy crawler;
	@Autowired
	TxService txService;
	@Autowired
	PageProxy pager;

	@GetMapping("/bugscrawling")
	public Box<?> bringBugs() {
		return txService.crawling("bugs");
	}

	@GetMapping("/cgvcrawling")
	public Map<?, ?> bringCgv() {
		trunk.put(Arrays.asList("list"), Arrays.asList(txService.crawling("cgv").get()));
		return trunk.get();
	}
	
	@GetMapping("/engcrawling")
    public Box<?> bringEng(){
        return txService.crawling("eng");
    }
}