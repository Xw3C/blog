package com.cxw.web.admin;


import com.cxw.po.Type;
import com.cxw.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        //分页
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    //types更新和新增的页面共用
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }


    @GetMapping("/types/{id}/input")
    //根据ID查询
    public String editInput(@PathVariable Long id, Model model) {

        model.addAttribute("type", typeService.getType(id));  //返回前端的数据
        return "admin/types-input";
    }


    @PostMapping("/types")
    //types-input新增方法
    //需要通过RedirectAttributes下的addFlashAttribute方法，将message传到前端
    //@Valid 代表要校验Type对象 使用BindingResult可以接受到校验的结果
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        //校验如果新增的分类不为空，则校验不通过
        if (type1 != null) {
            //利用BindingResult返回信息到前端
            //自定义往result里面去加错误信息
            //rejectValue的name要和实体类中的对象一直
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        //如果为空，则校验不通过
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }


    @PostMapping("/types/{id}")
    //types-input更新方法
    //需要通过RedirectAttributes下的addFlashAttribute方法，将message传到前端
    //@Valid 代表要校验Type对象 使用BindingResult可以接受到校验的结果
    public String editPost(@Valid Type type, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        //校验如果新增的分类不为空，则校验不通过
        if (type1 != null) {
            //利用BindingResult返回信息到前端
            //自定义往result里面去加错误信息
            //rejectValue的name要和实体类中的对象一直
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        //如果为空，则校验不通过
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
