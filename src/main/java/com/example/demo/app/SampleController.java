package com.example.demo.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
public class SampleController {

	// 通常であれば、newをしなければビルドエラーが発生するが、
	// 以下コンストラクタの@AutowiredでjdbcTemplateを引数で受け取っているので、ビルドエラーが発生しない
	// Spring独特のおまじない
	// 理由はDI テストの為
	// インスタンス生成をフレームワーク機能に任せることで単体テストをやりやすくする
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public SampleController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@GetMapping("/test")
	public String test(Model model) {

		// SQL文をqueryForMapでSQL実行。結果をMapで受け取る
		// Map型のインスタンスを取得する
		String sql = "SELECT id, name, email FROM inquiry WHERE id = 1";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);

		model.addAttribute("title", "Inquiry Form");
		model.addAttribute("name", map.get("name"));
		model.addAttribute("email", map.get("email"));

		return "test";
	}
}
