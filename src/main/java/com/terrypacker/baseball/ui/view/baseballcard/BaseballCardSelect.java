package com.terrypacker.baseball.ui.view.baseballcard;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.terrypacker.baseball.service.BaseballCardService;
import com.vaadin.flow.component.combobox.ComboBox;

/**
 * @author Terry Packer
 */
public class BaseballCardSelect extends ComboBox<BaseballCard> {

    private final BaseballCardDataProvider dataProvider;
    private final BaseballCardFilter filter;

    public BaseballCardSelect(BaseballCardService service) {
        this.dataProvider = new BaseballCardDataProvider(service);
        this.filter = new BaseballCardFilter(dataProvider);
        BaseballCardFilter filter = new BaseballCardFilter(dataProvider);
        setDataProvider(dataProvider, filterText -> {
            filter.setPlayerName(filterText);
            dataProvider.setFilter(filter);
            return null;
        });
        setItemLabelGenerator(card -> {
            return card.getPlayerName() + " - " + card.getBrand() + " - " + card.getCardNumber() + " - " + card.getYear();
        });
    }

}
