package pl.sda.springdemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springdemo.security.AccessRule;

@Repository
public interface AccessRuleRepository extends CrudRepository<AccessRule, String> {
}
