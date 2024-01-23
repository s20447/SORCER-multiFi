package sorcer.arithmetic.provider.impl;

public class ShoppingSupplier {
    private String name;

    public ShoppingSupplier() {
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setParam1(String name) {
        if (!name.isEmpty()) {
            this.name = name;
        }
    }
}
