package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameWalletLogVO;
import kr.co.kh.model.vo.GameWalletVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WalletLogMapper {


    List<GameWalletLogVO> selectLogsByUserId(Long userId);
}
