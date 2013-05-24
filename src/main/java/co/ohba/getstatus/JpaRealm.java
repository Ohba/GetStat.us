package co.ohba.getstatus;

import co.ohba.autumn.FilterChain;
import co.ohba.autumn.HasFilterChains;
import co.ohba.getstatus.entities.Geek;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JpaRealm extends AuthorizingRealm implements HasFilterChains {

    @Inject
    private EntityManager em;

    @Inject
    SecureRandomNumberGenerator srng;

    @Getter
    private List<FilterChain> filterChains = new ArrayList<>();

    public JpaRealm() {
        super(new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME));
        setName("JpaRealm");

        filterChains.add(new FilterChain("/login.jsp", ShiroWebModule.AUTHC));
        filterChains.add(new FilterChain("/api/*", ShiroWebModule.AUTHC));

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        log.error("AUTHENTICATION INFO em is {}", em);

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        log.error("AUTHENTICATION INFO un={} pw={} host={} remember={}", upToken.getUsername(), upToken.getPassword(), upToken.getHost(), upToken.isRememberMe());

        Geek user = em.createQuery("SELECT g FROM Geek g WHERE g.username = :username", Geek.class)
                .setParameter("username", upToken.getUsername()).getSingleResult();

        if (user == null) {
            log.error("AUTHENTICATION INFO cloud not find user");//, i will create one and you can try again");
            //createTempUser(upToken.getUsername(), upToken.getPassword());
            return null;
        } else {
            log.error("AUTHENTICATION INFO found user {}", user);

            return buildSimpleAccount(user);
        }

    }

    private Account buildSimpleAccount(Geek user) {
        SimpleAccount acct = new SimpleAccount();
        acct.setPrincipals(new SimplePrincipalCollection(user.getId(), getName()));
        acct.setCredentials(user.getHashedPw());
        acct.setCredentialsSalt(ByteSource.Util.bytes(user.getPwSalt()));
        acct.addRole("geek");
        return acct;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        log.error("AUTHORIZATION INFO em is {}", em);

        Long userId = (Long) getAvailablePrincipal(principals);

        Geek user = em.find(Geek.class, userId);

        if (user == null) {
            log.error("AUTHORIZATION INFO cloud not find user with id {}", userId);
            return null;
        } else {
            log.error("AUTHORIZATION INFO found user with id {}", userId);
            return buildSimpleAccount(user);
        }
    }

}
