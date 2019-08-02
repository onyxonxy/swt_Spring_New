package com.swt.controller.product;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swt.domain.product.ProductDTO;
import com.swt.service.product.ProductService;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequestMapping("product/*")
public class ProductController {
	@Inject
	private ProductService service;
	
	@GetMapping(value="teashop")
	public String teaShop(Model model) {
		log.info(">>> Tea 상품 리스트 페이지 이동");
		HashMap<String, List<ProductDTO>> map = service.productList();
		model.addAttribute("map",map);
		
		return "product/teashop";
	}
	
	@GetMapping(value="view")
	public String view(String p_code, Model model) {
		log.info(">>> 상품 상세 페이지 이동: "+p_code);
		ProductDTO pDto = service.productView(p_code);
		model.addAttribute("one",pDto);
		return "product/view";
	}
	
	@ResponseBody
	@PostMapping(value="wishCheck")
	public int wishCheck(String p_code, String id) {
		log.info(">>> 위시리스트 체크, 상품코드: "+p_code+",아이디: "+id);
		int resultVal = service.wishCheck(p_code,id);
		return resultVal;
	}
	
	@ResponseBody
	@PostMapping(value="wishUpdate")
	public void wishUpdate(String p_code, String id) {
		log.info(">>> 위시리스트 업데이트, 상품코드: "+p_code+",아이디: "+id);
		service.wishUpdate(p_code,id);
	}
	
	@GetMapping(value="wishView")
	public String wishView() {
		return "product/wish";
	}
	
	@GetMapping(value="wishList")
	public String wishList(HttpSession session, Model model) {
		String id = (String)session.getAttribute("userid");
		log.info(">>>> 위시리스트 목록 출력, 아이디: "+ id);
		List<HashMap<String, String>> list = service.wishList(id);
		model.addAttribute("wish", list);
		return "product/wish_list";
	}
	
	@ResponseBody
	@PostMapping(value="/wishDelete")
	public void wishDelete(String p_code, HttpSession session) {
		log.info(">>>>> 위시리스트 삭제");
		String id = (String)session.getAttribute("userid");
		service.wishDelete(p_code, id);
	}
}
