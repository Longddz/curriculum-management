// package com.example.security;

// import org.springframework.security.authentication.AbstractAuthenticationToken;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;

// import java.util.Collections;

// public class JwtAuthentication extends AbstractAuthenticationToken {

//     private final String gmail;
//     private final Integer idAccount;

//     public JwtAuthentication(String gmail, Integer idAccount) {
//         super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
//         this.gmail = gmail;
//         this.idAccount = idAccount;
//         setAuthenticated(true);
//     }

//     @Override
//     public Object getCredentials() {
//         return null;
//     }

//     @Override
//     public Object getPrincipal() {
//         return new JwtPrincipal(gmail, idAccount);
//     }

//     public static class JwtPrincipal {
//         private final String gmail;
//         private final Integer idAccount;

//         public JwtPrincipal(String gmail, Integer idAccount) {
//             this.gmail = gmail;
//             this.idAccount = idAccount;
//         }

//         public String getGmail() {
//             return gmail;
//         }

//         public Integer getIdAccount() {
//             return idAccount;
//         }
//     }
// }

package com.example.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public class JwtAuthentication extends UsernamePasswordAuthenticationToken {

    // Constructor với authorities (dùng khi có vai trò từ token)
    public JwtAuthentication(String gmail, Integer idAccount, Collection<? extends GrantedAuthority> authorities) {
        super(gmail, idAccount, authorities);
    }

    public JwtAuthentication(String gmail, Integer idAccount) {
        super(gmail, idAccount, null);
    }

    public String getGmail() {
        return (String) getPrincipal();
    }

    public Integer getIdAccount() {
        return (Integer) getCredentials();
    }
}