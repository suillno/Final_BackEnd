package kr.co.kh.service;

import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameLibraryVO;

public interface GameCartService {
    String saveGameLibrary(GameCartVO vo);
}
