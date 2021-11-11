package za.co.technetic.ss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import za.co.technetic.ss.domain.dto.AuthRequestDto;
import za.co.technetic.ss.domain.dto.MemberDto;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.service.MemberService;
import za.co.technetic.ss.web.util.JwtUtil;

@RestController
@RequestMapping("member")
@CrossOrigin("*")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse<String>> registerMember(@RequestBody MemberDto memberDto) {
        String message = "Successfully registered member";
        HttpStatus httpStatus = HttpStatus.CREATED;

        Member member = memberService.saveMember(memberDto);

        if (null == member) {
            message = "Member could not be registered";
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        GeneralResponse<String> generalResponse = new GeneralResponse<>(
                httpStatus.value(),
                message,
                null
        );

        return new ResponseEntity<>(generalResponse, httpStatus);
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse<String>> authenticateMember(@RequestBody AuthRequestDto authRequestDto) {
        String message = "Successfully logged in";
        HttpStatus httpStatus = HttpStatus.OK;
        String token = null;

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword())
            );
            token = jwtUtil.generateToken(authRequestDto.getEmail());
        }
        catch(Exception e) {
            message = e.getMessage();
            httpStatus = HttpStatus.UNAUTHORIZED;
        }

        GeneralResponse<String> generalResponse = new GeneralResponse<>(
                httpStatus.value(), message, token
        );

        return new ResponseEntity<>(generalResponse, httpStatus);
    }
}
