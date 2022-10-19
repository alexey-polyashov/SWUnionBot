package bot.union.sw.auth.controller;

import bot.union.sw.auth.JwtUtils;
import bot.union.sw.auth.dto.LoginResponse;
import bot.union.sw.auth.services.SWUserDetails;
import bot.union.sw.entities.dto.BotUserDto;
import bot.union.sw.auth.dto.LoginRequest;
import bot.union.sw.entities.mappers.BotUserMapper;
import bot.union.sw.services.BotUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final BotUserService botUserService;
    private final JwtUtils jwtUtils;
    private final BotUserMapper botUserMapper;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping()
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        SWUserDetails userDetails = (SWUserDetails) authentication.getPrincipal();
        if(!userDetails.isAccountNonLocked()){
            throw new DisabledException("Пользователь заблокирован");
        }
        return ResponseEntity.ok().body(
                new LoginResponse(jwt, botUserMapper.toDto(userDetails.getUser()))
        );

    }

}
