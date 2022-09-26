package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final MemberService memberService;


    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    //상품 등록
    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAll_ingredient(form.getAll_ingredient());
        //book.setIngre_category(form.getIngre_category());
        //book.setPage(form.getPage());
        //book.setGoodImage(form.getGoodImage());

        itemService.saveItem(book);
        return "redirect:/";
    }

    //itemList.html에 맵핑, 상품 목록에서 멤버의 데이터도 보여줘야함 ****
    @GetMapping("/items")
    public String list(Model model){

        //itemService로 부터 리스트를 받아옴.
        List<Item> items=itemService.findItems();
        List<Member> members = memberService.findMembers();

        // 모델을 통해 itemlist 페이지로 데이터 전달
        model.addAttribute("items", items);
        model.addAttribute("members", members);

        return "items/itemList";
    }

    /**
     * 상품 수정 폼
     */
    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model
            model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAll_ingredient(item.getAll_ingredient());
        //form.setIngre_category(form.getIngre_category());
        //form.setPage(form.getPage());

        //form.setGoodImage(item.getGoodImage());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    /**
     * 상품 수정
     */
    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity(), form.getAll_ingredient());
        return "redirect:/items";
    }

    //상품 목록에서 주문
    @PostMapping(value = "/items/{itemId}/buy")
    public String updateItembuy(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity(), form.getAll_ingredient());
        return "order/orderForm";
    }
}
