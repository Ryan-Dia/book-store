package bookstore.shop.domain.service;

import bookstore.shop.domain.Member;
import bookstore.shop.repository.MemberRepository;
import bookstore.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional // 데이터 변경해야 하니까 이게 있어야 데이터가 롤백됨
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    public void 회원가입() throws Exception{
        // given
        Member member = new Member();
        member.setName("kim");


        // when
        Long saveId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    @DisplayName("중복-회원-예외")
    public void 중복_회원_예외() throws Exception{
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        // when

        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);// 예외가 발생해야 한다.
        });
    }
}