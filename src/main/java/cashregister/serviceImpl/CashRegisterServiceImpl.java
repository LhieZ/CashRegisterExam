package cashregister.serviceImpl;


import cashregister.model.CashRegisterModel;
import cashregister.service.CashRegisterService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.min;

enum CommandList {SHOW, PUT, TAKE, CHANGE, QUIT}

public class CashRegisterServiceImpl implements CashRegisterService {

    private CashRegisterModel cashRegisterModel;
    private final List<Integer> denominations = Arrays.asList(20, 10, 5, 2, 1);

    private int index;

    @Override
    public void inputValue() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        try {
            input = reader.readLine().toUpperCase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loopInput(input);
    }

    @Override
    public void loopInput(String input) {

        List<String> inputs = Arrays.stream(input.split("\\s")).map(String::new).collect(Collectors.toList());

        executeQuit(input);
        executeShow(input);
        executePut(input, inputs);
        executeTake(input, inputs);
        executeChange(input, inputs);

        inputValue();
    }

    @Override
    public void executeChange(String input, List<String> inputs) {
        if (input.toUpperCase().contains(CommandList.CHANGE.name())) {
            inputs.removeIf(value -> value.contains(CommandList.CHANGE.name()));

            AtomicInteger change = new AtomicInteger(Integer.parseInt(inputs.get(0)));
            AtomicInteger counter = new AtomicInteger(0);
            List<Integer> newDenominationCounts = new LinkedList<>();

            setNewValues(change, counter, newDenominationCounts);
            cashRegisterModel.setDenominationCounts(newDenominationCounts);
        }
    }

    @Override
    public void executeTake(String input, List<String> inputs) {
        if (input.toUpperCase().contains(CommandList.TAKE.name())) {
            inputs.removeIf(value -> value.contains(CommandList.TAKE.name()));

            List<Integer> newDenominationCount = new LinkedList<>();
            int counter = 0;
            for (String inputStr : inputs) {
                newDenominationCount.add(cashRegisterModel.getDenominationCounts().get(counter) - Integer.parseInt(inputStr));
                counter++;
            }
            setCountsAndTotal(newDenominationCount);
            System.out.println(cashRegisterModel);
        }
    }

    @Override
    public void executePut(String input, List<String> inputs) {
        if (input.toUpperCase().contains(CommandList.PUT.name())) {
            inputs.removeIf(value -> value.contains(CommandList.PUT.name()));

            List<Integer> denominationCountAdded = new LinkedList<>();
            int counter = 0;
            for (String inputStr : inputs) {
                denominationCountAdded.add(cashRegisterModel.getDenominationCounts().get(counter) + Integer.parseInt(inputStr));
                counter++;
            }
            setCountsAndTotal(denominationCountAdded);
            System.out.println(cashRegisterModel);
        }
    }

    @Override
    public void executeShow(String input) {
        if (index == 0 && input.toUpperCase().equals(CommandList.SHOW.name())) {
            cashRegisterModel = new CashRegisterModel(Arrays.asList(1, 2, 3, 4, 5));
            cashRegisterModel.setTotal(computeTotal(cashRegisterModel));
            System.out.println(cashRegisterModel);
            index++;
        }
    }

    @Override
    public void executeQuit(String input) {
        if (input.toUpperCase().equals(CommandList.QUIT.name())) {
            System.out.println("Bye");
            System.exit(0);
        }
    }

    private void setNewValues(AtomicInteger change, AtomicInteger counter, List<Integer> newDenominationCounts) {
        int holdChange = change.get();

        denominations.forEach(denomination -> {
            newDenominationCounts.add(cashRegisterModel.getDenominationCounts().get(counter.get()));

            if (cashRegisterModel.getDenominationCounts().get(counter.get()) != 0) {
                while (change.get() / denomination != 0 && newDenominationCounts.get(counter.get()) != 0) {
                    change.updateAndGet(newValue -> newValue - denomination);
                    newDenominationCounts.set(counter.get(), newDenominationCounts.get(counter.get()) - 1);
                }
            }
            counter.getAndIncrement();
        });

        validateNewDenominationCounts(newDenominationCounts, holdChange);
    }

    private void validateNewDenominationCounts(List<Integer> newDenominationCounts, int holdChange) {
        int holdTotal = cashRegisterModel.getTotal();
        setCountsAndTotal(newDenominationCounts);

        if (holdTotal - holdChange != cashRegisterModel.getTotal() ){
            System.out.println("Sorry");
        }else{
            System.out.println(cashRegisterModel);
        }
    }

    private int computeTotal(CashRegisterModel cashRegisterModel) {
        return IntStream.range(0, min(cashRegisterModel.getDenominationCounts().size(), denominations.size()))
                .map(count -> cashRegisterModel.getDenominationCounts().get(count) * denominations.get(count))
                .sum();
    }

    private void setCountsAndTotal(List<Integer> newDenominationCount) {
        cashRegisterModel.setDenominationCounts(newDenominationCount);
        cashRegisterModel.setTotal(computeTotal(cashRegisterModel));
    }
}
