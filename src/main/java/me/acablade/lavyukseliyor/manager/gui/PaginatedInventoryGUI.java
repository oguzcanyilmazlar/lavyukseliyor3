package me.acablade.lavyukseliyor.manager.gui;

import lombok.Setter;
import me.acablade.lavyukseliyor.objects.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class PaginatedInventoryGUI extends InventoryGUI{
    private final int size;

    private int page = 1;
    private int maxPage = -1;
    @Setter
    private InventoryButton prevPageButton;
    @Setter
    private InventoryButton nextPageButton;
    @Setter
    private int nextPageSlot;
    @Setter
    private int prevPageSlot;

    public PaginatedInventoryGUI(int columnSize, int size){
        this.size = size;
        nextPageSlot = columnSize * 9 - 1;
        prevPageSlot = columnSize * 9 - 9;
        prevPageButton = new InventoryButton()
                .consumer(event -> {
                    this.page--;
                    this.decorate((Player) event.getWhoClicked());
                })
                .creator(player -> new ItemBuilder(Material.GREEN_WOOL)
                        .displayname("&a<- Previous page")
                        .lore("&7Page " + this.page + "/" + (getMaxPage()))
                        .build());



        nextPageButton = new InventoryButton()
                .consumer(event -> {
                    this.page++;
                    this.decorate((Player) event.getWhoClicked());
                })
                .creator(player -> new ItemBuilder(Material.GREEN_WOOL)
                        .displayname("&aNext page ->")
                        .lore("&7Page " + this.page + "/" + (getMaxPage()))
                        .build());

    }
    public abstract Collection<InventoryButton> getItems();

    private int getMaxPage(){
        if(maxPage == -1){
            this.maxPage = (int)Math.ceil(getItems().size() * 1.0 / size);
        }
        return this.maxPage;
    }

    @Override
    public void decorate(Player player) {
        this.removeButton(nextPageSlot);
        this.removeButton(prevPageSlot);
        AtomicInteger slot = new AtomicInteger(0);
        this.getItems().stream().skip((long) (page-1) * size).limit(size).forEach(inventoryButton -> this.addButton(slot.getAndIncrement(), inventoryButton));
        if(page < getMaxPage()) this.addButton(nextPageSlot, nextPageButton);
        if(page > 1) this.addButton(prevPageSlot, prevPageButton);
        super.decorate(player);
    }
}
