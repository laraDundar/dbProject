package pizzaSoftware;

public class Ingredient {
    private int ingredientId;
    private String ingredientName;
    private double price;
    private boolean isVegetarian;
    private boolean isVegan;

    //this is ok
    //getters and setters for Ingredient

    public Ingredient (int ingredient_id, String ingredientName, double price, boolean isVegetarian, boolean isVegan){
        this.ingredientId = ingredient_id;
        this.ingredientName = ingredientName;
        this.price = price;
        this.isVegan = isVegan;
        this.isVegetarian = isVegetarian;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }
}
