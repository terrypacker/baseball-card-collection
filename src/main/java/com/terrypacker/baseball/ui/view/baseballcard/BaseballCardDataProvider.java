package com.terrypacker.baseball.ui.view.baseballcard;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.terrypacker.baseball.service.BaseballCardService;
import com.terrypacker.baseball.ui.view.AbstractDataProvider;
import org.springframework.stereotype.Component;

/**
 * @author Terry Packer
 */
@Component
public class BaseballCardDataProvider extends AbstractDataProvider<BaseballCard, BaseballCardFilter, BaseballCardService> {
    public BaseballCardDataProvider(BaseballCardService baseballCardService) {
        super(baseballCardService);
    }
}
