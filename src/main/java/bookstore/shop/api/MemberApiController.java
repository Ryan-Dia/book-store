package bookstore.shop.api;

import bookstore.shop.domain.Address;
import bookstore.shop.domain.Member;
import bookstore.shop.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result<List<MemberDto>> memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName(), m.getAddress()))
                .collect(Collectors.toList());

        return new Result<>(collect);

    }

    public record Result<T>(T data) {}

    public record MemberDto(String name, Address address) {}


    @PostMapping("/api/v1/members")
    CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.name());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {

        // 커맨드와 커리를 분리 -> 유지보수성 증대
        memberService.update(id, request.name());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    record UpdateMemberRequest(String name) {}
    record UpdateMemberResponse(Long id, String name) {}

    record CreateMemberRequest(@NotEmpty String name) {}
    record CreateMemberResponse(Long id) {}
}
