package bookstore.shop.domain.service;

import bookstore.shop.domain.Member;
import bookstore.shop.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true) // JPA에서 데이터 변경은 transactional 안에서 이루어지는게 좋다.
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // AllArgsConstructor 을 했기 때문에 필요 x -> 이건 모든 필드를 생성자를 통해 주입
    // RequiredArgsConstructor -> final이 있는 필드만 가지고 생성자 만들어줌
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional // 이게 우선권을 가지고 기본값은 (readOnly = false) 이다
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    // 멀티쓰레드 상황 고려되지 않음
    private void validateDuplicateMember(Member member) {
        // Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     *
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


}
