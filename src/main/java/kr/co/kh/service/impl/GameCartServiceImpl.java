package kr.co.kh.service.impl;

import kr.co.kh.mapper.ChartMapper;
import kr.co.kh.mapper.GameCartMapper;
import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameLibraryVO;
import kr.co.kh.service.GameCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameCartServiceImpl implements GameCartService {
    private final GameCartMapper gameCartMapper;

    @Override
    public String saveGameLibrary(GameCartVO vo) {
        return gameCartMapper.saveGameLibrary(vo);
    }
}
