package co.ohba.getstatus;

import co.ohba.getstatus.entities.Geek;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Slf4j
public class JpaRealm extends AuthorizingRealm {

    @Inject @Setter
    private EntityManager em;

    public JpaRealm() {
        super(new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME));
        setName("JpaRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        log.error("em is {}", em);

        Long userId = (Long) principals.fromRealm(getName()).iterator().next();
        Geek user = em.find(Geek.class, userId);

        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //for (Role role : user.getRoles()) {
            //    info.addRole(role.getName());
            //    info.addStringPermissions(role.getPermissions());
            //}
            info.addRole("geek");
            return info;
        } else {
            return null;
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        log.error("em is {}", em);

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        Geek user = em.createQuery("SELECT g FROM Geek g WHERE g.username = :username", Geek.class)
                .setParameter("username", upToken.getUsername()).getSingleResult();

        if (user == null) {
            return null;
        } else {
            return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
        }

    }
}
