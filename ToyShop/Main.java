/*

Напишите проект для розыгрыша в магазине игрушек. Функционал должен содержать добавление новых игрушек и задания веса для выпадения игрушек.

 */

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, Integer> raffleClassList = new HashMap<>();
        raffleClassList.put(ToyTypes.CARMODEL.toString(), 10);
        raffleClassList.put(ToyTypes.CHILDRENDESIGNER.toString(), 20);
        raffleClassList.put(ToyTypes.DOLL.toString(), 50);
        raffleClassList.put(ToyTypes.ROBOT.toString(), 20);

        AnyToyFactory toyFactory = new AnyToyFactory();

        ToyShop shop = new ToyShop(raffleClassList);
        shop.addToy(toyFactory.createCarModel(2));
        shop.addToy(toyFactory.createCarModel(2));
        shop.addToy(toyFactory.createChildrenDesigner(1));
        shop.addToy(toyFactory.createChildrenDesigner(2));
        shop.addToy(toyFactory.createDoll(5));
        shop.addToy(toyFactory.createDoll(3));
        shop.addToy(toyFactory.createDoll(2));
        shop.addToy(toyFactory.createRobot(1));

        System.out.println(shop);

        System.out.println("====== РОЗЫГРЫШ ПРИЗОВ В МАГАЗИНЕ ИГРУШЕК ======");
        shop.showTypesAndChances();


        shop.raffleAllToys();


        shop.giveOutAllPrizes();
    }
}

