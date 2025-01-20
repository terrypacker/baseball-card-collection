package com.terrypacker.baseball.service;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.terrypacker.baseball.repository.baseballcard.BaseballCardJooqRepository;
import com.terrypacker.baseball.ui.view.baseballcard.BaseballCardFilter;
import org.springframework.stereotype.Service;

@Service
public class BaseballCardService extends AbstractService<BaseballCard, BaseballCardJooqRepository, BaseballCardFilter> {
    public BaseballCardService(BaseballCardJooqRepository baseballCardRepository) {
        super(baseballCardRepository);
    }
}
