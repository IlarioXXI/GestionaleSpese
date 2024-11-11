package com.apuliadigitalmaker.gestionalespese.earning;

import com.apuliadigitalmaker.gestionalespese.expense.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EarningService {

    @Autowired
    private EarningRepository earningRepository;

    public List<Earning> findAllEarnings(){
        return earningRepository.findAll();
    }
    public Optional<Earning> findById(Integer id) {
        return  earningRepository.findById(id);   }

    public Earning saveEarning(Earning earning){
        return earningRepository.save(earning);
    }
}
