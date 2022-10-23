package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {


    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void clear(){
        itemRepository.clearStore();
    }


    @Test
    void save(){
        //given
        Item item = new Item("itemA", 10000, 10);


        //when
        Item save = itemRepository.save(item);



        //then
        Item findItem = itemRepository.findById(item.getId());
        Assertions.assertThat(findItem).isEqualTo(findItem);

    }


    @Test
    void findAll(){
        //given
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 20000, 8);

        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when
        List<Item> result = itemRepository.findAll();


        //then

        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(itemA, itemB);

    }

    @Test
    void updateItem(){
        //given
        Item itemA = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(itemA);

        Long id = savedItem.getId();

        //when
        Item updateParam = new Item("itemB", 20000, 8);
        itemRepository.update(id, updateParam);



        //then

        Item findItem = itemRepository.findById(id);


        Assertions.assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());

        Assertions.assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());

        Assertions.assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());


    }





}