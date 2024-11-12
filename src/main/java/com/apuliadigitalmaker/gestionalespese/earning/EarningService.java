package com.apuliadigitalmaker.gestionalespese.earning;

import com.apuliadigitalmaker.gestionalespese.account.Account;
import com.apuliadigitalmaker.gestionalespese.account.AccountRepository;
import com.apuliadigitalmaker.gestionalespese.expense.Expense;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EarningService {

    @Autowired
    private EarningRepository earningRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Earning> findAllEarnings(){
        return earningRepository.findAll();
    }
    public Optional<Earning> findById(Integer id) {
        return  earningRepository.findById(id);   }

    public Earning saveEarning(Earning earning){
        Long amount = earning.getAmount().longValue();
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }else{
            earning.getAccount().getActualBalance().add(earning.getAmount());
            accountRepository.save(earning.getAccount());
            return earningRepository.save(earning);
        }
    }

    @Transactional
    public Earning updateEarning(Integer id, Map<String, Object> update) {
        Optional<Earning> optionalEarning = earningRepository.findById(id);
        if (optionalEarning.isPresent()){
            throw new EntityNotFoundException("Expense with id " + id + " not found");
        }
        Earning earning = optionalEarning.get();
        update.forEach((key, value) -> {
            switch (key) {
                case "amount":
                    earning.setAmount((BigDecimal) value);
                    break;
                case "expenseName":
                    earning.setEarningName((String) value);
                    break;
                case "expanseDate":
                    earning.setEarningDate((Instant) value);
                    break;
            }
        });
        return earningRepository.save(earning);
    }
}
