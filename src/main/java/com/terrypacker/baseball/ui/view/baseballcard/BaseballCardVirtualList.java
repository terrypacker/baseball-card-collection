package com.terrypacker.baseball.ui.view.baseballcard;

import com.terrypacker.baseball.entity.baseballcard.BaseballCard;
import com.vaadin.flow.component.virtuallist.VirtualList;
import java.util.function.Consumer;

/**
 * @author Terry Packer
 */
public class BaseballCardVirtualList extends VirtualList<BaseballCard> {

    public BaseballCardVirtualList(BaseballCardDataProvider dataProvider, Consumer<BaseballCard> selectListener) {
        this.setDataProvider(dataProvider);
        this.setRenderer(new BaseballCardRenderer(c -> new BaseballCardTile(c, selectListener, false)));
    }
}
