package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameWalletLogVO;
import kr.co.kh.model.vo.GameWalletVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalletLogMapper {
    long insertWalletLog(GameWalletLogVO log);

    String toggleGameWallet(GameWalletVO vo);
}
