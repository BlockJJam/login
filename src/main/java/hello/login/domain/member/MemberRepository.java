package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();
    private static long sequence = 1L;

    public Member save(Member member){
        member.setId(++sequence);
        log.info("save: member={}", member);

        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public Optional<Member> findByLoginId(String loginId){
       /* List<Member> all = findAll();
        for(Member m : all){
            if(m.getLoginId().equals(loginId)){
                return Optional.of(m);
            }
        }

        return Optional.empty();*/

        // 위 6줄을 3줄로 고치기 가능( Stream )
        return findAll().stream()
                .filter(m-> m.getLoginId().equals(loginId))
                .findFirst();
    }

   public void clearStore(){
        store.clear();
   }
}
