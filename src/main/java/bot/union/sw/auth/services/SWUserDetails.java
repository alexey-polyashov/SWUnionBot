package bot.union.sw.auth.services;

import bot.union.sw.entities.BotUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
public class SWUserDetails implements UserDetails {

    private BotUser user;
    private Collection<? extends GrantedAuthority> authorities;

    public SWUserDetails(BotUser user) {
        this.user = user;
        authorities = user.getRoles().stream()
                .map( role-> new SimpleGrantedAuthority((role.getName())))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(user.getBlocked()==true || user.getMarked()==true);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

}
