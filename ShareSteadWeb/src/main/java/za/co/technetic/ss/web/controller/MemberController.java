package za.co.technetic.ss.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.technetic.ss.domain.dto.MemberDto;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.service.MemberService;

@RestController
@RequestMapping("member")
@CrossOrigin("*")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/registration")
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
}
