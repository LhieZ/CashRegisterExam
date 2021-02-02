package cashregister.service;

import java.util.List;

public interface CashRegisterService {
    void loopInput(String input);
    void inputValue();
    void executeChange(String input, List<String> inputs);
    void executeTake(String input, List<String> inputs);
    void executePut(String input, List<String> inputs);
    void executeShow(String input);
    void executeQuit(String input);
}
