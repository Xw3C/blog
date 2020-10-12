package com.cxw.web.admin;

import com.cxw.po.Tag;
import com.cxw.service.TagService;
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
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        //分页
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    //tag修改方法 和新增的页面共用 
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }


    @GetMapping("/tags/{id}/input")
    //根据ID查询要编辑的记录
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    //标签
    //tags-input新增方法
    //需要通过RedirectAttributes下的addFlashAttribute方法，将message传到前端
    //@Valid 代表要校验Tag对象 使用BindingResult可以接受到校验的结果
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        //校验如果新增的标签不为空，则校验不通过,代表已存在
        if (tag1 != null) {
            //利用BindingResult返回信息到前端
            //自定义往result里面去加错误信息
            //rejectValue的name要和实体类中的对象一直
            result.rejectValue("name","nameError","该标签已存在");
        }
        //如果为空，则校验不通过
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }


    @PostMapping("/tags/{id}")
    //tags-input更新方法
    //需要通过RedirectAttributes下的addFlashAttribute方法，将message传到前端
    //@Valid 代表要校验tag对象 使用BindingResult可以接受到校验的结果
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        //校验如果新增的分类不为空，则校验不通过
        if (tag1 != null) {
            //利用BindingResult返回信息到前端
            //自定义往result里面去加错误信息
            //rejectValue的name要和实体类中的对象一直
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        //如果为空，则校验不通过
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }





    @GetMapping("/tags/{id}/delete")
    //删除方法
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

}
