package com.example.project.demos.web.dao;


import com.example.project.demos.web.dto.list.SupplyReturnInfo;
import com.example.project.demos.web.dto.supplyReturn.QueryByPageDTO;
import com.example.project.demos.web.entity.SupplyReturnEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-23 10:56:52
 */
@Mapper
public interface SupplyReturnDao extends BaseMapper<SupplyReturnEntity> {
    List<SupplyReturnInfo> selectSupplyReturnInfoListByPage(@Param("return") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    SupplyReturnInfo selectSupplyReturnInfoById(Long id);

    List<SupplyReturnInfo> queryListForExport(@Param("return") QueryByPageDTO dto);

    List<String> queryBillNoListByParam(@Param("billNoPrefix") String billNoPrefix);
}
