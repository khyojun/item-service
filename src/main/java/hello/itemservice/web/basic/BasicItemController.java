package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;


    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));

        itemRepository.save(new Item("itemB", 20000, 20));
    }



    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }


    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }




//    @PostMapping("/add")
//    public String addItemV1(@RequestParam String itemName,
//                       @RequestParam int price,
//                       @RequestParam Integer quantity,
//                       Model model
//
//    ){
//
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//
//        itemRepository.save(item);
//
//
//        model.addAttribute("item", item);
//
//        return "basic/item";
//    }

//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute("item") Item item, Model model)
//    {
//
//        itemRepository.save(item);
//
//
//     //   model.addAttribute("item", item); 생략가능 -> @ModelAttribute 에서 객체 item에 자동 추가
//
//        return "basic/item";
//    }

   /* @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item)
    {

        // Item -> 클래스명을 item으로 바꿔서 모델애트리뷰트에 자동으로 추가

        itemRepository.save(item);

        //   model.addAttribute("item", item); 생략가능 -> @ModelAttribute 에서 객체 item에 자동 추가

        return "basic/item";
    }*/


   /* @PostMapping("/add")
    public String addItemV4(Item item)
    {
        // 자동으로 @ModelAttribute 추가 파라미터에 객체가 왔으니 생략가능
        // Item -> 클래스명을 item으로 바꿔서 모델애트리뷰트에 자동으로 추가
        itemRepository.save(item);

        //   model.addAttribute("item", item); 생략가능 -> @ModelAttribute 에서 객체 item에 자동 추가
        // 새로고침할경우 마지막 으로 한 행위 추가 반복 중복 저장이 됨.
        return "basic/item";
    }*/



  /*  @PostMapping("/add")
    public String addItemV5(Item item)
    {
        // 자동으로 @ModelAttribute 추가 파라미터에 객체가 왔으니 생략가능
        // Item -> 클래스명을 item으로 바꿔서 모델애트리뷰트에 자동으로 추가
        itemRepository.save(item);

        //   model.addAttribute("item", item); 생략가능 -> @ModelAttribute 에서 객체 item에 자동 추가
        // 새로고침할경우 마지막 으로 한 행위 추가 반복 중복 저장이 됨.
        return "redirect:/basic/items/" + item.getId();
    }*/


    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes)
    {
        // 자동으로 @ModelAttribute 추가 파라미터에 객체가 왔으니 생략가능
        // Item -> 클래스명을 item으로 바꿔서 모델애트리뷰트에 자동으로 추가
       Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        //   model.addAttribute("item", item); 생략가능 -> @ModelAttribute 에서 객체 item에 자동 추가
        // 새로고침할경우 마지막 으로 한 행위 추가 반복 중복 저장이 됨.
        return "redirect:/basic/items/{itemId}";

        // status 는 url로 반환되지 않아서 남은것들은 쿼리 파라미터로 나오게 됨.
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";

    }


    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item){
       itemRepository.update(itemId, item);
       return "redirect:/basic/items/{itemId}";
    }


}
