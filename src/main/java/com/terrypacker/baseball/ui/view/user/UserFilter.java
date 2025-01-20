package com.terrypacker.baseball.ui.view.user;

import com.terrypacker.baseball.repository.EntityFilter;
import com.terrypacker.baseball.repository.Filter;
import java.util.List;

/**
 * @author Terry Packer
 */
public class UserFilter implements EntityFilter {

    @Override
    public List<Filter> getFilters() {
        return List.of();
    }
}
