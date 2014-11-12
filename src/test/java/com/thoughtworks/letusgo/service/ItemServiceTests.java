package com.thoughtworks.letusgo.service;

import com.thoughtworks.letusgo.dao.ItemDao;
import com.thoughtworks.letusgo.dao.impl.ItemDaoImpl;
import com.thoughtworks.letusgo.entity.Category;
import com.thoughtworks.letusgo.entity.Item;
import com.thoughtworks.letusgo.service.impl.ItemServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemServiceTests {

    private final List<Item> items = new ArrayList<Item>();

    private ItemService itemService;
    private ItemDao mockItemDao;

    @Before
    public void initItems() {
        Item item1 = new Item(1, "Item000001", "雪碧", "瓶", 3.0, new Category(1, "饮料"));
        Item item2 = new Item(2, "Item000002", "可乐", "瓶", 3.0, new Category(1, "饮料"));
        items.add(item1);
        items.add(item2);

        mockItemDao = mock(ItemDaoImpl.class);
        when(mockItemDao.getItems()).thenReturn(items);
        when(mockItemDao.getItemById("Item000002")).thenReturn(item2);

        itemService = new ItemServiceImpl(mockItemDao);
    }

    @Test
    public void should_return_all_items() {
        List<Item> result = itemService.getItems();
        assertEquals(2, result.size());
    }

    @Test
    public void should_return_item_by_id() {
        Item result = itemService.getItemById(2);
        assertEquals("可乐", result.getName());
    }

    @Test
    public void should_delete_item_by_id() {
        itemService.deleteItemById(2);
        verify(mockItemDao).deleteItemById(2);
    }

    @Test
    public void should_insert_item() {
        Item item = new Item(3, "Item000003", "果粒橙", "瓶", 3.0, new Category(1, "饮料"));
        itemService.addItem(item);
        verify(mockItemDao).addItem(item);
    }

    @Test
    public void should_update_item() {
        Item item = new Item(2, "Item000002", "可口可乐", "瓶", 3.0, new Category(1, "饮料"));
        itemService.updateItem(2, item);
        verify(mockItemDao).updateItem(2, item);
    }
}
