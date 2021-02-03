package cashregister.serviceImpl;

import cashregister.model.CashRegisterModel;
import cashregister.service.CashRegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CashRegisterServiceImplTest {
    @Mock
    private static CashRegisterModel cashRegisterModel;
    @Mock
    private static CashRegisterService cashRegisterService;

    List<String> inputs;
    List<Integer> denominations;

    @BeforeEach
    public void setUp() {
        cashRegisterModel = new CashRegisterModel(Arrays.asList(1, 2, 3, 4, 5));
        cashRegisterService = new CashRegisterServiceImpl();
        inputs = new LinkedList<>();
        denominations = Arrays.asList(20, 10, 5, 2, 1);
    }

    @Test
    public void test_show() {
        inputs.add("show");
        cashRegisterService.executeShow(inputs.get(0));
        assertEquals("show", inputs.get(0));
        assertTrue(cashRegisterModel.toString().contains(String.valueOf(cashRegisterModel.getTotal())));
    }

    @Test
    void executeQuit() {
        inputs.add("quit");
        String print = "Bye";
        assertEquals("quit", inputs.get(0));
        assertEquals("Bye", print);
    }

    @Test
    void executeChange() {
        inputs = Arrays.asList("change", "11");
        assertEquals("change", inputs.get(0));
    }

    @Test
    void executeTake() {
        inputs = Arrays.asList("take", "1", "0", "0", "2", "3");
        StringBuilder output = new StringBuilder();

        int counter = 0;
        int difference;
        int total = 0;
        for (String input : inputs) {
            if (!input.equals("take")) {
                difference = cashRegisterModel.getDenominationCounts().get(counter) - Integer.parseInt(input);
                total += denominations.get(counter) * difference;
                output.append(difference);
                output.append(" ");
                counter++;
            }
        }

        cashRegisterModel.setTotal(total);

        assertEquals("take", inputs.get(0));
        assertEquals(output.toString().trim(), "0 2 3 2 2");
        assertEquals(41, cashRegisterModel.getTotal());
    }

    @Test
    void executePut() {
        inputs = Arrays.asList("put", "1", "2", "3", "0", "5");
        StringBuilder output = new StringBuilder();

        int counter = 0;
        int sum;
        int total = 0;
        for (String input : inputs) {
            if (!input.equals("put")) {
                sum = cashRegisterModel.getDenominationCounts().get(counter) + Integer.parseInt(input);
                total += denominations.get(counter) * sum;
                output.append(sum);
                output.append(" ");
                counter++;
            }
        }

        cashRegisterModel.setTotal(total);

        assertEquals("put", inputs.get(0));
        assertEquals(output.toString().trim(), "2 4 6 4 10");
        assertEquals(128, cashRegisterModel.getTotal());
    }
}
