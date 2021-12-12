package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void getId() {
        Item item1 = new Item(1, "label", "12-08-2021", "12:22", 0, "information");
        assertEquals(1, item1.getId());
        Item item2 = new Item(1, "label", "12-08-2021", "12:22", 0);
        assertEquals(1, item2.getId());
        Item item3 = new Item();
        assertEquals(0, item3.getId());
        Item item4 = new Item("label", "12-08-2021", "12:22", 0);
        assertEquals(0, item4.getId());
    }

    @Test
    void setId() {
        Item item1 = new Item(1, "label", "12-08-2021", "12:22", 0, "information");
        item1.setId(2);
        assertEquals(2, item1.getId());
        Item item2 = new Item(1, "label", "12-08-2021", "12:22", 0);
        item2.setId(2);
        assertEquals(2, item2.getId());
        Item item3 = new Item();
        item3.setId(2);
        assertEquals(2, item3.getId());
        Item item4 = new Item("label", "12-08-2021", "12:22", 0);
        item4.setId(2);
        assertEquals(2, item4.getId());
    }

    @Test
    void getLabel() {
        Item item1 = new Item(1, "label", "12-08-2021", "12:22", 0, "information");
        assertEquals("label", item1.getLabel());
        Item item2 = new Item(1, "label", "12-08-2021", "12:22", 0);
        assertEquals("label", item2.getLabel());
        Item item3 = new Item();
        assertNull(item3.getLabel());
        Item item4 = new Item("label", "12-08-2021", "12:22", 0);
        assertEquals("label", item4.getLabel());
    }

    @Test
    void setLabel() {
        Item item1 = new Item(1, "label", "12-08-2021", "12:22", 0, "information");
        item1.setLabel("new");
        assertEquals("new", item1.getLabel());
        Item item2 = new Item(1, "label", "12-08-2021", "12:22", 0);
        item2.setLabel("new");
        assertEquals("new", item2.getLabel());
        Item item3 = new Item();
        item3.setLabel("new");
        assertEquals("new", item3.getLabel());
        Item item4 = new Item("label", "12-08-2021", "12:22", 0);
        item4.setLabel("new");
        assertEquals("new", item4.getLabel());
    }

    @Test
    void getDate() {
        Item item1 = new Item(1, "label", "12-08-2021", "12:22", 0, "information");
        assertEquals("12-08-2021", item1.getDate());
        Item item2 = new Item(1, "label", "12-08-2021", "12:22", 0);
        assertEquals("12-08-2021", item2.getDate());
        Item item3 = new Item();
        assertNull(item3.getDate());
        Item item4 = new Item("label", "12-08-2021", "12:22", 0);
        assertEquals("12-08-2021", item4.getDate());
    }

    @Test
    void setDate() {
        Item item1 = new Item(1, "label", "12-08-2021", "12:22", 0, "information");
        item1.setDate("15-03-2021");
        assertEquals("15-03-2021", item1.getDate());
        Item item2 = new Item(1, "label", "12-08-2021", "12:22", 0);
        item2.setDate("15-03-2021");
        assertEquals("15-03-2021", item2.getDate());
        Item item3 = new Item();
        item3.setDate("15-03-2021");
        assertEquals("15-03-2021", item3.getDate());
        Item item4 = new Item("label", "12-08-2021", "12:22", 0);
        item4.setDate("15-03-2021");
        assertEquals("15-03-2021", item4.getDate());
    }

    @Test
    void getTime() {
        Item item1 = new Item(1, "label", "12-08-2021", "12:22", 0, "information");
        assertEquals("12:22", item1.getTime());
        Item item2 = new Item(1, "label", "12-08-2021", "12:22", 0);
        assertEquals("12:22", item2.getTime());
        Item item3 = new Item();
        assertNull(item3.getTime());
        Item item4 = new Item("label", "12-08-2021", "12:22", 0);
        assertEquals("12:22", item4.getTime());
    }

    @Test
    void setTime() {
        Item item1 = new Item(1, "label", "12-08-2021", "12:22", 0, "information");
        item1.setTime("15:44");
        assertEquals("15:44", item1.getTime());
        Item item2 = new Item(1, "label", "12-08-2021", "12:22", 0);
        item2.setTime("15:44");
        assertEquals("15:44", item2.getTime());
        Item item3 = new Item();
        item3.setTime("15:44");
        assertEquals("15:44", item3.getTime());
        Item item4 = new Item("label", "12-08-2021", "12:22", 0);
        item4.setTime("15:44");
        assertEquals("15:44", item4.getTime());
    }

    @Test
    void getCheck() {
        Item item1 = new Item(1, "label", "12-08-2021", "12:22", 0, "information");
        assertEquals(0, item1.getCheck());
        Item item2 = new Item(1, "label", "12-08-2021", "12:22", 0);
        assertEquals(0, item2.getCheck());
        Item item3 = new Item();
        assertEquals(0, item3.getCheck());
        Item item4 = new Item("label", "12-08-2021", "12:22", 0);
        assertEquals(0, item4.getCheck());
    }

    @Test
    void setCheck() {
        Item item1 = new Item(1, "label", "12-08-2021", "12:22", 0, "information");
        item1.setCheck(1);
        assertEquals(1, item1.getCheck());
        Item item2 = new Item(1, "label", "12-08-2021", "12:22", 0);
        item2.setCheck(1);
        assertEquals(1, item2.getCheck());
        Item item3 = new Item();
        item3.setCheck(1);
        assertEquals(1, item3.getCheck());
        Item item4 = new Item("label", "12-08-2021", "12:22", 0);
        item4.setCheck(1);
        assertEquals(1, item4.getCheck());
    }

    @Test
    void getInformFriend() {
        Item item1 = new Item(1, "label", "12-08-2021", "12:22", 0, "information");
        assertEquals("information", item1.getInformFriend());
        Item item2 = new Item(1, "label", "12-08-2021", "12:22", 0);
        assertNull(item2.getInformFriend());
        Item item3 = new Item();
        assertNull(item3.getInformFriend());
        Item item4 = new Item("label", "12-08-2021", "12:22", 0);
        assertNull(item4.getInformFriend());
    }
}