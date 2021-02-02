package cashregister.serviceImpl;

import cashregister.model.CashRegisterModel;
import cashregister.service.CashRegisterService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CashRegisterServiceImplTest {
    @Mock
    CashRegisterModel cashRegisterModel;
    @Mock
    CashRegisterService cashRegisterService;
    List<String> inputs;

    @Before
    public void setUp() {
       cashRegisterModel = new CashRegisterModel(Arrays.asList(1, 2, 3, 4, 5));
       cashRegisterService = new CashRegisterServiceImpl();
    }

    @Test
    public void test_show() {
        cashRegisterService.executeShow("show");
        assert(cashRegisterModel.toString().contains(String.valueOf(cashRegisterModel.getTotal())));
    }
}
