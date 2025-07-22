package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameWalletLogVO;
import kr.co.kh.model.vo.GameWalletVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WalletLogMapper {

    /**
     * 내지갑 전체내역
     * @param userId
     * @return
     */
    List<GameWalletLogVO> listWallet(Long userId);

    /**
     * 내지감내역 10개만
     * @param userId
     * @return
     */
    List<GameWalletLogVO> selectLogsByUserId(Long userId);
}
