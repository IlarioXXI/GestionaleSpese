package com.apuliadigitalmaker.gestionalespese.earning;

import com.apuliadigitalmaker.gestionalespese.account.Account;
import com.apuliadigitalmaker.gestionalespese.account.AccountRepository;
import com.apuliadigitalmaker.gestionalespese.expense.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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
}
