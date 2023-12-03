import java.util.*;

public class ToyShop {
    private Map<String, ArrayList<Toy>> warehouse;
    private Map<String, Integer> raffleToyTypesList;
    private Deque<Toy> prizesQueue;
    private Logger logger = new Logger();

    public ToyShop(Map<String, Integer> raffleClassList) {
        this.warehouse = new HashMap<>();
        this.raffleToyTypesList = raffleClassList;
        this.prizesQueue = new ArrayDeque<>();
        typesListVerify();
    }

    private void typesListVerify(){
        Iterator<Map.Entry<String, Integer>> iterator = raffleToyTypesList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> pair = iterator.next();
            Integer chance = pair.getValue();
            if (chance < 1) {
                iterator.remove();
            }
        }
    }

    public void addToy(Toy newToy) {
        String newToyClass = newToy.getClass().getSimpleName();

        if (warehouse.containsKey(newToyClass)) {
            warehouse.get(newToyClass).add(newToy);
        } else {
            warehouse.put(newToyClass, new ArrayList<>(Collections.singletonList(newToy)));
        }
    }

    public Toy raffleToy() {
        String message = "";
        if (!warehouse.isEmpty()) {
            while (!raffleToyTypesList.isEmpty()) {
                Toy raffledToy = getToyFromShop(getToyTypeWithChance());
                if (raffledToy != null) {
                    prizesQueue.offer(raffledToy);
                    System.out.println("       Разыграно: " + raffledToy + ", остаток " + raffledToy.getCount());
                    //System.out.println("       ОЧЕРЕДЬ: " + prizesQueue);
                    return raffledToy;
                }
            }
            message = "( < ! > ) Лотерея закончилась. Все призы разыграны.";
        } else {
            message = "( < ! > ) В магазине не осталось игрушек. Разыгрывать больше нечего (>.<)";
        }
        System.out.println(message);
        // logger.writeLogFile(message);
        return null;
    }


    public void raffleAllToys(){
        while(raffleToy() != null){
            raffleToy();
        }
    }


    private String getToyTypeWithChance() {
        NavigableMap<Integer, String> chances = new TreeMap<>();
        int chancesSum = 0;
        for (Map.Entry<String, Integer> entry : raffleToyTypesList.entrySet()) {
            String item = entry.getKey();
            Integer chance = entry.getValue();
            if (chance > 0) {
                chancesSum += chance;
                chances.put(chancesSum, item);
            }
        }
        if (!chances.isEmpty()){
            return chances.ceilingEntry(new Random().nextInt(chancesSum + 1)).getValue();
        } else {
            return null;
        }
    }


    private Toy getToyFromShop(String toyType) { //
        if (toyType == null){
            return null;
        }
        ArrayList<Toy> currentToysList = warehouse.get(toyType);
        while (true) {
            if (!currentToysList.isEmpty()) {
                int currentToyIndex = new Random().nextInt(currentToysList.size());
                Toy currentToy = currentToysList.get(currentToyIndex);
                System.out.println("Попытка разыграть игрушку " + currentToy + ". ");
                if (currentToy.getCount() > 0) {
                    currentToy.decreaseCount();
                    return currentToy;
                } else {
                    System.out.println("       Игрушки " + currentToy + " закончились. Поищем похожую.");
                    currentToysList.remove(currentToyIndex);
                }
            } else {
                warehouse.remove(toyType);
                raffleToyTypesList.remove(toyType);
                System.out.println("       Игрушки типа " + toyType + ", к сожалению, уже закончились.");
                return null;
            }
        }
    }

    public void giveOutPrize() { 
        if (!prizesQueue.isEmpty()) {
            Toy prize = prizesQueue.poll();
            String message = "Выдан приз - " + prize;
            System.out.println(message);
            logger.writeToLog(message);
        } else {
            String message = "Очередь выдачи пуста. Все разыгранные игрушки розданы призёрам.";
            System.out.println(message);
        }
    }


    public void giveOutAllPrizes(){
        while (!prizesQueue.isEmpty()){
            giveOutPrize();
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String line = "--------------";
        sb.append(line)
                .append("\n<< СКЛАД МАГАЗИНА >>\n");
        Integer totalToysCount = 0;
        Integer toysTypesCount = 1;
        for (var list : warehouse.values()) {
            for (int i = 0; i < list.size(); i++) {
                sb.append(toysTypesCount)
                        .append(". ")
                        .append(list.get(i))
                        .append(", остаток ")
                        .append(list.get(i).getCount())
                        .append("\n");
                toysTypesCount++;
                totalToysCount += list.get(i).getCount();
            }
        }
        sb.append("ИТОГО: ")
                .append(totalToysCount)
                .append(" игрушек по ")
                .append(toysTypesCount - 1)
                .append(" наименованиям.\n--------------");
        return sb.toString();
    }

    public void showTypesAndChances() {
        if (!raffleToyTypesList.isEmpty()) {
            System.out.println("В розыгрыше участвуют следующие типы игрушек: ");
            double totalChance = 0;
            for (Integer chance : raffleToyTypesList.values()) {
                totalChance += chance;
            }
            if (totalChance > 0) {
                for (Map.Entry<String, Integer> entry : raffleToyTypesList.entrySet()) {
                    System.out.println(entry.getKey() + " - шанс выпадения " + String.format("%.0f", entry.getValue() / totalChance * 100) + ".");
                }
            } else {
                System.out.println("В списке типов игрушек для лотереи указаны нулевые шансы.");
            }
        } else {
            System.out.println("Список типов игрушек для лотереи пуст.");
        }
        System.out.println("--------------");
    }
}
