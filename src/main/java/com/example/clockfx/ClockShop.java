package com.example.clockfx;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;


public class ClockShop  implements Iterable<Clock>, Serializable {
    ArrayList<Clock> clocks;
    transient ArrayList<IObserver> iOserver = new ArrayList<>();

    public void events(){
        iOserver.forEach((o) -> {o.event(this);});
    }

    public void sub(IObserver o){
        iOserver.add(o);
    }


    public ClockShop(){
        clocks = new ArrayList<>();
    }

    @Override
    public String toString(){
        StringBuilder outBuilder = new StringBuilder("##### Clocks assortment #####");
        for (Clock clock : clocks) {
            outBuilder.append("\n").append(clock.toString());
        }
        outBuilder.append("#############################\n");
        return outBuilder.toString();
    }

    @Override
    public Iterator<Clock> iterator() {
        return clocks.iterator();
    }

    @Override
    public void forEach(Consumer<? super Clock> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Clock> spliterator(){
        return Iterable.super.spliterator();
    }

    public void addClock(Clock clock){
        clocks.add(clock);
        events();
    }

    public void removeAt(int ind) throws TimeException{
        if (ind < 0 || ind >= clocks.size()){
            throw new TimeException(14, "Index out of bounds");
        }
        clocks.remove(ind);
        events();
    }

    public void remove(Clock cl) throws TimeException{
        if (cl != null){
            clocks.remove(cl);
            events();
        }
        else {throw new TimeException(39, "You are trying to remove a null element"); }
        
    }
    public int getIndex(Clock c) {
        return c.getId();
    }

    public void setIndex(Clock c, int i) {
        c.setId(i);
    }

    public void setClocks(ArrayList<Clock> _clocks) {
        this.clocks = _clocks;
    }

    public void setTime(Types type, int val) throws TimeException{
        if (clocks.isEmpty()) throw new TimeException(10, "Clocks shop is empty");
        for (Clock clock : clocks){
            clock.setTime(type, val);
        }
        events();
    }
    public void setTimeAt(Types type, int val, int id) throws TimeException{
        if (clocks.isEmpty()) throw new TimeException(10, "Clocks shop is empty");
        clocks.get(id).setTime(type, val);
        events();
    }
    public void skipTimeAt(Types type, int val, int id) throws TimeException{
        if (clocks.isEmpty()) throw new TimeException(10, "Clocks shop is empty");
        clocks.get(id).skipTime(type, val);
        events();
    }

    public void clocksClear(){
        clocks.clear();
    }

    public Clock getClock(int ind) throws TimeException{
        if (ind < 0 || ind >= clocks.size()) throw new TimeException(14, "Index out of bounds");
        return clocks.get(ind);
    }

    public int getSize(){
        return  clocks.size();
    }

    public void print() throws TimeException{
        if (clocks.isEmpty()){
            throw new TimeException(12, "Clocks shop is empty");
        }
        System.out.println("##### Clocks assortment #####");
        for (Clock clock : clocks) {
            System.out.println(clock.toString());
        }
        System.out.println("#############################\n");
    }

    public void printOnly(Types sortBy) throws TimeException{
        if (clocks.isEmpty()) throw new TimeException(12, "Clocks shop is empty");
        int i = 1;
        System.out.println("Displayed only by " + sortBy);
        switch (sortBy){
            case TIME -> {
                for (Clock clock : clocks) {
                    System.out.println(i++ + " | " + clock.getTime());
                }
            }
            case BRAND -> {
                for (Clock clock : clocks) {
                    System.out.println(i++ + " | " + clock.getBrand());
                }
            }
            case PRICE -> {
                for (Clock clock : clocks) {
                    System.out.println(i++ + " | " +clock.getPrice());
                }
            }
            default -> throw new TimeException(13, "Wrong type of info to sort by given");
        }
        System.out.println();
    }

    public String getMostExpensiveDescription() throws TimeException {
        /*
        if (clocks.isEmpty()) throw new TimeException(10, "Clocks shop is empty");
        int max = 0;
        String descr = "";
        for (Clock clock: clocks) {
            if (clock.getPrice() > max) {
                descr = clock.toString();
                max = clock.getPrice();
            }
        }
        return descr;
        */
        if (clocks.isEmpty()) throw new TimeException(12, "Clocks shop is empty");

        Clock mostExpensiveClock = Collections.max(clocks, Comparator.comparingDouble(Clock::getPrice));
        return mostExpensiveClock.toString();
    }

    public String getMostCommonBrand(){
        Map<String, Integer> InsertionsCount = new HashMap<>();
        for (Clock clock : clocks){
            InsertionsCount.put(clock.getBrand(), InsertionsCount.getOrDefault(clock.getBrand(), 0) + 1); //
        }
        return Collections.max(InsertionsCount.entrySet(), Map.Entry.comparingByValue()).getKey(); ///
    }

    public int getMostCommonBrandCount(){
        HashMap<String, Integer> InsertionsCount = new HashMap<>();
        for (Clock clock : clocks){
            InsertionsCount.put(clock.getBrand(), InsertionsCount.getOrDefault(clock.getBrand(), 0) + 1); //
        }
        return Collections.max(InsertionsCount.entrySet(), Map.Entry.comparingByValue()).getValue(); ///
    }

    public Set<String> sortBrandNames() {
        /*
        Comparator<Clock> compr = Comparator.comparing(Clock::getBrand);
        Collections.sort(clocks, compr);
        */
        Set<String> distinctBrandNames = new TreeSet<>();
        for (Clock clock : clocks) {
            distinctBrandNames.add(clock.getBrand());
        }
        return distinctBrandNames;

        /*clocks.stream().collect(
                        () -> new HashSet<String>(),
                        (set, item) -> set.add(item.getBrand()),
                        (set1, set2) -> set1.addAll(set2))
                .forEach((x) -> System.out.println(x));
        return null;*/
    }


    public void printSortedBrandNames() throws TimeException {
        Set<String> brands = this.sortBrandNames();
        System.out.println("Sorted brand names list:");
        brands.forEach(System.out::println);
    }

    public String getDescriptionByBrand(String _brand) throws TimeException{
        if (clocks.isEmpty()) throw new TimeException(12, "Clocks shop is empty");
        for (Clock clock: clocks) {
            if (clock.getBrand().equals(_brand))
                return clock.toString();
        }
        throw new TimeException(15, "Wrong type of info to given");
    }
}

