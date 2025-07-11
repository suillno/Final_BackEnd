package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameWalletLogVO;
import kr.co.kh.model.vo.GameWalletVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface WalletLogMapper {
    long insertWalletLog(GameWalletLogVO log);

    void toggleGameWallet(Map<String, Object> param);
}
