package me.acablade.templateplugin.manager.gui;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryGUI implements InventoryHandler {

    @Getter
    private final Inventory inventory;
    private final Int2ObjectOpenHashMap<InventoryButton> buttonMap = new Int2ObjectOpenHashMap<>();

    public InventoryGUI(){
        this.inventory = this.createInventory();
    }

    public void addButton(int slot, InventoryButton inventoryButton){
        this.buttonMap.put(slot, inventoryButton);
    }

    public void decorate(Player player){
        this.inventory.clear();
        this.buttonMap.forEach((slot, button) -> {
            ItemStack icon = button.getIconCreator().apply(player);
            this.inventory.setItem(slot, icon);
        });
    }

    public void removeButton(int slot){
        buttonMap.remove(slot);
    }

    @Override
    public void onClick(InventoryClickEvent event){
        event.setCancelled(true);
        int slot = event.getSlot();
        InventoryButton button = this.buttonMap.get(slot);
        if(button != null){
            button.getConsumer().accept(event);
        }
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        this.decorate((Player) event.getPlayer());
    }

    @Override
    public void onClose(InventoryCloseEvent event) {}

    protected abstract Inventory createInventory();


}
