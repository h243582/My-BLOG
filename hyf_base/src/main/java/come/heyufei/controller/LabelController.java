package come.heyufei.controller;

import come.heyufei.pojo.Label;
import come.heyufei.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 标签控制层
 */
@RestController
@RequestMapping("label")
public class LabelController {
    @Autowired
    private LabelService labelService;


    /**
     * 查询全部列表
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
    }

    /**
     * 根据ID查询标签
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable String id) {
        System.out.println("No.1");
        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(id));
    }

    /**
     * 增加标签
     */
    @PostMapping
    public Result add(@RequestBody Label label) {
        labelService.add(label);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改标签
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Label label, @PathVariable String id) {
        label.setId(id);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable String id) {
        labelService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }


    /**
     * 条件查询
     * 可以根据 labelname / state / recommend查询
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Map map) {
        return new Result(true, StatusCode.OK, "查询成功",labelService.findSearch(map));
    }

    /**
     * 条件查询--分页
     * 可以根据 labelname / state / recommend查询
     */
    @PostMapping("/search/{page}/{size}") //第page页，一页size个
    public Result findSearch(@RequestBody Map map, @PathVariable int page,@PathVariable int size){
        Page<Label> pageList = labelService.findSearch(map, page, size); //这里面不止包含数据，还有其他一些东西，pageList.getContent()的返回才是数据
        return new Result(true, StatusCode.OK, "查询成功",new PageResult<Label>(pageList.getTotalElements(),pageList.getContent()));
    }
}