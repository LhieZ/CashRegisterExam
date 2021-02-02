import cashregister.service.CashRegisterService;
import cashregister.serviceImpl.CashRegisterServiceImpl;

public class CashRegisterMain {
    public static void main(String[] args) {
        System.out.println("start program, waiting for a command");

        CashRegisterService cashRegisterService = new CashRegisterServiceImpl();
        cashRegisterService.inputValue();
    }
}
