package pl.sda.springdemo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import pl.sda.springdemo.repository.AccessRuleRepository;

import java.util.function.Supplier;

import static org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager.Builder;
import static org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager.builder;

@Component
@Slf4j
public class AccessRuleAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final AccessRuleRepository rules;
    private RequestMatcherDelegatingAuthorizationManager delegate;  //builded in 'applyRules'

    public AccessRuleAuthorizationManager(AccessRuleRepository rules) {
        this.rules = rules;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        return this.delegate.check(authentication, object.getRequest());
    }

    @EventListener(ApplicationReadyEvent.class) //disable Spring Shell in application.yml so that event gets fired
    void applyRules() {
        Builder builder = builder();
        for (AccessRule rule : this.rules.findAll()) {
            builder.add(
                    new AntPathRequestMatcher(rule.getUrlPattern(), rule.getHttpMethod()),
                    AuthorityAuthorizationManager.hasAuthority(rule.getAuthority())
            );
        }
        this.delegate = builder.build();

        log.info("Access rules used to build RequestMatcherDelegatingAuthorizationManager");
    }
}
