package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameLibraryVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameCartMapper {
    String saveGameLibrary(GameCartVO vo);
}
