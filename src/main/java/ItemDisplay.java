import javax.swing.*;
import java.util.ArrayList;

public class ItemDisplay extends JPanel {
    public ItemDisplay(Item item)
    {
        //JLabel name = new JLabel(item.name);
        //JLabel price = new JLabel(item.price);
//        add(name);
//        add(price);
    }

}

class ItemListDisplay extends JPanel
{
    public ItemListDisplay() {
        //Get all item list

        ArrayList<Item> itemList = (ArrayList<Item>) Database.ItemGetAll().getData();


        for (Item i: itemList)
        {
            add(new ItemDisplay(i));
        }

    }
}

class VendingMachineDisplay extends JFrame{ //GUI FRAME
    private JPanel itemDisplayBox; //The panel in the gui form editor that contains the item list

    public void main(String[] args) //OnFinishLoading
    {
        itemDisplayBox.add(new ItemListDisplay());
    }
}