package kr.co.kh.service.game.impl;

import kr.co.kh.mapper.GameMemberMapper;
import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.service.game.service.GameMemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class GameMemberServiceImpl implements GameMemberService {
    final private GameMemberMapper gameMemberMapper;

    @Override
    public void cartSave(GameCartVO vo) {
        gameMemberMapper.cartSave(vo);
    }
}
