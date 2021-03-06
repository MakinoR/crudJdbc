package com.example.crudJdbc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
public class TestController {

    private JdbcTemplate jdbcTemplate;

    //コンストラクタ
    @Autowired
    public TestController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //一覧画面の表示
    @GetMapping
    public String index(Model model) {
        String sql = "SELECT * FROM test_table";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        model.addAttribute("testList", list);
        return "sample/index";
    }

    //新規入力フォームの表示
    @GetMapping("/form")
    public String form(@ModelAttribute TestForm testForm) {
        return "sample/form";
    }

    //新規入力データの保存
    @PostMapping("/form")
    public String create(TestForm testForm) {
        String sql = "INSERT INTO test_table(title, content) VALUES(?, ?);";
        jdbcTemplate.update(sql, testForm.getTitle(), testForm.getContent());
        return "redirect:/sample";
    }

    //編集フォームの表示
    @GetMapping("/edit/{id}")
    public String edit(@ModelAttribute TestForm testForm, @PathVariable int id) {
        String sql = "SELECT * FROM test_table WHERE id = " + id;
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        testForm.setId((int)map.get("id"));
        testForm.setTitle((String)map.get("title"));
        testForm.setContent((String)map.get("content"));
        return "sample/edit";
    }

    //編集データの保存
    @PostMapping("/edit/{id}")
    public String update(TestForm testForm, @PathVariable int id) {
        String sql = "UPDATE test_table SET title = ?, content = ? WHERE id = " + id;
        jdbcTemplate.update(sql, testForm.getTitle(), testForm.getContent());
        return "redirect:/sample";
    }

    //データの削除
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        String sql = "DELETE from test_table WHERE id = " + id;
        jdbcTemplate.update(sql);
        return "redirect:/sample";
    }
}
