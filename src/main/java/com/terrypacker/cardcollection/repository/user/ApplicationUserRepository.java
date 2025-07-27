package com.terrypacker.cardcollection.repository.user;

import com.terrypacker.cardcollection.db.tables.Applicationuser;
import com.terrypacker.cardcollection.db.tables.records.ApplicationuserRecord;
import com.terrypacker.cardcollection.entity.user.ApplicationUser;
import com.terrypacker.cardcollection.repository.JooqRepository;
import com.terrypacker.cardcollection.service.SecurityService;
import com.terrypacker.cardcollection.ui.view.user.UserFilter;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author Terry Packer
 */
@Repository
public class ApplicationUserRepository extends
    JooqRepository<Applicationuser, ApplicationUser, ApplicationuserRecord, UserFilter> implements UserDetailsService {

    private final SecurityService securityService;

    public ApplicationUserRepository(@Autowired DSLContext dslContext, SecurityService securityService) {
        super(dslContext, Applicationuser.APPLICATIONUSER);
        this.securityService = securityService;
    }

    public Mono<ApplicationUser> get(String username) {
        return Mono.justOrEmpty(
            create.fetchOptional(table, table.USERNAME.eq(username))
                .map(this::unmapFromRecord));
    }

    @Override
    protected ApplicationUser unmapFromRecord(ApplicationuserRecord record) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(record.getUsername());
        applicationUser.setPassword(record.getPassword());
        return applicationUser;
    }

    @Override
    protected ApplicationuserRecord mapToRecord(ApplicationUser entity) {
        ApplicationuserRecord record = new ApplicationuserRecord();
        record.setId(entity.getId());
        record.setUsername(entity.getUsername());
        if(entity.getPassword().matches("\\{.*\\}.*")) {
            record.setPassword(entity.getPassword());
        }else {
            //Hash it
            String hash = securityService.hashPassword(entity.getPassword());
            record.setPassword("{bcrypt}" + hash);
        }

        return record;
    }

    @Override
    protected Field<Integer> getIdField() {
        return table.ID;
    }

    @Override
    public ApplicationUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return get(username).block();
    }
}
